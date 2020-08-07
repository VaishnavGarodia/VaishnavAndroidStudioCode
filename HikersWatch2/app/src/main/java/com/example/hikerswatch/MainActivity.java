package com.example.hikerswatch;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    TextView textView;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        textView = findViewById(R.id.textView3);
        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                message = "";

                message += "Latitude:  " + location.getLatitude() + "\r\n";
                message += "\r\n"+ "Longitude:  " + location.getLongitude() + "\r\n";
                message +="\r\n"+  "Accuracy:  " + location.getAccuracy() + "\r\n";
                message +="\r\n"+ "Altitude:  " + location.getAltitude() + "\r\n";

                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String address1 = "";
                    String address2 = "";
                    if (addressList != null && addressList.size() > 0) {

                        if(addressList.get(0).getThoroughfare() != null ){
                            address1 += addressList.get(0).getThoroughfare() + " ";
                        }


                        if(addressList.get(0).getLocality() != null ){
                            address1 += addressList.get(0).getLocality() + " ";
                        }

                        if(addressList.get(0).getAdminArea() != null ){
                            address2 += addressList.get(0).getAdminArea() + " " ;
                        }
                        if(addressList.get(0).getPostalCode() != null ){
                            address2 += addressList.get(0).getPostalCode() ;
                        }

                    }

                    message += "\r\n" + "Address:  "+ "\r\n";
                    message += address1 + "\r\n";
                    message += address2;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                textView.setText(message);


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
        if (Build.VERSION.SDK_INT < 23) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }

            }

        }}}