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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddDulapActivity extends AppCompatActivity {

    private Spinner selectTip;
    private Button btnClear, btnSave, btnScanner;
    private EditText editTextDenumire, editTextLong, editTextLatit, editTextLocalitate, editTextAdresa;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    String[] tip = {"CTA", "POP", "SWITCH", "DULAP" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dulap);

        selectTip = findViewById(R.id.selectTip);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_save);
        btnScanner = findViewById(R.id.btn_scanner);
        editTextDenumire = findViewById(R.id.editTextDenumire);
        editTextLong = findViewById(R.id.editTextLong);
        editTextLatit = findViewById(R.id.editTextLatit);
        editTextLocalitate = findViewById(R.id.editTextLocalitate);
        editTextAdresa = findViewById(R.id.editTextAdresa);


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, tip);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTip.setAdapter(aa);

//            @Override
//        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//            Toast.makeText(getApplicationContext(), tip[position] , Toast.LENGTH_LONG).show();
//        }


        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    clear();
                    getLocation();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                Toast.makeText(getApplicationContext(), "Cimpurile au fost curatate cu succes!", Toast.LENGTH_SHORT).show();
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
                AddDulapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddDulapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {

                try {
                    Geocoder geocoder = new Geocoder(AddDulapActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            locationGPS.getLatitude(), locationGPS.getLongitude(), 1);

//                    editTextAdresa.setText(addresses.get(0).getCountryName());
                    editTextLocalitate.setText( addresses.get(0).getLocality());
                    editTextAdresa.setText( addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                editTextLatit.setText(String.valueOf(lat));
                editTextLong.setText(String.valueOf(longi));
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void clear(){
        editTextLong.setText("");
        editTextLatit.setText("");
        editTextLocalitate.setText("");
        editTextAdresa.setText("");
    }

}