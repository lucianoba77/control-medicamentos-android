package com.controlmedicamentos.myapplication.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Medicamento {
    private String id;
    private String nombre;
    private String presentacion; // comprimidos, jarabe, crema, etc.
    private int tomasDiarias;
    private String horarioPrimeraToma; // formato "HH:mm"
    private String afeccion;
    private int stockInicial;
    private int stockActual;
    private int color; // color del medicamento
    private int diasTratamiento; // -1 para crónico
    private boolean activo;
    private String detalles;
    private List<String> horariosTomas; // lista de horarios de todas las tomas
    private int iconoPresentacion; // ícono según la presentación

    // Constructor por defecto
    public Medicamento() {
        this.horariosTomas = new ArrayList<>();
        this.activo = true;
        this.iconoPresentacion = android.R.drawable.ic_menu_info_details;
    }

    // Constructor con parámetros
    public Medicamento(String id, String nombre, String presentacion,
                       int tomasDiarias, String horarioPrimeraToma,
                       String afeccion, int stockInicial, int color, int diasTratamiento) {
        this.id = id;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.tomasDiarias = tomasDiarias;
        this.horarioPrimeraToma = horarioPrimeraToma;
        this.afeccion = afeccion;
        this.stockInicial = stockInicial;
        this.stockActual = stockInicial;
        this.color = color;
        this.diasTratamiento = diasTratamiento;
        this.activo = true;
        this.horariosTomas = new ArrayList<>();
        this.iconoPresentacion = android.R.drawable.ic_menu_info_details;

        // Configurar ícono según presentación
        asignarIconoPresentacion();

        // Generar horarios de tomas
        generarHorariosTomas();
    }

    // Configurar ícono según la presentación
    private void asignarIconoPresentacion() {
        switch (presentacion.toLowerCase()) {
            case "comprimidos":
            case "pastillas":
                this.iconoPresentacion = android.R.drawable.ic_menu_edit;
                break;
            case "jarabe":
                this.iconoPresentacion = android.R.drawable.ic_menu_help;
                break;
            case "crema":
                this.iconoPresentacion = android.R.drawable.ic_menu_preferences;
                break;
            case "spray nasal":
                this.iconoPresentacion = android.R.drawable.ic_menu_help;
                break;
            case "inyección":
                this.iconoPresentacion = android.R.drawable.ic_menu_send;
                break;
            default:
                this.iconoPresentacion = android.R.drawable.ic_menu_info_details;
        }
    }

    // Generar horarios de tomas basado en tomas diarias
    private void generarHorariosTomas() {
        horariosTomas.clear();

        if (tomasDiarias <= 0) return;

        // Calcular intervalo entre tomas (24 horas / tomas diarias)
        int intervaloHoras = 24 / tomasDiarias;

        // Parsear hora inicial
        String[] partesHora = horarioPrimeraToma.split(":");
        int horaInicial = Integer.parseInt(partesHora[0]);
        int minutoInicial = Integer.parseInt(partesHora[1]);

        // Generar horarios
        for (int i = 0; i < tomasDiarias; i++) {
            int hora = (horaInicial + (i * intervaloHoras)) % 24;
            String horario = String.format("%02d:%02d", hora, minutoInicial);
            horariosTomas.add(horario);
        }
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
        asignarIconoPresentacion();
    }

    public int getTomasDiarias() {
        return tomasDiarias;
    }

    public void setTomasDiarias(int tomasDiarias) {
        this.tomasDiarias = tomasDiarias;
        generarHorariosTomas();
    }

    public String getHorarioPrimeraToma() {
        return horarioPrimeraToma;
    }

    public void setHorarioPrimeraToma(String horarioPrimeraToma) {
        this.horarioPrimeraToma = horarioPrimeraToma;
        generarHorariosTomas();
    }

    public String getAfeccion() {
        return afeccion;
    }

    public void setAfeccion(String afeccion) {
        this.afeccion = afeccion;
    }

    public int getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(int stockInicial) {
        this.stockInicial = stockInicial;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDiasTratamiento() {
        return diasTratamiento;
    }

    public void setDiasTratamiento(int diasTratamiento) {
        this.diasTratamiento = diasTratamiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public List<String> getHorariosTomas() {
        return horariosTomas;
    }

    public void setHorariosTomas(List<String> horariosTomas) {
        this.horariosTomas = horariosTomas;
    }

    public int getIconoPresentacion() {
        return iconoPresentacion;
    }

    public void setIconoPresentacion(int iconoPresentacion) {
        this.iconoPresentacion = iconoPresentacion;
    }

    // Métodos útiles
    public boolean esCronico() {
        return diasTratamiento == -1;
    }

    public int getDiasRestantesTratamiento() {
        if (esCronico()) {
            return -1; // Crónico, no tiene fin
        }
        // Aquí podrías implementar lógica para calcular días restantes
        // Por ahora retornamos un valor por defecto
        return diasTratamiento;
    }

    public int getPorcentajeStock() {
        if (stockInicial <= 0) return 0;
        return (stockActual * 100) / stockInicial;
    }

    public boolean necesitaReposicion() {
        return getPorcentajeStock() <= 20; // Menos del 20% de stock
    }

    public void consumirDosis() {
        if (stockActual > 0) {
            stockActual--;
        }
    }

    public void agregarStock(int cantidad) {
        stockActual += cantidad;
    }

    public boolean estaAgotado() {
        return stockActual <= 0;
    }
}
