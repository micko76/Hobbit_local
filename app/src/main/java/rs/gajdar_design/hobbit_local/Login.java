package rs.gajdar_design.hobbit_local;

        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;


public class Login extends Activity {
    private EditText userName, password;
    private DBHelper db = new DBHelper(this);
    public Konobar user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logovanje);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
    }

    public void prijavljivanje(View v) {

        boolean test;
        test = Proveri(userName.getText().toString(), password.getText().toString());
        if (test) {
            db.openDataBase();
            Cursor c = db.getKonobar(userName.getText().toString(), password.getText().toString());
            int id = Integer.parseInt(c.getString(0));
            String name = c.getString(1);
            String pass = c.getString(2);
            String full = c.getString(3);
            user = new Konobar(id, name, pass, full);
            Intent intent = new Intent(this, Prva.class);
            intent.putExtra("konobar", user);
            startActivity(intent);
            db.close();
            finish();

        } else {
            Toast.makeText(this, "Nepostojeci korisnik/Pogresna lozinka", Toast.LENGTH_SHORT).show();
            userName.setText("");
            password.setText("");
        }

    }

    public boolean Proveri(String user, String pass) {
        boolean test;
        db.openDataBase();
        Cursor c = db.getKonobar(user, pass);
        if (c.getCount() != 0)
            test = true;
        else
            test = false;
        db.close();
        return test;
    }


}
