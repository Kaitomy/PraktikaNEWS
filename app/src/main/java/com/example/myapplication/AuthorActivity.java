package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AuthorActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DatabaseHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")) Toast.makeText(AuthorActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkusernamepass = DB.CheckAll(user, pass);
                    if(checkusernamepass==true){
                        String role = DB.RoleDB(user, pass);
                        Toast.makeText(AuthorActivity.this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
                        if (role.equals("Администратор")){
                            Intent intent  = new Intent(getApplicationContext(), NewsActivity.class);
                            startActivity(intent);
                        }
                        else if (role.equals("Читатель")){
                            Intent intent  = new Intent(getApplicationContext(), WallActivity.class);
                            startActivity(intent);
                        }
                    }else{ Toast.makeText(AuthorActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show(); }
                }
            }
        });
    }
}