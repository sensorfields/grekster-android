package com.sensorfields.grekster.android;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

@DataEnum
interface Event_dataenum {

  dataenum_case Up();

  dataenum_case Down();
}
