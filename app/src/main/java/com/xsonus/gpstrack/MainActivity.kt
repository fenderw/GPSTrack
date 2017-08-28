package com.xsonus.gpstrack

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val LOCATION_PERMISSION = 1837
    val LOCATION_UPDATE_TIME_MS = 10000L
    val LOCATION_UPDATE_DISTANCE = 10f

    val FLOAT_FORMATER_M = "%.2f m"
    val FLOAT_FORMATER_DEG = "%.5f"

    lateinit var tvAltitude: TextView
    lateinit var tvLatitude: TextView
    lateinit var tvLongitude: TextView
    lateinit var btStart: Button
    lateinit var btStop: Button

    lateinit var locationManager: LocationManager
    var providerEnabled = false

    val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(p0: Location) {
            tvAltitude.text = FLOAT_FORMATER_M.format(p0.altitude)
            tvLatitude.text = FLOAT_FORMATER_DEG.format(p0.latitude)
            tvLongitude.text = FLOAT_FORMATER_DEG.format(p0.longitude)
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            //
        }

        override fun onProviderEnabled(p0: String?) {
            providerEnabled = true
            enableButtons()
        }

        override fun onProviderDisabled(p0: String?) {
            stopGettingData()
            providerEnabled = false
            disableButtons()
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_start -> startGettingData()
            R.id.bt_stop -> stopGettingData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAltitude = findViewById(R.id.tv_altitude)
        tvLatitude = findViewById(R.id.tv_Latitude)
        tvLongitude = findViewById(R.id.tv_longitude)
        btStart = findViewById(R.id.bt_start)
        btStop = findViewById(R.id.bt_stop)
        btStart.setOnClickListener(this)
        btStop.setOnClickListener(this)

        // check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            disableButtons()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_TIME_MS, LOCATION_UPDATE_DISTANCE, locationListener)
        } else
            providerEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun startGettingData() {
        if (providerEnabled) {
            btStart.isEnabled = false
            btStop.isEnabled = true
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_TIME_MS, LOCATION_UPDATE_DISTANCE, locationListener)
        }
    }

    private fun stopGettingData() {
        if (providerEnabled) {
            btStart.isEnabled = true
            btStop.isEnabled = false
            locationManager.removeUpdates(locationListener)
        }
    }

    private fun enableButtons() {
        btStart.isEnabled = true
        btStop.isEnabled = false
    }

    private fun disableButtons() {
        btStart.isEnabled = false
        btStop.isEnabled = false
    }

    override fun onPause() {
        super.onPause()
        stopGettingData()
    }
}
