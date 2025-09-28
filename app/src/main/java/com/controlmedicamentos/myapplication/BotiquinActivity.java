package com.controlmedicamentos.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.controlmedicamentos.myapplication.adapters.BotiquinAdapter;
import com.controlmedicamentos.myapplication.models.Medicamento;
import com.controlmedicamentos.myapplication.utils.DatosPrueba;
import java.util.List;
import android.content.Intent;

public class BotiquinActivity extends AppCompatActivity implements BotiquinAdapter.OnMedicamentoClickListener {

    private RecyclerView rvMedicamentos;
    private FloatingActionButton fabNuevaMedicina;
    private MaterialButton btnVolver;
    private BotiquinAdapter adapter;
    private List<Medicamento> medicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiquin);

        inicializarVistas();
        configurarRecyclerView();
        cargarMedicamentos();
        configurarListeners();
    }

    private void inicializarVistas() {
        rvMedicamentos = findViewById(R.id.rvMedicamentos);
        fabNuevaMedicina = findViewById(R.id.fabNuevaMedicina);
        btnVolver = findViewById(R.id.btnVolver);
    }

    private void configurarRecyclerView() {
        adapter = new BotiquinAdapter(this, medicamentos);
        adapter.setOnMedicamentoClickListener(this);
        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));
        rvMedicamentos.setAdapter(adapter);
    }

    private void cargarMedicamentos() {
        medicamentos = DatosPrueba.obtenerMedicamentosPrueba(this);
        adapter.actualizarMedicamentos(medicamentos);
    }

    private void configurarListeners() {
        fabNuevaMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BotiquinActivity.this, NuevaMedicinaActivity.class);
                startActivity(intent);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onEditarClick(Medicamento medicamento) {
        mostrarDialogoEditar(medicamento);
    }

    @Override
    public void onEliminarClick(Medicamento medicamento) {
        mostrarDialogoEliminar(medicamento);
    }

    @Override
    public void onAgregarStockClick(Medicamento medicamento) {
        mostrarDialogoAgregarStock(medicamento);
    }

    private void mostrarDialogoEditar(Medicamento medicamento) {
        Toast.makeText(this, "Editar " + medicamento.getNombre() + " - Próximamente", Toast.LENGTH_SHORT).show();
    }

    private void mostrarDialogoEliminar(Medicamento medicamento) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Medicamento")
                .setMessage("¿Estás seguro de que quieres eliminar " + medicamento.getNombre() + "?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (DatosPrueba.eliminarMedicamento(medicamento.getId())) {
                            Toast.makeText(BotiquinActivity.this, "Medicamento eliminado", Toast.LENGTH_SHORT).show();
                            cargarMedicamentos();
                        } else {
                            Toast.makeText(BotiquinActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoAgregarStock(Medicamento medicamento) {
        Toast.makeText(this, "Agregar stock a " + medicamento.getNombre() + " - Próximamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMedicamentos();
    }
}