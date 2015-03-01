package rs.gajdar_design.hobbit_local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Created by Micko on 1/26/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/rs.gajdar_design.hobbit_local/databases/";
    private static String DB_NAME = "hobbitlocal.db";
    private SQLiteDatabase baza;
    Context ctx;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.ctx = context;
    }

    public void createDB() throws IOException {

        boolean dbExists = checkDataBase();
        if (dbExists) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Greska kod kopiranja");
            }

        }
    }

    private boolean checkDataBase() {

        SQLiteDatabase check = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                check = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
        }

        if (check != null) {
            check.close();
        }
        return check != null ? true : false;

    }

    private void copyDataBase() throws IOException {
        InputStream myInput = ctx.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String putanja = DB_PATH + DB_NAME;
        baza = SQLiteDatabase.openDatabase(putanja, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public Cursor getKonobar(String user, String pass) throws SQLException {
        Cursor c =
                baza.query("Konobari", new String[]{"_id", "username", "password", "fullName"},
                        "username ='" + user + "'" + " and password='" + pass + "';", null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;
    }

    public Cursor getAlkohol() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='alkoholno pice';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getAlkoholSlike() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime","Id_slike"}, "tip ='alkoholno pice';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getSokovi() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='sokovi';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getNapitak() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='topli napici';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getJelo() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='jela';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getSalata() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='salate';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }
    public Cursor getPrilog() {

        Cursor c = baza.query("hobbitmeni", new String[]{"Ime"}, "tip ='prilog';", null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        } else
            c.close();

        return c;


    }

    public Cursor getDetalje(String ime) throws SQLException {
        Cursor c = baza.query(true, "hobbitmeni", new String[]{"Ime", "Cena", "Id_slike", "Sifra"}, "Ime ='" + ime + "';", null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getSlika(String id) throws SQLException {
        Cursor c = baza.query(true, "Slike", new String[]{"Slika"}, "Slika_Id='" + id + "';", null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }

    public Cursor getNarudzbina() throws SQLException {
        Cursor c = baza.query("Narudzbina", new String[]{"_id", "idKorisnika", "sifraStavke", "cena", "kolicina",
                "idStola", "nazivStavke"}, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public void Clear() throws SQLException {
        baza.delete("Narudzbina", null, null);
    }

    public void DeleteItem(Narudzbina or) throws SQLException {
        baza.delete("Narudzbina", "_id='" + or.get_id() + "';", null);
    }

    public void updateStavku(Narudzbina or) {
        ContentValues edited = new ContentValues();
        edited.put("kolicina", or.getKolicina());
        baza.update("Narudzbina", edited, "_id='" + or.get_id() + "';", null);
    }

    public long ubaciStavku(Narudzbina order) throws SQLException {
        ContentValues initial = new ContentValues();
        initial.put("idStola", order.getIdStola());
        initial.put("idKorisnika", order.getIdKorisnika());
        initial.put("sifraStavke", order.getSifraStavke());
        initial.put("cena", order.getCena());
        initial.put("kolicina", order.getKolicina());
        initial.put("nazivStavke", order.getNazivStavke());
        return baza.insert("Narudzbina", null, initial);
    }

    @Override
    public synchronized void close() {

        if (baza != null)
            baza.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
