package com.controlmedicamentos.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.controlmedicamentos.myapplication.R;
import com.controlmedicamentos.myapplication.models.Medicamento;
import com.controlmedicamentos.myapplication.utils.DatosPrueba;
import java.util.Calendar;

public class NuevaMedicinaActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etAfeccion, etDetalles;
    private TextInputLayout tilNombre, tilAfeccion;
    private MaterialButton btnGuardar, btnCancelar, btnSeleccionarColor, btnFechaVencimiento;
    private android.widget.Spinner spinnerPresentacion;
    private TextInputEditText etTomasDiarias, etHorarioPrimeraToma, etStockInicial, etDiasTratamiento;
    private TextInputLayout tilTomasDiarias, tilHorarioPrimeraToma, tilStockInicial, tilDiasTratamiento;

    private int colorSeleccionado = R.color.medicamento_azul;
    private Calendar fechaVencimiento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_medicina);

        inicializarVistas();
        configurarSpinner();
        configurarListeners();
    }

    private void inicializarVistas() {
        etNombre = findViewById(R.id.etNombre);
        etAfeccion = findViewById(R.id.etAfeccion);
        etDetalles = findViewById(R.id.etDetalles);
        tilNombre = findViewById(R.id.tilNombre);
        tilAfeccion = findViewById(R.id.tilAfeccion);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnSeleccionarColor = findViewById(R.id.btnSeleccionarColor);
        btnFechaVencimiento = findViewById(R.id.btnFechaVencimiento);

        spinnerPresentacion = findViewById(R.id.spinnerPresentacion);
        etTomasDiarias = findViewById(R.id.etTomasDiarias);
        etHorarioPrimeraToma = findViewById(R.id.etHorarioPrimeraToma);
        etStockInicial = findViewById(R.id.etStockInicial);
        etDiasTratamiento = findViewById(R.id.etDiasTratamiento);

        tilTomasDiarias = findViewById(R.id.tilTomasDiarias);
        tilHorarioPrimeraToma = findViewById(R.id.tilHorarioPrimeraToma);
        tilStockInicial = findViewById(R.id.tilStockInicial);
        tilDiasTratamiento = findViewById(R.id.tilDiasTratamiento);
    }

    private void configurarSpinner() {
        String[] presentaciones = {
                "Comprimidos", "Cápsulas", "Jarabe", "Crema",
                "Pomada", "Spray nasal", "Inyección", "Gotas"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, presentaciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresentacion.setAdapter(adapter);
    }

    private void configurarListeners() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarMedicamento();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSeleccionarColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorColor();
            }
        });

        btnFechaVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorFecha();
            }
        });
    }

    private void guardarMedicamento() {
        if (validarFormulario()) {
            Medicamento medicamento = crearMedicamento();
            DatosPrueba.agregarMedicamento(medicamento);

            Toast.makeText(this, "Medicamento guardado exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validarFormulario() {
        boolean valido = true;

        if (TextUtils.isEmpty(etNombre.getText())) {
            tilNombre.setError("El nombre es requerido");
            valido = false;
        } else {
            tilNombre.setError(null);
        }

        if (TextUtils.isEmpty(etAfeccion.getText())) {
            tilAfeccion.setError("La afección es requerida");
            valido = false;
        } else {
            tilAfeccion.setError(null);
        }

        if (TextUtils.isEmpty(etTomasDiarias.getText())) {
            tilTomasDiarias.setError("Las tomas diarias son requeridas");
            valido = false;
        } else {
            tilTomasDiarias.setError(null);
        }

        if (TextUtils.isEmpty(etHorarioPrimeraToma.getText())) {
            tilHorarioPrimeraToma.setError("El horario es requerido");
            valido = false;
        } else {
            tilHorarioPrimeraToma.setError(null);
        }

        if (TextUtils.isEmpty(etStockInicial.getText())) {
            tilStockInicial.setError("El stock inicial es requerido");
            valido = false;
        } else {
            tilStockInicial.setError(null);
        }

        return valido;
    }

    private Medicamento crearMedicamento() {
        String nombre = etNombre.getText().toString();
        String afeccion = etAfeccion.getText().toString();
        String detalles = etDetalles.getText().toString();
        String presentacion = spinnerPresentacion.getSelectedItem().toString();
        int tomasDiarias = Integer.parseInt(etTomasDiarias.getText().toString());
        String horarioPrimeraToma = etHorarioPrimeraToma.getText().toString();
        int stockInicial = Integer.parseInt(etStockInicial.getText().toString());
        int diasTratamiento = Integer.parseInt(etDiasTratamiento.getText().toString());

        // Generar ID único
        String id = String.valueOf(System.currentTimeMillis());

        Medicamento medicamento = new Medicamento(
                id, nombre, presentacion, tomasDiarias, horarioPrimeraToma,
                afeccion, stockInicial, colorSeleccionado, diasTratamiento
        );

        medicamento.setDetalles(detalles);

        if (fechaVencimiento != null) {
            medicamento.setFechaVencimiento(fechaVencimiento.getTime());
        }

        return medicamento;
    }

    private void mostrarSelectorColor() {
        // TODO: Implementar selector de color
        Toast.makeText(this, "Selector de color - Próximamente", Toast.LENGTH_SHORT).show();
    }

    private void mostrarSelectorFecha() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        fechaVencimiento = Calendar.getInstance();
                        fechaVencimiento.set(year, month, dayOfMonth);
                        btnFechaVencimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}