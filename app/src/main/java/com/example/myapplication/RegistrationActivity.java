package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, password, password2;
    Button registrationbtn, signbtn;
    DatabaseHelper DB;
    Spinner roleU;

    String[] data = {"Администратор", "Читатель"};
    String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        registrationbtn = (Button) findViewById(R.id.registrationbtn);
        signbtn = (Button) findViewById(R.id.signbtn);
        DB = new DatabaseHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleU = findViewById(R.id.role);
        roleU.setAdapter(adapter);
        roleU.setPrompt("Выберить роль");
        roleU.setSelection(1);

        getInfoUsers();

        registrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = name.getText().toString();
                String pass = password.getText().toString();
                String repass = password2.getText().toString();
                role = roleU.getSelectedItem().toString();

                if (user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(RegistrationActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {Boolean checkuser = DB.CheckAll(user, pass);
                        if (checkuser == false) { Boolean insert = DB.insertDB(user, pass, role);
                            if (insert == true) {
                                if (role.equals("Администратор")) {
                                    Toast.makeText(RegistrationActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                    startActivity(intent);
                                } else if (role.equals("Читатель")) {
                                    Toast.makeText(RegistrationActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), WallActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthorActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getInfoUsers(){
        Cursor res = DB.getData();
        if(res.getCount() == 0){
            Toast.makeText(this,"Пусто", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()){
            buffer.append("Логин: ").append(res.getString(0)).append("\n");
            buffer.append("Пароль: ").append(res.getString(1)).append("\n");
            buffer.append("Роль: ").append(res.getString(2)).append("\n\n");
        }

    }
}