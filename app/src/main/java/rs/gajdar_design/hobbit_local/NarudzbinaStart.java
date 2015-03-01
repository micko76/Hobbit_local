package rs.gajdar_design.hobbit_local;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class NarudzbinaStart extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public Konobar waiter;
    private TextView imeKonobara,zbir,cena;
    private int total=0;
    private Spinner spinner;
    private String brojstola;
    private String[] stolovi = new String[43];
    private TabHost tabHost;
    private List<Narudzbina> Orders = new ArrayList<Narudzbina>();
    private ListView lista;
    private InputStream is;

    Button alkoholButton, sokoviButton, napiciButton, jelaButton, salateButton, priloziButton, zatvori, edit, delete;
    String selektovano;
    DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzbina_start);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Dobijanje i postavljanje podataka o konobaru
        waiter = getIntent().getParcelableExtra("konobar");
        brojstola = getIntent().getStringExtra("brojStola");
        imeKonobara = (TextView) findViewById(R.id.imeKonobaraNarudzbina);
        imeKonobara.setText(waiter.get_fullname());
        lista = (ListView) findViewById(R.id.listPregled);
        napuniListu();
        NarudzbinaListAdapter adapterNarudzbina = new NarudzbinaListAdapter();
        lista.setAdapter(adapterNarudzbina);


        //Postavljanje i inicijalizacija tabova
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("narudzbina");
        tabSpec.setContent(R.id.tabnarudzbina);
        tabSpec.setIndicator("Narudzbina");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("pregled");
        tabSpec.setContent(R.id.tabpregled);
        tabSpec.setIndicator("Pregled");
        tabHost.addTab(tabSpec);

        //Inicijalizacija spinner-a
        napuniString();
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spin, stolovi);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        if (brojstola != null)
            spinner.setSelection(Integer.parseInt(brojstola));

        spinner.setOnItemSelectedListener(this);

        //Inicijalizacija i postavljanje button-a
        alkoholButton = (Button) findViewById(R.id.alkoholButton);
        sokoviButton = (Button) findViewById(R.id.sokoviButton);
        napiciButton = (Button) findViewById(R.id.napiciButton);
        jelaButton = (Button) findViewById(R.id.jelaButton);
        salateButton = (Button) findViewById(R.id.salateButton);
        priloziButton = (Button) findViewById(R.id.priloziButton);
        zatvori = (Button) findViewById(R.id.zatvaranje);

        //Inicijalizacija totala
        zbir = (TextView) findViewById(R.id.textViewRacunica);
        izracunajTotal(Orders);
        zbir.setText(Integer.toString(total)+" din.");

    }

    public void napuniString() {
        for (int i = 1; i < 43; i++)
            stolovi[i] = String.valueOf(i);

        stolovi[0] = "";

    }

    public void napuniListu() {
        db.openDataBase();
        Cursor c = db.getNarudzbina();
        db.close();
        if (c.getCount() != 0) {
            do {
                Narudzbina narudzbina = new Narudzbina();
                narudzbina.set_id(Integer.parseInt(c.getString(0)));
                narudzbina.setIdKorisnika(c.getString(1));
                narudzbina.setSifraStavke(c.getString(2));
                narudzbina.setCena(c.getString(3));
                narudzbina.setKolicina(c.getString(4));
                narudzbina.setIdStola(c.getString(5));
                narudzbina.setNazivStavke(c.getString(6));
                Orders.add(narudzbina);
                } while (c.moveToNext());


        }
    }
    public void izracunajTotal(List<Narudzbina> ord){
        total=0;
        int brojac=lista.getCount();
        int i =0;
        if(!ord.isEmpty()) {
            do {
                Narudzbina item = ord.get(i);
                total += Integer.parseInt(item.getKolicina()) * Integer.parseInt(item.getCena());
                i++;
            } while (i < brojac);
        }

    }

    public void zatvoriNarudzbinu(View view) {
        NarudzbinaServer zaispucavanje = new NarudzbinaServer();
        zaispucavanje.set_username(waiter.get_username());
        zaispucavanje.set_status(1);
        zaispucavanje.set_brojstola(brojstola);
        int brojac=lista.getCount();
        int i=0;
        do{
            Narudzbina item=Orders.get(i);
            zaispucavanje.set_sifra(item.getSifraStavke());
            zaispucavanje.set_naziv(item.getNazivStavke());
            zaispucavanje.set_kolicina(item.getKolicina());
            zaispucavanje.set_cena(item.getCena());
            Upisi(zaispucavanje);
            i++;
        }while(i<brojac);
        db.openDataBase();
        db.Clear();
        db.close();
        total=0;
        zbir.setText(Integer.toString(total)+" din.");
        lista.setAdapter(null);
    }

    public void Upisi(NarudzbinaServer item)
    {
        ArrayList<NameValuePair> parovi=new ArrayList<NameValuePair>();
        parovi.add(new BasicNameValuePair("brojStola",item.get_brojstola()));
        parovi.add(new BasicNameValuePair("username",item.get_username()));
        parovi.add(new BasicNameValuePair("sifra",item.get_sifra()));
        parovi.add(new BasicNameValuePair("naziv",item.get_naziv()));
        parovi.add(new BasicNameValuePair("kolicina",item.get_kolicina()));
        parovi.add(new BasicNameValuePair("cena",item.get_cena()));
        parovi.add(new BasicNameValuePair("status",Integer.toString(item.get_status())));

        try{
            SharedPreferences pref=getSharedPreferences("AdresaServer",MODE_PRIVATE);
            String adresa=pref.getString("adresa","");
            HttpClient httpClient=new DefaultHttpClient();

            HttpPost httpPost=new HttpPost("http://"+adresa+"/Insert.php");
            httpPost.setEntity(new UrlEncodedFormEntity(parovi));
            HttpResponse odgovor=httpClient.execute(httpPost);
            HttpEntity entity=odgovor.getEntity();
            is = entity.getContent();
            Toast.makeText(getApplicationContext(),"Uspesno upisano u bazu",Toast.LENGTH_SHORT).show();

            }
        catch (ClientProtocolException e)
        {
            Log.e("ClientProtocol","Log_tag");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e("Log_tag","IOException");
            e.printStackTrace();
        }

    }

    public void openAListu(View v) {
        Intent i = new Intent(this, AlkoholListaSlika.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }
    public void openSokListu(View v)
    {
        Intent i = new Intent(this, SokoviLista.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }
    public void openNapListu(View v)
    {
        Intent i = new Intent(this, NapiciLista.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }
    public void openJListu(View v)
    {
        Intent i = new Intent(this, JelaLista.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }
    public void openSalListu(View v)
    {
        Intent i = new Intent(this, SalateLista.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }
    public void openPrListu(View v)
    {
        Intent i = new Intent(this, PriloziLista.class);
        i.putExtra("konobar", waiter);
        i.putExtra("brojStola", selektovano);
        startActivity(i);
        finish();
    }

    private class NarudzbinaListAdapter extends ArrayAdapter<Narudzbina> {
        public NarudzbinaListAdapter() {
            super(NarudzbinaStart.this, R.layout.listview_narudzbina2, Orders);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_narudzbina2, parent, false);

            final Narudzbina tekuci = Orders.get(position);

            TextView naziv = (TextView) view.findViewById(R.id.nazivStavkeNarudzbina);
            naziv.setText(tekuci.getNazivStavke());

             cena = (TextView) view.findViewById(R.id.cenaStavkeNarudzbina);
            cena.setText(Integer.parseInt(tekuci.getKolicina()) * Integer.parseInt(tekuci.getCena()) + " din.");


            TextView kolicina = (TextView) view.findViewById(R.id.kolicinaNarudzbina);
            kolicina.setText(tekuci.getKolicina() + " kom.");

            TextView sto = (TextView) view.findViewById(R.id.brojStolaNarudzbina);
            sto.setText("Broj stola: " + tekuci.getIdStola());

            edit = (Button) view.findViewById(R.id.editButton);
            delete = (Button) view.findViewById(R.id.deleteButton);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //kod za editovanje listview-a
                    final Narudzbina item = Orders.get(position);
                    final AlertDialog.Builder builder;
                    final EditText txtInput = new EditText(NarudzbinaStart.this);
                    txtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder = new AlertDialog.Builder(NarudzbinaStart.this);
                    builder.setTitle("Unesi Izmenu");
                    builder.setView(txtInput);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            item.setKolicina(txtInput.getText().toString());
                            NarudzbinaListAdapter adapter1 = new NarudzbinaListAdapter();
                            lista.setAdapter(adapter1);
                            izracunajTotal(Orders);
                            zbir.setText(Integer.toString(total)+" din.");
                            db.openDataBase();
                            db.updateStavku(item);
                            db.close();

                        }
                    });
                    builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Narudzbina item = Orders.get(position);
                    Orders.remove(item);
                    NarudzbinaListAdapter adapter1 = new NarudzbinaListAdapter();
                    lista.setAdapter(adapter1);
                    izracunajTotal(Orders);
                    zbir.setText(Integer.toString(total)+" din.");
                    db.openDataBase();
                    db.DeleteItem(item);
                    db.close();


                }
            });

            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_narudzbina_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nazad) {
            Intent i = new Intent(this, Prva.class);
            i.putExtra("konobar", waiter);
            startActivity(i);
            finish();
        }
        return true;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.setSelection(position);
        selektovano = (String) spinner.getSelectedItem();

        if (selektovano != "") {
            alkoholButton.setEnabled(true);
            sokoviButton.setEnabled(true);
            napiciButton.setEnabled(true);
            jelaButton.setEnabled(true);
            salateButton.setEnabled(true);
            priloziButton.setEnabled(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
