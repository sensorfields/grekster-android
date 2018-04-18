package com.sensorfields.grekster.android.grek.list;

import com.sensorfields.grekster.android.model.Grek;
import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

@DataEnum
interface Effect_dataenum {

  dataenum_case LoadGreks();

  dataenum_case ShowGrekDetails(Grek grek);
}
