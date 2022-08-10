package pro.msoft.coordonatele;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pro.msoft.coordonatele.util.Timer;

public class MainActivity extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener{

    LocationManager locationManager;

    TextView textViewNumeLocalitate, textViewNumeCTA, textViewTitluDisponibil, textViewTitluTehnologia, textViewTitlu;
    Spinner spinnerSelectTehnologie, spinnerSelectPopSwitch;
    RecyclerView listViewCutiDisponibile;
    Button btn_refrash_localitate, btn;
    ProgressBar progressLoading;
    String localitete;


    String[] tip = {"CTA", "POP", "SWITCH" ,"DULAP"};
    String[] tehn = {"Telefonie fixa", "FTTH", "GPON" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressLoading = findViewById(R.id.progressLoading);
        spinnerSelectTehnologie = findViewById(R.id.spinnerSelectTehnologie);
        spinnerSelectPopSwitch = findViewById(R.id.spinnerSelectPopSwitch);
        listViewCutiDisponibile = findViewById(R.id.listViewCutiDisponibile);
        textViewNumeLocalitate = findViewById(R.id.textViewNumeLocalitate);
        textViewNumeCTA = findViewById(R.id.textViewNumeCTA);
        textViewTitluDisponibil = findViewById(R.id.textViewTitluDisponibil);
        textViewTitluTehnologia = findViewById(R.id.textViewTitluTehnologia);
        textViewTitlu = findViewById(R.id.textViewTitlu);
        btn_refrash_localitate = findViewById(R.id.btn_refrash_localitate);
        btn = findViewById(R.id.btn);

        progressLoading.setVisibility(View.VISIBLE);
        hiddenAll();
        getLocation();
        progressLoading.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Timer.showLoadingDialog(MainActivity.this, 500);
                progressLoading.setVisibility(View.VISIBLE);
                if(Timer.loading(3000)){
                    hiddenAll();
                }

                progressLoading.setVisibility(View.INVISIBLE);
            }
        });

        btn_refrash_localitate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hiddenAll();
                progressLoading.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            onGPS();
                        } else {
                            getLocation();
                            progressLoading.setVisibility(View.INVISIBLE);
                        }
                    }
                }, 1500);
            }
        });

    }

    private void hiddenAll() {
        textViewTitluDisponibil.setText("");
        textViewTitluTehnologia.setText("");
        spinnerSelectTehnologie.setVisibility(View.INVISIBLE);
        textViewNumeLocalitate.setText("");
        textViewTitlu.setText("");
        textViewNumeCTA.setText("");
        spinnerSelectPopSwitch.setVisibility(View.INVISIBLE);
    }


    private void getLocation() {

            if (ActivityCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    onGPS();
                } else {
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        double lat = locationGPS.getLatitude();
                        double longi = locationGPS.getLongitude();

                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
                            localitete = addresses.get(0).getLocality().toUpperCase();

                            textViewNumeLocalitate.setText(localitete);
                            loadData(localitete);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        progressLoading.setVisibility(View.INVISIBLE);
    }


    private void loadData(String localitete) {
        String[] teh = {"Alege... ","Telefonie fixa", "GPON"};
        if(localitete.equals("HECIUL VECHI")){
            textViewNumeCTA.setText("CTA-54");
            ArrayAdapter aTip = new ArrayAdapter(this,android.R.layout.simple_spinner_item, teh);
            aTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSelectTehnologie.setAdapter(aTip);
        }

        if(localitete.equals("MOUNTAIN VIEW")){

            textViewTitluDisponibil.setText("Disponibil: ");
            textViewNumeCTA.setText("CTA-54");

            textViewTitluTehnologia.setText("Selecteaza tehnologia:");
            spinnerSelectTehnologie.setVisibility(View.VISIBLE);

            ArrayAdapter aTip = new ArrayAdapter(this,android.R.layout.simple_spinner_item, teh);
            aTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSelectTehnologie.setAdapter(aTip);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> aTip, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), tehn[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), tehn.length, Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_add_cta_dulap){
            startActivity(new Intent(MainActivity.this, AddDulapActivity.class));
        }
        if(item.getItemId() == R.id.menu_add_distributie){
            startActivity(new Intent(MainActivity.this, DistributieActivity.class));
        }
        if(item.getItemId() == R.id.menu_add_abonat){
            startActivity(new Intent(MainActivity.this, AddAbonatActivity.class));
        }
        if(item.getItemId() == R.id.menu_cauta_abonat){
            startActivity(new Intent(MainActivity.this, CautareAbonatActivity.class));
        }
        if(item.getItemId() == R.id.menu_scanare_local){
            startActivity(new Intent(MainActivity.this, ScannerActivity.class));
        }
        if(item.getItemId() == R.id.menu_profile){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu,  menu);
        return super.onCreateOptionsMenu(menu);
    }


}