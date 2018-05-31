package com.sensorfields.grekster.android.grek.create;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

@DataEnum
interface Effect_dataenum {

  dataenum_case NavigateUp();

  dataenum_case GetPhotoFromCamera();

  dataenum_case GetPhotoFromGallery();
}
