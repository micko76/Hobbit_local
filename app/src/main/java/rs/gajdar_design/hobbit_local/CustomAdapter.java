package rs.gajdar_design.hobbit_local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

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

        Bitmap icon = DajSliku(pojedinacnaStavka.get_idSlike());


        nazivStavke.setText(pojedinacnaStavka.get_ime());
        slikaStavke.setImageBitmap(icon);
        return customView;
    }

    Bitmap DajSliku(String id)
    {
        Bitmap icon;
        db.openDataBase();
        c = db.getSlika(pojedinacnaStavka.get_idSlike());
        db.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(0));
        return icon = BitmapFactory.decodeStream(inputStream);

    }
}
