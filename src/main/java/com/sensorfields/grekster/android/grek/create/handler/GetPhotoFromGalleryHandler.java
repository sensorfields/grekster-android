package com.sensorfields.grekster.android.grek.create.handler;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import com.google.common.io.ByteStreams;
import com.sensorfields.cyborg.ActivityService;
import com.sensorfields.cyborg.FileService;
import com.sensorfields.grekster.android.grek.create.Effect.GetPhotoFromGallery;
import com.sensorfields.grekster.android.grek.create.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.inject.Inject;

public final class GetPhotoFromGalleryHandler
    implements ObservableTransformer<GetPhotoFromGallery, Event> {

  private final ActivityService activityService;
  private final FileService fileService;
  private final ContentResolver contentResolver;

  @Inject
  GetPhotoFromGalleryHandler(
      ActivityService activityService, FileService fileService, ContentResolver contentResolver) {
    this.activityService = activityService;
    this.fileService = fileService;
    this.contentResolver = contentResolver;
  }

  @Override
  public ObservableSource<Event> apply(Observable<GetPhotoFromGallery> upstream) {
    return upstream.switchMap(
        effect ->
            activityService
                .activityResult(
                    Intent.createChooser(
                        new Intent(Intent.ACTION_GET_CONTENT)
                            .setType("image/*")
                            .addCategory(Intent.CATEGORY_OPENABLE),
                        null),
                    null)
                .observeOn(Schedulers.io())
                .filter(
                    activityResult ->
                        activityResult.getResultCode() == Activity.RESULT_OK
                            && activityResult.getResultData() != null)
                .map(
                    activityResult -> {
                      Uri uri = activityResult.getResultData().getData();
                      String name = UUID.randomUUID() + ".jpeg";
                      try (Cursor cursor =
                          contentResolver.query(
                              uri, new String[] {OpenableColumns.DISPLAY_NAME}, null, null)) {
                        if (cursor != null && cursor.moveToFirst() && !cursor.isNull(0)) {
                          name = cursor.getString(0);
                        }
                      }
                      File file = fileService.cacheFile(name);
                      try (InputStream input = contentResolver.openInputStream(uri);
                          OutputStream output = new FileOutputStream(file)) {
                        ByteStreams.copy(input, output);
                      }
                      return file;
                    })
                .map(Event::photoReceived)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable());
  }
}
