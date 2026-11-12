package com.example.gradingmadeeasy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(
                "GradingMadeEasyAccount",
                MODE_PRIVATE
        );

        showLoginScreen();
    }

    // ---------- LOGIN SCREEN ----------
    private void showLoginScreen() {

        setContentView(R.layout.malonzo_login_screen);

        EditText username = findViewById(R.id.loginUsername);
        EditText password = findViewById(R.id.loginPassword);

        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnGoRegister);

        loginButton.setOnClickListener(v -> {

            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty()) {
                username.setError("Enter username");
                username.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Enter password");
                password.requestFocus();
                return;
            }

            String savedUser = preferences.getString("username", "");
            String savedPass = preferences.getString("password", "");

            if (!savedUser.isEmpty()
                    && user.equals(savedUser)
                    && pass.equals(savedPass)) {

                Toast.makeText(this,
                        "Login Successful!",
                        Toast.LENGTH_SHORT).show();

                showDashboard();

            } else {

                Toast.makeText(this,
                        "Invalid username or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> showRegisterScreen());
    }

    // ---------- REGISTER SCREEN ----------
    private void showRegisterScreen() {

        setContentView(R.layout.malonzo_register_screen);

        EditText username = findViewById(R.id.registerUsername);
        EditText password = findViewById(R.id.registerPassword);

        Button registerButton = findViewById(R.id.btnRegister);
        Button loginButton = findViewById(R.id.btnGoLogin);

        registerButton.setOnClickListener(v -> {

            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty()) {
                username.setError("Enter username");
                username.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Enter password");
                password.requestFocus();
                return;
            }

            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("username", user);
            editor.putString("password", pass);

            editor.apply();

            Toast.makeText(this,
                    "Registration Successful!",
                    Toast.LENGTH_SHORT).show();

            showDashboard();
        });

        loginButton.setOnClickListener(v -> showLoginScreen());
    }

    // ---------- DASHBOARD SCREEN ----------
    private void showDashboard() {

        setContentView(R.layout.activity_dashboard);
    }

    // ---------- BACK BUTTON ----------
    @Override
    public void onBackPressed() {
        showLoginScreen();
    }
}
