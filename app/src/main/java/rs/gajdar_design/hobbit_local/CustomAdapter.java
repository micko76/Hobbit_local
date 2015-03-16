package rs.gajdar_design.hobbit_local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Milan on 3/1/2015.
 */
public class CustomAdapter extends ArrayAdapter<listaDetalj>{

    public DBHelper db=new DBHelper(getContext());
    public Cursor c;
    public TextView nazivStavke;
    public ImageView slikaStavke;
    public listaDetalj pojedinacnaStavka;

    public CustomAdapter(Context context, listaDetalj[] stavke)
    {
        super(context,R.layout.meni_celija_slika ,stavke);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.meni_celija_slika,parent,false);

        pojedinacnaStavka = getItem(position);
        nazivStavke=(TextView) customView.findViewById(R.id.nazivStavke);
        slikaStavke= (ImageView) customView.findViewById(R.id.slikaStavke);
        nazivStavke.setText(pojedinacnaStavka.get_ime());
        db.openDataBase();
        c=db.getSlika(pojedinacnaStavka.get_idSlike());
        DajSliku(c);
        db.close();
        return customView;
    }

    void DajSliku(Cursor c)
    {
        Bitmap icon;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(0));
        icon = BitmapFactory.decodeStream(inputStream);
        slikaStavke.setImageBitmap(icon);
    }
}
