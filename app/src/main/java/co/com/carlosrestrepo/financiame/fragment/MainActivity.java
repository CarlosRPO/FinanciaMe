package co.com.carlosrestrepo.financiame.fragment;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.customcontrol.ScrimInsetsFrameLayout;
import co.com.carlosrestrepo.financiame.fragment.adapter.MenuAdapter;
import co.com.carlosrestrepo.financiame.fragment.adapter.util.AdapterConfiguration;
import co.com.carlosrestrepo.financiame.model.OpcionMenu;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ScrimInsetsFrameLayout sifl;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView ndList;

    private final int OPTION_TIPO_MOVIMIENTO = 0;
    private final int OPTION_MEDIO_PAGO = 1;
    private final int OPTION_DEUDORES = 2;
    private final int OPTION_MOVIMIENTOS = 3;
    private final int OPTION_PRESTAMOS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencia al ScrimInsetsFrameLayout
        sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        final List<OpcionMenu> opcionesMenu = AdapterConfiguration.MENU_OPTIONS;
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), opcionesMenu);

        //Menu del Navigation Drawer (ListView)
        ndList = (ListView) findViewById(R.id.navdrawerlist);
        ndList.setAdapter(menuAdapter);
        ndList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Fragment fragment = null;

                switch (pos) {
                    case OPTION_TIPO_MOVIMIENTO:
                        fragment = new TipoMovimientoFragment();
                        break;
                    case OPTION_MEDIO_PAGO:
                        fragment = new MedioPagoFragment();
                        break;
                    case OPTION_DEUDORES:
                        fragment = new DeudorFragment();
                        break;
                    case OPTION_MOVIMIENTOS:
                        fragment = new MovimientoFragment();
                        break;
                    case OPTION_PRESTAMOS:
                        fragment = new PrestamoFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                ndList.setItemChecked(pos, true);

                getSupportActionBar().setTitle(opcionesMenu.get(pos).getTitulo());

                drawerLayout.closeDrawer(sifl);
            }
        });

        //Drawer Layout
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        drawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary_dark));

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        //Botón de apertura de DrawerList
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}