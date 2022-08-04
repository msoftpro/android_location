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
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LoadingAppActivity extends AppCompatActivity {
    ImageView imageViewLoading;
    TextView textViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_app);
        imageViewLoading = findViewById(R.id.imageViewLoading);
        textViewLoading = findViewById(R.id.textViewLoading);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_app);
        textViewLoading.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getSharedPreferences("my_app", MODE_PRIVATE).getString("token", "").trim().isEmpty()){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        }, 3000);

    }
}