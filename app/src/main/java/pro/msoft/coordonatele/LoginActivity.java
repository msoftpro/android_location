package pro.msoft.coordonatele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail ,editTextPassword;
    private TextView textResetPassword, textViewRegister;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        textResetPassword = findViewById(R.id.textResetPassword);
        btnLogin = findViewById(R.id.btn_login);


        progressBar.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Copleteaza corect cimpul Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = editTextPassword.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Copleteaza corect cimpul Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                if(email.equals("admin@gmail.com")&& password.equals("12345")){

                    btnLogin.setEnabled(false);
                    SharedPreferences sharedPreferences = getSharedPreferences("my_app", MODE_PRIVATE);
                    SharedPreferences.Editor mySet = sharedPreferences.edit();
                    mySet.putString("token", email);
                    mySet.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Date incorecte", Toast.LENGTH_SHORT).show();
                }

//                SharedPreferences sharedPreferences = getSharedPreferences("my_app", MODE_PRIVATE);
//                SharedPreferences.Editor mySet = sharedPreferences.edit();
//                mySet.putString("token", response.headers().get("Jwt-Token"));
//                mySet.putString("username", response.body().getUsername());
//                mySet.commit();

            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        textResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Pagina inca nu e pregatita", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


    }
}