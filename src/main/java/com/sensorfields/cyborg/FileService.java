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
  private final File cacheDirectory;

  @Inject
  FileService(Context context, String fileProviderAuthority, File cacheDirectory) {
    this.context = context;
    this.fileProviderAuthority = fileProviderAuthority;
    this.cacheDirectory = cacheDirectory;
  }

  public File cacheFile(String name) {
    return new File(cacheDirectory, name);
  }

  public Uri getUriForFile(File file) {
    return FileProvider.getUriForFile(context, fileProviderAuthority, file);
  }
}
