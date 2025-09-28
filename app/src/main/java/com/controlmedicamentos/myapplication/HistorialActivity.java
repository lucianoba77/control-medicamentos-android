package com.controlmedicamentos.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.controlmedicamentos.myapplication.adapters.HistorialAdapter;
import com.controlmedicamentos.myapplication.models.Medicamento;
import com.controlmedicamentos.myapplication.utils.DatosPrueba;
import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private BarChart chartAdherencia;
    private RecyclerView rvTratamientosConcluidos;
    private TextView tvEstadisticasGenerales;
    private MaterialButton btnVolver;
    private HistorialAdapter adapter;
    private List<Medicamento> tratamientosConcluidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        inicializarVistas();
        configurarGrafico();
        configurarRecyclerView();
        cargarDatos();
        configurarListeners();
    }

    private void inicializarVistas() {
        chartAdherencia = findViewById(R.id.chartAdherencia);
        rvTratamientosConcluidos = findViewById(R.id.rvTratamientosConcluidos);
        tvEstadisticasGenerales = findViewById(R.id.tvEstadisticasGenerales);
        btnVolver = findViewById(R.id.btnVolver);
    }

    private void configurarGrafico() {
        // Configurar el gráfico de barras
        chartAdherencia.getDescription().setEnabled(false);
        chartAdherencia.setDrawGridBackground(false);
        chartAdherencia.setDrawBarShadow(false);
        chartAdherencia.setDrawValueAboveBar(true);

        // Configurar eje X
        XAxis xAxis = chartAdherencia.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        // Configurar eje Y
        chartAdherencia.getAxisLeft().setDrawGridLines(true);
        chartAdherencia.getAxisRight().setEnabled(false);

        // Configurar leyenda
        chartAdherencia.getLegend().setEnabled(false);

        // Configurar animación
        chartAdherencia.animateY(1000);
    }

    private void configurarRecyclerView() {
        adapter = new HistorialAdapter(this, tratamientosConcluidos);
        rvTratamientosConcluidos.setLayoutManager(new LinearLayoutManager(this));
        rvTratamientosConcluidos.setAdapter(adapter);
    }

    private void cargarDatos() {
        // Cargar estadísticas generales
        DatosPrueba.EstadisticasAdherencia estadisticas = DatosPrueba.obtenerEstadisticas(this);
        tvEstadisticasGenerales.setText(String.format(
                "Adherencia General: %.1f%%\nMedicamentos Activos: %d\nMedicamentos Pausados: %d\nTotal Medicamentos: %d",
                estadisticas.getPorcentajeAdherencia(),
                estadisticas.medicamentosActivos,
                estadisticas.medicamentosPausados,
                estadisticas.totalMedicamentos
        ));

        // Cargar gráfico de adherencia por medicamento
        cargarGraficoAdherencia();

        // Cargar tratamientos concluidos
        tratamientosConcluidos = DatosPrueba.obtenerMedicamentosPausados(this);
        adapter.actualizarMedicamentos(tratamientosConcluidos);
    }

    private void cargarGraficoAdherencia() {
        List<Medicamento> medicamentos = DatosPrueba.obtenerMedicamentosPrueba(this);
        DatosPrueba.EstadisticasAdherencia estadisticas = DatosPrueba.obtenerEstadisticas(this);
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < medicamentos.size(); i++) {
            Medicamento medicamento = medicamentos.get(i);
            float adherencia = (float) estadisticas.getPorcentajeAdherencia();
            entries.add(new BarEntry(i, adherencia));
            labels.add(medicamento.getNombre());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Adherencia (%)");
        dataSet.setColor(getResources().getColor(R.color.primary));
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        chartAdherencia.setData(barData);
        chartAdherencia.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        chartAdherencia.invalidate();
    }

    private void configurarListeners() {
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos(); // Recargar datos al volver
    }
}