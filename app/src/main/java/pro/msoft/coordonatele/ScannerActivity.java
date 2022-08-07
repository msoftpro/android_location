package pro.msoft.coordonatele;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ScannerActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;

    MapView mView;
    GoogleMap googleMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mView = findViewById(R.id.map);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        if(mView != null){
            mView.onCreate(null);
            mView.onResume();
            mView.getMapAsync(this);
        }

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocation = findViewById(R.id.showLocation);
        btnGetLocation = findViewById(R.id.btnGetLocation);



        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    onGPS();
                } else {
                    getLocation();
                }
            }
        });
    }
    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                ScannerActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                ScannerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
           Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                setMarcher(locationGPS);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setMarcher(Location location){
        mView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: ");
                Toast.makeText(ScannerActivity.this, "googleMap.getMapType()", Toast.LENGTH_SHORT).show();
                try {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Geocoder geocoder = new Geocoder(ScannerActivity.this, Locale.getDefault());

                    try {
                        sleep(500);
                        Toast.makeText(ScannerActivity.this, geocoder.toString(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<Address> addresses = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);

                    try {
                        sleep(500);
                        Toast.makeText(ScannerActivity.this, addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MapsInitializer.initialize(getBaseContext());
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title(addresses.get(0).getLocality())
                            .snippet(addresses.get(0).getAddressLine(0)));

                    CameraPosition liberty = CameraPosition.builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(25).bearing(0).tilt(45).build();
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        MapsInitializer.initialize(getBaseContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(47.8004600, 28.10813833333)).title("Heciu meu").snippet("Casa"));

        CameraPosition liberty = CameraPosition.builder().target(new LatLng(47.8004600, 28.10813833333)).zoom(20).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

    }


}

// if (locationGPS != null) {

//                try {
//                    Geocoder geocoder = new Geocoder(ScannerActivity.this, Locale.getDefault());
//                    List<Address> addresses = geocoder.getFromLocation(
//                            locationGPS.getLatitude(), locationGPS.getLongitude(), 1);
//                    textView1.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Latitudine:</b><br></font>" + addresses.get(0).getLatitude()
//                    ));
//                    textView2.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Longitudene:</b><br></font>" + addresses.get(0).getLongitude()
//                    ));
//                    textView3.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Country name:</b><br></font>" + addresses.get(0).getCountryName()
//                    ));
//                    textView4.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Locality:</b><br></font>" + addresses.get(0).getLocality()
//                    ));
//                    textView5.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Sub Localitate :</b><br></font>" + addresses.get(0).getSubLocality()
//                    ));
//                    textView6.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Cod Postal:</b><br></font>" + addresses.get(0).getPostalCode()
//                                    +", "+addresses.get(0).getFeatureName()+", ?: "+addresses.get(0).getThoroughfare()
//                    ));
//                    textView7.setText(Html.fromHtml(
//                            "<font color = '#6200EE'><b> Address:</b><br></font>" + addresses.get(0).getAddressLine(0)
//                    ));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//         }