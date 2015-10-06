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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.ScrimInsetsFrameLayout;

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

    private final int OPTION_MOVIMIENTOS = 0;
    private final int OPTION_PRESTAMOS = 1;
    private final int OPTION_DEUDORES = 2;
    private final int OPTION_TIPO_MOVIMIENTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencia al ScrimInsetsFrameLayout
        sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //Menu del Navigation Drawer (ListView)
        ndList = (ListView) findViewById(R.id.navdrawerlist);

        final String[] opciones = getResources().getStringArray(R.array.menu_options);

        ArrayAdapter<String> ndMenuAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, opciones);

        ndList.setAdapter(ndMenuAdapter);

        ndList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Fragment fragment = null;

                switch (pos) {
                    case OPTION_MOVIMIENTOS:
                        fragment = new MovimientoFragment();
                        break;
                    case OPTION_PRESTAMOS:
                        fragment = new PrestamoFragment();
                        break;
                    case OPTION_DEUDORES:
                        fragment = new DeudorFragment();
                        break;
                    case OPTION_TIPO_MOVIMIENTO:
                        fragment = new TipoMovimientoFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                ndList.setItemChecked(pos, true);

                getSupportActionBar().setTitle(opciones[pos]);

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