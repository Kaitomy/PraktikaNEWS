package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;


public class MainActivity extends AppCompatActivity {

    private Button btnScan;
    private Button btnSkip;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        btnSkip = findViewById(R.id.btnSkip);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("ErrorAUTH", errString.toString());

        }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Успешно!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);

            }
                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Log.e("FailedAUTH", "FAIL!!!");
                }
                });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Отмена")
                .build();
        btnScan.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
        btnSkip.setOnClickListener(view -> {


            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);

        });
    }
}