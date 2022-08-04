package pro.msoft.coordonatele;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ScannerActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;
    TextView textView1, textView2, textView3, textView4, textView5,textView6,textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);



        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocation = findViewById(R.id.showLocation);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        textView1 = findViewById(R.id.text_view_1);
        textView2 = findViewById(R.id.text_view_2);
        textView3 = findViewById(R.id.text_view_3);
        textView4 = findViewById(R.id.text_view_4);
        textView5 = findViewById(R.id.text_view_5);
        textView6 = findViewById(R.id.text_view_6);
        textView7 = findViewById(R.id.text_view_7);


        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    clear();
                    getLocation();
                }
            }
        });
    }
    private void OnGPS() {
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
                ScannerActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ScannerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {

                try {
                    Geocoder geocoder = new Geocoder(ScannerActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            locationGPS.getLatitude(), locationGPS.getLongitude(), 1);
                    textView1.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Latitudine:</b><br></font>" + addresses.get(0).getLatitude()
                    ));
                    textView2.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Longitudene:</b><br></font>" + addresses.get(0).getLongitude()
                    ));
                    textView3.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Country name:</b><br></font>" + addresses.get(0).getCountryName()
                    ));
                    textView4.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Locality:</b><br></font>" + addresses.get(0).getLocality()
                    ));
                    textView5.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Sub Localitate :</b><br></font>" + addresses.get(0).getSubLocality()
                    ));
                    textView6.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Cod Postal:</b><br></font>" + addresses.get(0).getPostalCode()
                                    +", "+addresses.get(0).getFeatureName()+", ?: "+addresses.get(0).getThoroughfare()
                    ));
                    textView7.setText(Html.fromHtml(
                            "<font color = '#6200EE'><b> Address:</b><br></font>" + addresses.get(0).getAddressLine(0)
                    ));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void clear(){
        textView1.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");
        textView5.setText("");
        textView6.setText("");
        textView7.setText("");
    }
}