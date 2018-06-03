package com.sensorfields.grekster.android.grek.create;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;
import java.io.File;

@DataEnum
interface Event_dataenum {

  dataenum_case UpButtonClicked();

  dataenum_case PhotoCameraButtonClicked();

  dataenum_case PhotoGalleryButtonClicked();

  dataenum_case PhotoReceived(File file);
}
