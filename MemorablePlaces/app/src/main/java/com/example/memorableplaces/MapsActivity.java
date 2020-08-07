package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    Intent mapsActivityIntent;

    int theExtra2;

    Geocoder geocoder;


    private GoogleMap mMap;

    public void centreMapOnStart(Location location,String title){
        if (location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centreMapOnStart(lastKnownLocation, "Your Location");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapsActivityIntent = getIntent();
        theExtra2 = mapsActivityIntent.getIntExtra("position",0);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        setLocationListener();
        setLongClickListener();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else{
            if(theExtra2 == -1){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,200,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            centreMapOnStart(lastKnownLocation,"You are here");}
        }




    }

    private void setLongClickListener() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"Location saved!", LENGTH_SHORT).show();
                String address = "";

                try {

                    List<Address> listAdddresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

                    if (listAdddresses != null && listAdddresses.size() > 0) {
                        if (listAdddresses.get(0).getThoroughfare() != null) {
                            if (listAdddresses.get(0).getSubThoroughfare() != null) {
                                address += listAdddresses.get(0).getSubThoroughfare() + " ";
                            }
                            address += listAdddresses.get(0).getThoroughfare();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (address.equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
                    address += sdf.format(new Date());
                }
                Log.i("info",address);

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(address)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                MainActivity.arrayList.add(address);
                MainActivity.latLngs.add(latLng);
                try {
                    MainActivity.sharedPreferences.edit().putString("list",MainActivity.objectSerializer.serialize(MainActivity.arrayList)).apply();
                    ArrayList<Double> lat = new ArrayList<Double>();
                    for(int i=0;i<MainActivity.latLngs.size();i++){

                        lat.add(MainActivity.latLngs.get(i).latitude);
                    }
                    MainActivity.sharedPreferences.edit().putString("lat",MainActivity.objectSerializer.serialize(lat)).apply();
                    ArrayList<Double> lon = new ArrayList<Double>();
                    for(int i=0;i<MainActivity.latLngs.size();i++){
                        lon.add(MainActivity.latLngs.get(i).latitude);
                    }
                    MainActivity.sharedPreferences.edit().putString("lon",MainActivity.objectSerializer.serialize(lon)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainActivity.arrayAdapter.notifyDataSetChanged();

            }
        });
    }

    private void setLocationListener() {
        if(theExtra2 == -1){

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    centreMapOnStart(location,"You are here");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }else{
           addPlaceMarker();
        }
    }

    private void addPlaceMarker() {
        Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
        placeLocation.setLatitude(MainActivity.latLngs.get(mapsActivityIntent.getIntExtra("position",0)).latitude);
        placeLocation.setLongitude(MainActivity.latLngs.get(mapsActivityIntent.getIntExtra("position",0)).longitude);

        centreMapOnStart(placeLocation, MainActivity.arrayList.get(mapsActivityIntent.getIntExtra("position",0)));

    }


}
