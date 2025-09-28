package com.controlmedicamentos.myapplication.utils;

import android.content.Context;
import com.controlmedicamentos.myapplication.models.Medicamento;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatosPrueba {
    private static List<Medicamento> medicamentosPrueba = new ArrayList<>();
    private static boolean inicializado = false;

    // Método principal para obtener medicamentos de prueba
    public static List<Medicamento> obtenerMedicamentosPrueba(Context context) {
        if (!inicializado) {
            inicializarDatosPrueba(context);
            inicializado = true;
        }
        return new ArrayList<>(medicamentosPrueba);
    }

    // Inicializar datos de prueba
    private static void inicializarDatosPrueba(Context context) {
        medicamentosPrueba.clear();

        // Agregar medicamentos de prueba
        medicamentosPrueba.add(crearParacetamol(context));
        medicamentosPrueba.add(crearIbuprofeno(context));
        medicamentosPrueba.add(crearJarabe(context));
        medicamentosPrueba.add(crearCrema(context));
        medicamentosPrueba.add(crearInyectable(context));
    }

    // Crear Paracetamol
    private static Medicamento crearParacetamol(Context context) {
        Medicamento medicamento = new Medicamento(
                "1", "Paracetamol 500mg", "Comprimidos", 3, "08:00",
                "Dolor de cabeza", 30, context.getResources().getColor(R.color.medicamento_azul, null), 7
        );
        medicamento.setDetalles("Tomar con agua");

        // Simular consumo parcial
        medicamento.consumirDosis();
        medicamento.consumirDosis();

        // Configurar fecha de vencimiento
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        medicamento.setFechaVencimiento(cal.getTime());

        return medicamento;
    }

    // Crear Ibuprofeno
    private static Medicamento crearIbuprofeno(Context context) {
        Medicamento medicamento = new Medicamento(
                "2", "Ibuprofeno 400mg", "Comprimidos", 2, "12:00",
                "Inflamación", 20, context.getResources().getColor(R.color.medicamento_rojo, null), 5
        );
        medicamento.setDetalles("Tomar con comida");

        // Simular consumo parcial
        medicamento.consumirDosis();

        // Configurar fecha de vencimiento
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 4);
        medicamento.setFechaVencimiento(cal.getTime());

        return medicamento;
    }

    // Crear Jarabe
    private static Medicamento crearJarabe(Context context) {
        Medicamento medicamento = new Medicamento(
                "3", "Jarabe para la tos", "Jarabe", 3, "09:00",
                "Tos", 1, context.getResources().getColor(R.color.medicamento_verde, null), 10
        );
        medicamento.setDetalles("Agitar antes de usar");

        // Configurar días estimados para jarabe
        medicamento.setDiasEstimadosDuracion(15);
        medicamento.setDiasRestantesDuracion(12);

        // Configurar fecha de vencimiento
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 8);
        medicamento.setFechaVencimiento(cal.getTime());

        return medicamento;
    }

    // Crear Crema
    private static Medicamento crearCrema(Context context) {
        Medicamento medicamento = new Medicamento(
                "4", "Crema antiinflamatoria", "Crema", 2, "20:00",
                "Dolor muscular", 1, context.getResources().getColor(R.color.medicamento_naranja, null), 14
        );
        medicamento.setDetalles("Aplicar en la zona afectada");

        // Configurar días estimados para crema
        medicamento.setDiasEstimadosDuracion(20);
        medicamento.setDiasRestantesDuracion(18);

        // Configurar fecha de vencimiento
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 12);
        medicamento.setFechaVencimiento(cal.getTime());

        return medicamento;
    }

    // Crear Inyectable
    private static Medicamento crearInyectable(Context context) {
        Medicamento medicamento = new Medicamento(
                "5", "Insulina", "Inyección", 2, "08:00",
                "Diabetes", 1, context.getResources().getColor(R.color.medicamento_morado, null), -1
        );
        medicamento.setDetalles("Medicamento crónico");

        // Configurar días estimados para inyectable
        medicamento.setDiasEstimadosDuracion(30);
        medicamento.setDiasRestantesDuracion(25);

        // Configurar fecha de vencimiento
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        medicamento.setFechaVencimiento(cal.getTime());

        return medicamento;
    }

    // Métodos CRUD
    public static void agregarMedicamento(Medicamento medicamento) {
        medicamentosPrueba.add(medicamento);
    }

    public static boolean eliminarMedicamento(String id) {
        for (int i = 0; i < medicamentosPrueba.size(); i++) {
            if (medicamentosPrueba.get(i).getId().equals(id)) {
                medicamentosPrueba.remove(i);
                return true;
            }
        }
        return false;
    }

    public static boolean actualizarMedicamento(Medicamento medicamento) {
        for (int i = 0; i < medicamentosPrueba.size(); i++) {
            if (medicamentosPrueba.get(i).getId().equals(medicamento.getId())) {
                medicamentosPrueba.set(i, medicamento);
                return true;
            }
        }
        return false;
    }

    public static Medicamento buscarPorId(String id) {
        for (Medicamento medicamento : medicamentosPrueba) {
            if (medicamento.getId().equals(id)) {
                return medicamento;
            }
        }
        return null;
    }

    // Métodos de filtrado
    public static List<Medicamento> obtenerMedicamentosActivos(Context context) {
        List<Medicamento> medicamentos = obtenerMedicamentosPrueba(context);
        List<Medicamento> activos = new ArrayList<>();

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.isActivo() && !medicamento.isPausado() && !medicamento.estaVencido()) {
                activos.add(medicamento);
            }
        }

        return activos;
    }

    public static List<Medicamento> obtenerMedicamentosVencidos(Context context) {
        List<Medicamento> medicamentos = obtenerMedicamentosPrueba(context);
        List<Medicamento> vencidos = new ArrayList<>();

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.estaVencido()) {
                vencidos.add(medicamento);
            }
        }

        return vencidos;
    }

    public static List<Medicamento> obtenerMedicamentosPausados(Context context) {
        List<Medicamento> medicamentos = obtenerMedicamentosPrueba(context);
        List<Medicamento> pausados = new ArrayList<>();

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.isPausado()) {
                pausados.add(medicamento);
            }
        }

        return pausados;
    }

    // Estadísticas
    public static class EstadisticasAdherencia {
        public int totalMedicamentos;
        public int medicamentosActivos;
        public int medicamentosVencidos;
        public int medicamentosPausados;
        public int tomasCompletadas;
        public int tomasPerdidas;

        public double getPorcentajeAdherencia() {
            int totalTomas = tomasCompletadas + tomasPerdidas;
            if (totalTomas == 0) return 0;
            return (double) tomasCompletadas / totalTomas * 100;
        }
    }

    public static EstadisticasAdherencia obtenerEstadisticas(Context context) {
        List<Medicamento> medicamentos = obtenerMedicamentosPrueba(context);
        EstadisticasAdherencia stats = new EstadisticasAdherencia();

        stats.totalMedicamentos = medicamentos.size();

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.isActivo() && !medicamento.isPausado()) {
                stats.medicamentosActivos++;
            }
            if (medicamento.estaVencido()) {
                stats.medicamentosVencidos++;
            }
            if (medicamento.isPausado()) {
                stats.medicamentosPausados++;
            }
        }

        // Simular estadísticas de tomas
        stats.tomasCompletadas = 45;
        stats.tomasPerdidas = 5;

        return stats;
    }

    // Método para resetear datos (útil para testing)
    public static void resetearDatos() {
        medicamentosPrueba.clear();
        inicializado = false;
    }
}
