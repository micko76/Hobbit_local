package rs.gajdar_design.hobbit_local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

/**
 * Created by Milan on 3/1/2015.
 */
public class CustomAdapter extends ArrayAdapter<Detalj>{

    public DBHelper db=new DBHelper(getContext());
    public Cursor c;
    public CustomAdapter(Context context, Detalj[] stavke)
    {
        super(context,R.layout.meni_celija_slika ,stavke);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.meni_celija_slika,parent,false);

        Detalj pojedinacnaStavka = getItem(position);
        TextView nazivStavke=(TextView) customView.findViewById(R.id.nazivStavke);
        ImageView slikaStavke= (ImageView) customView.findViewById(R.id.slikaStavke);

        nazivStavke.setText(pojedinacnaStavka.get_ime());
        db.openDataBase();
        c = db.getSlika(pojedinacnaStavka.get_idSlike());
        db.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(0));
        Bitmap icon = BitmapFactory.decodeStream(inputStream);
        slikaStavke.setImageBitmap(icon);
        return customView;
    }
}
