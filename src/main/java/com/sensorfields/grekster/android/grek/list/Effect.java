package com.sensorfields.grekster.android.grek.list;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

@DataEnum
interface Effect_dataenum {

  dataenum_case LoadGreks();

  dataenum_case ShowGrekDetails(String grek);
}
