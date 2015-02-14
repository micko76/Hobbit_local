package rs.gajdar_design.hobbit_local;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class Prva extends ActionBarActivity {

    private TextView konobar;
    public Konobar waiter;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prva);
        waiter = getIntent().getParcelableExtra("konobar");
        konobar = (TextView) findViewById(R.id.imeKonobara);
        konobar.setText(waiter.get_fullname());
        db.openDataBase();
        db.Clear();
        db.close();
    }

    public void noviKorisnik(View v) {
        Intent getback = new Intent(this, Login.class);
        startActivity(getback);
        finish();
    }

    public void izaberiSto(View v) {
        Intent izaberi = new Intent(this, NarudzbinaStart.class);
        izaberi.putExtra("konobar", waiter);
        startActivity(izaberi);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.kraj)
            finish();

        else if(id == R.id.settings){

            AlertDialog.Builder builder;
            final EditText txtInput = new EditText(Prva.this);

            builder = new AlertDialog.Builder(Prva.this);
            builder.setTitle("Adresa SQL Servera");
            builder.setView(txtInput);
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pref=getSharedPreferences("AdresaServer",MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("adresa",txtInput.getText().toString());
                    editor.commit();

                }
            });
            builder.setNegativeButton("Odustani",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;

    }
}
