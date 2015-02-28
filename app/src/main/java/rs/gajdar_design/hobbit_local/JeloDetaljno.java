package rs.gajdar_design.hobbit_local;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;


public class JeloDetaljno extends Activity {

    DBHelper db = new DBHelper(this);
    Detalj izabrano = new Detalj();
    TextView detaljnoIme, detaljnoCena;
    EditText kolicina;
    String sto;
    Konobar waiter = new Konobar();
    ImageView slika;
    Narudzbina order = new Narudzbina();
    Button dodaj;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaljno_layout);
        detaljnoCena = (TextView) findViewById(R.id.detaljnoCena);
        detaljnoIme = (TextView) findViewById(R.id.detaljnoIme);
        slika = (ImageView) findViewById(R.id.slika);
        kolicina = (EditText) findViewById(R.id.brojKomada);
        String stavka = getIntent().getStringExtra("stavka");
        sto = getIntent().getStringExtra("brojStola");
        waiter = getIntent().getParcelableExtra("konobar");
        dodaj = (Button) findViewById(R.id.buttonDodaj);


        //Citanje detalja o jelu iz baze
        db.openDataBase();
        Cursor c = db.getDetalje(stavka);
        db.close();
        izabrano.set_ime(c.getString(0));
        izabrano.set_cena(c.getString(1));
        izabrano.set_idSlike(c.getString(2));
        izabrano.set_sifra(c.getString(3));

        //Citanje slike iz baze
        db.openDataBase();
        c = db.getSlika(izabrano.get_idSlike());
        db.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(0));
        Bitmap icon = BitmapFactory.decodeStream(inputStream);

        //Postavljanje elemenata na ekranu
        detaljnoIme.setText(izabrano.get_ime());
        detaljnoCena.setText(izabrano.get_cena() + " din.");
        slika.setImageBitmap(icon);

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kolicina.getText().toString().trim().length()>0){
                   dodajNarudzbinu(v);
                }
                else
                    Toast.makeText(getApplicationContext(),"Morate uneti kolicinu",Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void dodajNarudzbinu(View v) {

            order.setIdStola(sto);
            order.setIdKorisnika(Integer.toString(waiter.get_id()));
            order.setKolicina(kolicina.getText().toString());
            order.setNazivStavke(izabrano.get_ime());
            order.setCena(izabrano.get_cena());
            order.setSifraStavke(izabrano.get_sifra());
            db.openDataBase();
            db.ubaciStavku(order);
            db.close();
            Intent i = new Intent(getApplicationContext(), NarudzbinaStart.class);
            i.putExtra("konobar", waiter);
            i.putExtra("brojStola", sto);
            startActivity(i);
            finish();





    }
}
