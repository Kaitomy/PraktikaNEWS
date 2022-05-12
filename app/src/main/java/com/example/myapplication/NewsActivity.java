package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsActivity extends AppCompatActivity {

    EditText txtNewsName, txtNewsDescription;
    Button btnInsert,btnSelect,btnUpdate,btnDelete;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        txtNewsName = findViewById(R.id.txtNewsName);
        txtNewsDescription = findViewById(R.id.txtNewsDescription);
        btnSelect = findViewById(R.id.btnSelect);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        databaseHelper = new DatabaseHelper(this);

        btnInsert.setOnClickListener(v -> {
            Boolean checkInsertData = databaseHelper.insertNews(txtNewsName.getText().toString(),txtNewsDescription.getText().toString());
            if (checkInsertData)
                Toast.makeText(getApplicationContext(),"Новость добавлена", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();

        });
        btnUpdate.setOnClickListener(v -> {
            Boolean checkUpdateData = databaseHelper.updateNews(txtNewsName.getText().toString(),txtNewsDescription.getText().toString());
            if (checkUpdateData)
                Toast.makeText(getApplicationContext(),"Новость изменена", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v -> {
            Boolean checkDeleteData = databaseHelper.deleteNews(txtNewsName.getText().toString());
            if (checkDeleteData)
                Toast.makeText(getApplicationContext(),"Новость удалена", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();
        });

        btnSelect.setOnClickListener(v -> {
            Cursor res = databaseHelper.getDataNews();
            if(res.getCount() == 0){
                Toast.makeText(this,"Нет данных", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()){
                buffer.append("Название: ").append(res.getString(0)).append("\n");
                buffer.append("Описание: ").append(res.getString(1)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Новости");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }

}