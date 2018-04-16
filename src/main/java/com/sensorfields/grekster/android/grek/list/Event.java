package com.sensorfields.grekster.android.grek.list;

import com.google.common.collect.ImmutableList;
import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

@DataEnum
interface Event_dataenum {

  dataenum_case ScreenLoaded();

  dataenum_case SwipeRefreshTriggered();

  dataenum_case GrekClicked(String grek);

  dataenum_case GreksLoaded(ImmutableList<String> greks);

  dataenum_case GreksLoadingFailed(Throwable error);
}
