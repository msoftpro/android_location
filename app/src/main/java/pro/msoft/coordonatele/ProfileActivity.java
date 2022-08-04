package pro.msoft.coordonatele;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_edit_profile){
//            startActivity(new Intent(ProfileActivity.this, AddDulapActivity.class));
        }
        if(item.getItemId() == R.id.menu_logout){
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        SharedPreferences sP = getSharedPreferences("my_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.remove("token");
        editor.commit();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu,  menu);
        return super.onCreateOptionsMenu(menu);
    }
}