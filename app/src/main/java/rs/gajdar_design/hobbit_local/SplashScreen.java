package rs.gajdar_design.hobbit_local;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class SplashScreen extends Activity {

    private ImageView pozadina;
    public DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pozadina = (ImageView) findViewById(R.id.splashimage);
        pozadina.setImageResource(R.drawable.ic_launcher);
        try {
            db.createDB();
            Toast.makeText(this, "Baza uspesno kreirana", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(this, "Greska kod kreiranja baze", Toast.LENGTH_SHORT);
        }
        Intent login = new Intent(this, Login.class);
        startActivity(login);
        finish();
    }


}
