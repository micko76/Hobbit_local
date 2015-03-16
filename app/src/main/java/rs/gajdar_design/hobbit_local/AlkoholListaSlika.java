package rs.gajdar_design.hobbit_local;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class AlkoholListaSlika extends ActionBarActivity {

    private ListView alkoholLista;
    private Cursor alkohol;
    private Konobar waiter;
    private String sto;
    DBHelper db = new DBHelper(this);
    listaDetalj[] spisak;
    int brojac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alkohol_lista_slika);
        alkoholLista = (ListView) findViewById(R.id.alkoholListaSlike);
        waiter = getIntent().getParcelableExtra("konobar");
        sto = getIntent().getStringExtra("brojStola");

        //citasnje pica iz baze i kopiranje u string array
       /* db.openDataBase();
        alkohol = db.getAlkoholSlike();
        int brojac = alkohol.getCount();
        db.close();
        final listaDetalj[] spisak = new listaDetalj[brojac];
        for (int i = 0; i < brojac; i++) {
            spisak[i]=new listaDetalj();

            spisak[i].set_ime(alkohol.getString(0)) ;
            spisak[i].set_idSlike(alkohol.getString(1));

            alkohol.moveToNext();
        }*/

        //punjenje liste
        new NapuniListu().execute(new DBHelper(this));

        //Vezivanje Listenera
        alkoholLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listaDetalj stavkaKliknuta = spisak[position];
                    Intent showDetails = new Intent(getApplicationContext(), JeloDetaljno.class);
                    showDetails.putExtra("stavka", stavkaKliknuta.get_ime());
                    showDetails.putExtra("brojStola", sto);
                    showDetails.putExtra("konobar", waiter);
                    startActivity(showDetails);
                    finish();
                } catch (Exception e) {
                }

            }
        });


    }
    public void PopuniListu(Cursor alkohol)
    {
       brojac = alkohol.getCount();
       spisak=new listaDetalj[brojac];
        for (int i = 0; i < brojac; i++) {
            spisak[i] = new listaDetalj();

            spisak[i].set_ime(alkohol.getString(0));
            spisak[i].set_idSlike(alkohol.getString(1));

            alkohol.moveToNext();
        }
        CustomAdapter adapter = new CustomAdapter(this,spisak);
        alkoholLista.setAdapter(adapter);

    }
    public class NapuniListu extends AsyncTask<DBHelper,Void,Cursor>
    {
        @Override
        protected void onPostExecute(Cursor alkohol) {
            PopuniListu(alkohol);
        }

        @Override
        protected Cursor doInBackground(DBHelper... params) {
            params[0].openDataBase();
            Cursor c = params[0].getAlkoholSlike();
            params[0].close();
            return c;
        }

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
