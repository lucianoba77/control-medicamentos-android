package com.controlmedicamentos.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.controlmedicamentos.myapplication.adapters.MedicamentoAdapter;
import com.controlmedicamentos.myapplication.models.Medicamento;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MedicamentoAdapter.OnMedicamentoClickListener {

    private RecyclerView rvMedicamentos;
    private MaterialButton btnNuevaMedicina, btnBotiquin, btnHistorial, btnAjustes, btnLogout;
    private MedicamentoAdapter adapter;
    private List<Medicamento> medicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        inicializarVistas();

        // Configurar RecyclerView
        configurarRecyclerView();

        // Cargar datos de prueba
        cargarDatosPrueba();

        // Configurar navegación
        configurarNavegacion();
    }

    private void inicializarVistas() {
        rvMedicamentos = findViewById(R.id.rvMedicamentos);
        btnNuevaMedicina = findViewById(R.id.btnNuevaMedicina);
        btnBotiquin = findViewById(R.id.btnBotiquin);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnAjustes = findViewById(R.id.btnAjustes);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void configurarRecyclerView() {
        medicamentos = new ArrayList<>();
        adapter = new MedicamentoAdapter(this, medicamentos);
        adapter.setOnMedicamentoClickListener(this);

        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));
        rvMedicamentos.setAdapter(adapter);
    }

    private void cargarDatosPrueba() {
        // Crear medicamentos de prueba
        medicamentos.clear();

        // Medicamento 1: Paracetamol
        Medicamento paracetamol = new Medicamento(
                "1", "Paracetamol 500mg", "Comprimidos", 3, "08:00",
                "Dolor de cabeza", 30, getResources().getColor(R.color.medicamento_azul, null), 7
        );
        paracetamol.setDetalles("Tomar con agua");
        medicamentos.add(paracetamol);

        // Medicamento 2: Ibuprofeno
        Medicamento ibuprofeno = new Medicamento(
                "2", "Ibuprofeno 400mg", "Comprimidos", 2, "12:00",
                "Inflamación", 20, getResources().getColor(R.color.medicamento_rojo, null), 5
        );
        ibuprofeno.setDetalles("Tomar con comida");
        medicamentos.add(ibuprofeno);

        // Medicamento 3: Jarabe
        Medicamento jarabe = new Medicamento(
                "3", "Jarabe para la tos", "Jarabe", 3, "09:00",
                "Tos", 1, getResources().getColor(R.color.medicamento_verde, null), 10
        );
        jarabe.setDetalles("Agitar antes de usar");
        medicamentos.add(jarabe);

        // Actualizar RecyclerView
        adapter.actualizarMedicamentos(medicamentos);
    }

    private void configurarNavegacion() {
        btnNuevaMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implementar NuevaMedicinaActivity
                Toast.makeText(MainActivity.this, "Nueva Medicina - En desarrollo", Toast.LENGTH_SHORT).show();
            }
        });

        btnBotiquin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implementar BotiquinActivity
                Toast.makeText(MainActivity.this, "Botiquín - En desarrollo", Toast.LENGTH_SHORT).show();
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implementar HistorialActivity
                Toast.makeText(MainActivity.this, "Historial - En desarrollo", Toast.LENGTH_SHORT).show();
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implementar AjustesActivity
                Toast.makeText(MainActivity.this, "Ajustes - En desarrollo", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Implementar métodos de la interfaz
    @Override
    public void onTomadoClick(Medicamento medicamento) {
        // Consumir dosis
        medicamento.consumirDosis();

        // Mostrar confirmación
        Toast.makeText(this, "✓ " + medicamento.getNombre() + " marcado como tomado", Toast.LENGTH_SHORT).show();

        // Verificar si se completó el tratamiento
        if (medicamento.estaAgotado()) {
            Toast.makeText(this, "¡Tratamiento de " + medicamento.getNombre() + " completado!", Toast.LENGTH_LONG).show();
            // Remover de la lista
            medicamentos.remove(medicamento);
            // Actualizar RecyclerView
            adapter.actualizarMedicamentos(medicamentos);
        } else {
            // Solo actualizar el item específico
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMedicamentoClick(Medicamento medicamento) {
        // Mostrar detalles del medicamento
        Toast.makeText(this, "Detalles de " + medicamento.getNombre(), Toast.LENGTH_SHORT).show();
        // TODO: Implementar pantalla de detalles
    }
}