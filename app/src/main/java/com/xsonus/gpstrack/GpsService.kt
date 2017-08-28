package com.xsonus.gpstrack

import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Binder
import android.os.Bundle
import android.os.IBinder

/**
 * Created by F on 28/08/17.
 */
class GpsService : Service() {

    val gpsBinder : LocalBinder = LocalBinder()
    var callback : GpsServiceInterface? = null

    val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(p0: Location) {
            callback?.updateLocation(p0)
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            //
        }

        override fun onProviderEnabled(p0: String) {
            callback?.providerEnabled(p0)
        }

        override fun onProviderDisabled(p0: String) {
            callback?.providerDisaled(p0)
        }

    }

    override fun onBind(p0: Intent?): IBinder {
        return gpsBinder
    }

    inner class LocalBinder : Binder() {

        fun getService() : GpsService {
            return this@GpsService
        }
    }

    fun startLocationUpdates(time : Float, distance : Float) {
        //TODO
    }

    fun stoplocationUpdates() {
        //TODO
    }

    fun setLocationCallback(callback : GpsServiceInterface) {
        this.callback = callback
    }
}