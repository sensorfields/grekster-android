package com.sensorfields.cyborg;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FileService {

  private final Context context;
  private final String fileProviderAuthority;

  @Inject
  FileService(Context context, String fileProviderAuthority) {
    this.context = context;
    this.fileProviderAuthority = fileProviderAuthority;
  }

  public File cacheFile(String name) {
    return new File(context.getCacheDir(), name);
  }

  public Uri getUriForFile(File file) {
    return FileProvider.getUriForFile(context, fileProviderAuthority, file);
  }
}
