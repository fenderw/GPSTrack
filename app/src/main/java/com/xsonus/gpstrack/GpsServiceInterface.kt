package com.xsonus.gpstrack

import android.location.Location

/**
 * Created by F on 28/08/17.
 */
interface GpsServiceInterface {

    fun updateLocation(location : Location)
    fun providerEnabled(str : String)
    fun providerDisaled(str : String)

}