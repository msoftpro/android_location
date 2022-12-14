package pro.msoft.coordonatele;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        }, 1500);

    }
}