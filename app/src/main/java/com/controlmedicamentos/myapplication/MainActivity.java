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
import com.controlmedicamentos.myapplication.utils.DatosPrueba;
import android.content.Intent;

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
        // Usar la clase DatosPrueba para obtener medicamentos de prueba
        medicamentos = DatosPrueba.obtenerMedicamentosActivos(this);
        adapter.actualizarMedicamentos(medicamentos);
    }

    private void configurarNavegacion() {
        btnNuevaMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NuevaMedicinaActivity.class);
                startActivity(intent);
            }
        });

        btnBotiquin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BotiquinActivity.class);
                startActivity(intent);
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
                startActivity(intent);
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            // Pausar el medicamento
            medicamento.pausarMedicamento();
            // Actualizar en DatosPrueba
            DatosPrueba.actualizarMedicamento(medicamento);
            // Recargar datos
            cargarDatosPrueba();
        } else {
            // Actualizar en DatosPrueba
            DatosPrueba.actualizarMedicamento(medicamento);
            // Actualizar RecyclerView
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