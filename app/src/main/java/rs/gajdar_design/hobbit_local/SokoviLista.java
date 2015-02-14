package rs.gajdar_design.hobbit_local;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SokoviLista extends ActionBarActivity {

    private ListView sokovilista;
    private Cursor sokovi;
    private Konobar waiter;
    private String sto;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sokovi_lista);
        sokovilista = (ListView) findViewById(R.id.sokovilista);
        waiter = getIntent().getParcelableExtra("konobar");
        sto = getIntent().getStringExtra("brojStola");

        //citasnje pica iz baze i kopiranje u string array
        db.openDataBase();
        sokovi = db.getSokovi();
        int brojac = sokovi.getCount();
        db.close();
        final String[] spisak = new String[brojac];
        for (int i = 0; i < brojac; i++) {
            spisak[i] = sokovi.getString(0);
            sokovi.moveToNext();
        }

        //punjenje liste
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.meni_celija, spisak);
        sokovilista.setAdapter(adapter);

        //Vezivanje Listenera
        sokovilista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String stavkaKliknuta = spisak[position];
                    Intent showDetails = new Intent(getApplicationContext(), JeloDetaljno.class);
                    showDetails.putExtra("stavka", stavkaKliknuta);
                    showDetails.putExtra("brojStola", sto);
                    showDetails.putExtra("konobar", waiter);
                    startActivity(showDetails);
                    finish();
                } catch (Exception e) {
                }

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_open_alistu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nazad) {
            Intent i = new Intent(this, NarudzbinaStart.class);
            i.putExtra("konobar", waiter);
            i.putExtra("brojStola", sto);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}