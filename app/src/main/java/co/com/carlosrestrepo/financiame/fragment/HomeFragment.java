package co.com.carlosrestrepo.financiame.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.persistence.dao.MovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 *
 * @author  Carlos Restrepo
 * @created Octubre 15 de 2015
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mostrarSaldos(view);

        return view;
    }

    /**
     * Método encargado de consultar y mostrar los saldos consolidados
     * @param view
     */
    private void mostrarSaldos(View view) {
        MovimientoDAO mtoDAO = new MovimientoDAO(getContext());
        DecimalFormat df = new DecimalFormat("#,###");
        try {
            // SALDO DE INGRESOS - GASTOS
            Integer saldo = mtoDAO.getSaldo();
            if (saldo == null) saldo = Integer.valueOf("0");
            TextView txtSaldo = (TextView) view.findViewById(R.id.txtSaldo);
            txtSaldo.setText("$" + df.format(saldo));
            if (saldo < 0) {
                txtSaldo.setTextColor(Color.RED);
            } else {
                txtSaldo.setTextColor(Color.BLUE);
            }

            // PRÉSTAMOS
            Integer saldoPrestamos = mtoDAO.getSaldoPrestamos();
            if (saldoPrestamos == null) saldoPrestamos = Integer.valueOf("0");
            TextView txtSaldoPrestamos = (TextView) view.findViewById(R.id.txtPrestamos);
            txtSaldoPrestamos.setText("$" + df.format(saldoPrestamos));

            // TIPOS DE MOVIMIENTO CON CONSULTA DE SALDO
            Map<String, Integer> saldos = mtoDAO.getSaldosMarcados();
            if (!saldos.isEmpty()) {
                LinearLayout saldosLayout = (LinearLayout) view.findViewById(R.id.saldosLayout);
                for (Map.Entry<String, Integer> _saldo : saldos.entrySet()) {
                    if (_saldo.getKey() != null && !_saldo.getKey().isEmpty()) {
                        LinearLayout ll = new LinearLayout(getContext());
                        ll.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        ll.setOrientation(LinearLayout.VERTICAL);

                        AppCompatTextView name = new AppCompatTextView(getContext());
                        name.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                        name.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                        name.setTextSize(18);
                        name.setText(_saldo.getKey());
                        name.setTextColor(Color.BLACK);

                        AppCompatTextView value = new AppCompatTextView(getContext());
                        value.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                        value.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                        value.setTextSize(50);
                        value.setText("$" + df.format(_saldo.getValue()));
                        value.setTextColor(Color.BLACK);

                        ll.addView(name);
                        ll.addView(value);
                        saldosLayout.addView(ll);
                    }
                }
            }
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}