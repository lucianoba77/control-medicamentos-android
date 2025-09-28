package com.controlmedicamentos.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.controlmedicamentos.myapplication.R;

public class AjustesActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etEmail, etTelefono, etEdad;
    private TextInputLayout tilNombre, tilEmail, tilTelefono, tilEdad;
    private Switch switchNotificaciones, switchVibracion, switchSonido;
    private SeekBar seekBarVolumen, seekBarRepeticiones;
    private TextView tvVolumen, tvRepeticiones, tvDiasAntelacion;
    private MaterialButton btnGuardar, btnVolver, btnDiasAntelacion, btnResetearDatos;

    private SharedPreferences preferences;
    private int diasAntelacionStock = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        inicializarVistas();
        cargarPreferencias();
        configurarListeners();
    }

    private void inicializarVistas() {
        // Campos de usuario
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        etEdad = findViewById(R.id.etEdad);
        tilNombre = findViewById(R.id.tilNombre);
        tilEmail = findViewById(R.id.tilEmail);
        tilTelefono = findViewById(R.id.tilTelefono);
        tilEdad = findViewById(R.id.tilEdad);

        // Switches de configuración
        switchNotificaciones = findViewById(R.id.switchNotificaciones);
        switchVibracion = findViewById(R.id.switchVibracion);
        switchSonido = findViewById(R.id.switchSonido);

        // SeekBars
        seekBarVolumen = findViewById(R.id.seekBarVolumen);
        seekBarRepeticiones = findViewById(R.id.seekBarRepeticiones);

        // TextViews
        tvVolumen = findViewById(R.id.tvVolumen);
        tvRepeticiones = findViewById(R.id.tvRepeticiones);
        tvDiasAntelacion = findViewById(R.id.tvDiasAntelacion);

        // Botones
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        btnDiasAntelacion = findViewById(R.id.btnDiasAntelacion);
        btnResetearDatos = findViewById(R.id.btnResetearDatos);

        // SharedPreferences
        preferences = getSharedPreferences("ControlMedicamentos", MODE_PRIVATE);
    }

    private void cargarPreferencias() {
        // Cargar datos del usuario
        etNombre.setText(preferences.getString("nombre", ""));
        etEmail.setText(preferences.getString("email", ""));
        etTelefono.setText(preferences.getString("telefono", ""));
        etEdad.setText(String.valueOf(preferences.getInt("edad", 0)));

        // Cargar configuraciones de notificaciones
        switchNotificaciones.setChecked(preferences.getBoolean("notificaciones", true));
        switchVibracion.setChecked(preferences.getBoolean("vibracion", true));
        switchSonido.setChecked(preferences.getBoolean("sonido", true));

        // Cargar configuraciones de volumen y repeticiones
        int volumen = preferences.getInt("volumen", 70);
        int repeticiones = preferences.getInt("repeticiones", 3);
        diasAntelacionStock = preferences.getInt("dias_antelacion_stock", 3);

        seekBarVolumen.setProgress(volumen);
        seekBarRepeticiones.setProgress(repeticiones);

        actualizarTextos();
    }

    private void actualizarTextos() {
        tvVolumen.setText("Volumen: " + seekBarVolumen.getProgress() + "%");
        tvRepeticiones.setText("Repeticiones: " + seekBarRepeticiones.getProgress());
        tvDiasAntelacion.setText("Días de antelación: " + diasAntelacionStock);
    }

    private void configurarListeners() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarConfiguracion();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDiasAntelacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoDiasAntelacion();
            }
        });

        btnResetearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoResetear();
            }
        });

        seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvVolumen.setText("Volumen: " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarRepeticiones.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRepeticiones.setText("Repeticiones: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void guardarConfiguracion() {
        SharedPreferences.Editor editor = preferences.edit();

        // Guardar datos del usuario
        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("email", etEmail.getText().toString());
        editor.putString("telefono", etTelefono.getText().toString());
        editor.putInt("edad", Integer.parseInt(etEdad.getText().toString()));

        // Guardar configuraciones
        editor.putBoolean("notificaciones", switchNotificaciones.isChecked());
        editor.putBoolean("vibracion", switchVibracion.isChecked());
        editor.putBoolean("sonido", switchSonido.isChecked());
        editor.putInt("volumen", seekBarVolumen.getProgress());
        editor.putInt("repeticiones", seekBarRepeticiones.getProgress());
        editor.putInt("dias_antelacion_stock", diasAntelacionStock);

        editor.apply();

        Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
    }

    private void mostrarDialogoDiasAntelacion() {
        String[] opciones = {"1 día", "2 días", "3 días", "5 días", "7 días"};
        int[] valores = {1, 2, 3, 5, 7};

        new AlertDialog.Builder(this)
                .setTitle("Días de antelación para stock bajo")
                .setSingleChoiceItems(opciones, getIndiceDiasAntelacion(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diasAntelacionStock = valores[which];
                        actualizarTextos();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private int getIndiceDiasAntelacion() {
        switch (diasAntelacionStock) {
            case 1: return 0;
            case 2: return 1;
            case 3: return 2;
            case 5: return 3;
            case 7: return 4;
            default: return 2;
        }
    }

    private void mostrarDialogoResetear() {
        new AlertDialog.Builder(this)
                .setTitle("Resetear Datos")
                .setMessage("¿Estás seguro de que quieres resetear todos los datos? Esta acción no se puede deshacer.")
                .setPositiveButton("Resetear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetearDatos();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void resetearDatos() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Resetear datos de prueba
        // DatosPrueba.resetearDatos();

        Toast.makeText(this, "Datos reseteados", Toast.LENGTH_SHORT).show();
        cargarPreferencias();
    }
}
