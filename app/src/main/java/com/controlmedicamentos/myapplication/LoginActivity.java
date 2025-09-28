package com.controlmedicamentos.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        inicializarVistas();

        // Configurar listeners
        configurarListeners();
    }

    private void inicializarVistas() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void configurarListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarRegistro();
            }
        });
    }

    private void realizarLogin() {
        // Obtener datos de los campos
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar campos
        if (!validarCampos(email, password)) {
            return;
        }

        // Verificar credenciales (por ahora datos de prueba)
        if (verificarCredenciales(email, password)) {
            // Login exitoso
            Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();

            // Navegar a MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Cerrar LoginActivity
        } else {
            // Credenciales incorrectas
            Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarCampos(String email, String password) {
        boolean valido = true;

        // Validar email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Ingresa tu email");
            valido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Ingresa un email válido");
            valido = false;
        } else {
            etEmail.setError(null);
        }

        // Validar contraseña
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Ingresa tu contraseña");
            valido = false;
        } else if (password.length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            valido = false;
        } else {
            etPassword.setError(null);
        }

        return valido;
    }

    private boolean verificarCredenciales(String email, String password) {
        // Datos de prueba para testing
        // En una app real, esto se haría con una base de datos o API

        if (email.equals("usuario@test.com") && password.equals("123456")) {
            return true;
        }

        if (email.equals("admin@test.com") && password.equals("admin123")) {
            return true;
        }

        return false;
    }

    private void mostrarRegistro() {
        // Por ahora solo mostramos un mensaje
        // En el futuro se puede implementar una pantalla de registro
        Toast.makeText(this, "Función de registro en desarrollo", Toast.LENGTH_SHORT).show();
    }
}