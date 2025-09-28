package com.controlmedicamentos.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.controlmedicamentos.myapplication.R;
import com.controlmedicamentos.myapplication.models.Medicamento;
import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {

    private Context context;
    private List<Medicamento> medicamentos;
    private OnMedicamentoClickListener listener;

    // Interface para manejar clicks
    public interface OnMedicamentoClickListener {
        void onTomadoClick(Medicamento medicamento);
        void onMedicamentoClick(Medicamento medicamento);
    }

    public MedicamentoAdapter(Context context, List<Medicamento> medicamentos) {
        this.context = context;
        this.medicamentos = medicamentos;
    }

    public void setOnMedicamentoClickListener(OnMedicamentoClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento medicamento = medicamentos.get(position);
        holder.bind(medicamento);
    }

    @Override
    public int getItemCount() {
        return medicamentos.size();
    }

    public void actualizarMedicamentos(List<Medicamento> nuevosMedicamentos) {
        this.medicamentos = nuevosMedicamentos;
        notifyDataSetChanged();
    }

    class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIconoMedicamento;
        private TextView tvNombreMedicamento;
        private TextView tvInfoMedicamento;
        private LinearLayout llBarrasTomas;
        private TextView tvStockInfo;
        private MaterialButton btnTomado;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIconoMedicamento = itemView.findViewById(R.id.ivIconoMedicamento);
            tvNombreMedicamento = itemView.findViewById(R.id.tvNombreMedicamento);
            tvInfoMedicamento = itemView.findViewById(R.id.tvInfoMedicamento);
            llBarrasTomas = itemView.findViewById(R.id.llBarrasTomas);
            tvStockInfo = itemView.findViewById(R.id.tvStockInfo);
            btnTomado = itemView.findViewById(R.id.btnTomado);
        }

        public void bind(Medicamento medicamento) {
            // Configurar información básica
            tvNombreMedicamento.setText(medicamento.getNombre());
            tvInfoMedicamento.setText(medicamento.getPresentacion() + " • " +
                    medicamento.getTomasDiarias() + " tomas diarias");
            tvStockInfo.setText("Stock: " + medicamento.getInfoStock());

            // Configurar ícono
            ivIconoMedicamento.setImageResource(medicamento.getIconoPresentacion());

            // Configurar barras de progreso
            configurarBarrasTomas(medicamento);

            // Configurar botón Tomado
            btnTomado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTomadoClick(medicamento);
                    }
                }
            });

            // Configurar click en el item completo
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onMedicamentoClick(medicamento);
                    }
                }
            });
        }

        private void configurarBarrasTomas(Medicamento medicamento) {
            // Limpiar barras existentes
            llBarrasTomas.removeAllViews();

            // Crear barras según tomas diarias
            int tomasDiarias = medicamento.getTomasDiarias();
            for (int i = 0; i < tomasDiarias; i++) {
                ProgressBar barra = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
                barra.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        context.getResources().getDimensionPixelSize(R.dimen.progress_bar_height),
                        1.0f
                ));
                barra.setMax(100);
                barra.setProgress(0);
                barra.setProgressTintList(context.getColorStateList(R.color.barra_pendiente));

                // Agregar margen entre barras
                if (i > 0) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) barra.getLayoutParams();
                    params.setMargins(context.getResources().getDimensionPixelSize(R.dimen.margin_small), 0, 0, 0);
                    barra.setLayoutParams(params);
                }

                llBarrasTomas.addView(barra);
            }
        }
    }
}
