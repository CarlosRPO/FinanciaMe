package co.com.carlosrestrepo.financiame.fragment.adapter.util;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.OpcionMenu;

/**
 *
 * @author  Carlos Restrepo
 * @created Octubre 05 de 2015
 */
public class AdapterConfiguration {

    public static final List<OpcionMenu> MENU_OPTIONS = new ArrayList<OpcionMenu>() {
        private static final long serialVersionUID = 1L;
        {
            add(new OpcionMenu(R.drawable.financiame, R.string.optionHome));
            add(new OpcionMenu(R.drawable.ic_menu, R.string.optionTipoMovimiento));
            add(new OpcionMenu(R.drawable.ic_menu, R.string.optionMedioPago));
            add(new OpcionMenu(R.drawable.ic_menu, R.string.optionDeudores));
            add(new OpcionMenu(R.drawable.ic_menu, R.string.optionMovimientos));
            add(new OpcionMenu(R.drawable.ic_menu, R.string.optionPrestamos));
        }
    };
}