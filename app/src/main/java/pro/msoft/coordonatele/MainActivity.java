package pro.msoft.coordonatele;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_add_cta_dulap){
            startActivity(new Intent(MainActivity.this, AddDulapActivity.class));
        }
        if(item.getItemId() == R.id.menu_add_distributie){
            startActivity(new Intent(MainActivity.this, DistributieActivity.class));
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