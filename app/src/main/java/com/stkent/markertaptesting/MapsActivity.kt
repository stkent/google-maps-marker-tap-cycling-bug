package com.stkent.markertaptesting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.BitmapDescriptorFactory.*
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        rootView = findViewById(android.R.id.content)
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val center = LatLng(42.335329, -83.045753)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 20f))

        googleMap.addMarker(center.neBy(0.000004f), HUE_RED, "Red")
        googleMap.addMarker(center, HUE_GREEN, "Green")
        googleMap.addMarker(center.neBy(-0.000004f), HUE_BLUE, "Blue")

        // Does not exhibit bug
        googleMap.setOnMarkerClickListener { marker ->
            Snackbar.make(rootView, "${marker.tag} marker tapped", LENGTH_SHORT).show()

            false // Allow default animation to occur.
        }

//        // Does not exhibit bug
//        googleMap.setOnMarkerClickListener { marker ->
//            Snackbar.make(rootView, "${marker.tag} marker tapped", LENGTH_SHORT).show()
//
//            true // Prevent default animation from occurring.
//        }

//        // Does not exhibit bug
//        googleMap.setOnMarkerClickListener { marker ->
//            Snackbar.make(rootView, "${marker.tag} marker tapped", LENGTH_SHORT).show()
//
//            // Manually perform animation:
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
//
//            true // Prevent default animation from occurring.
//        }
    }

}

private fun GoogleMap.addMarker(latLng: LatLng, hue: Float, tag: String) {
    this.addMarker(MarkerOptions().position(latLng).icon(defaultMarker(hue)))
        .apply { this.tag = tag }
}

private fun LatLng.neBy(delta: Float) = LatLng(latitude + delta, longitude + delta)
