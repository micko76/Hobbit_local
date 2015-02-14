package rs.gajdar_design.hobbit_local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Micko on 1/27/2015.
 */
public class Konobar implements Parcelable {
    private int _id;
    private String _username;
    private String _password;
    private String _fullname;

    public Konobar() {
    }

    public Konobar(int id, String user, String pass, String full) {
        this._id = id;
        this._username = user;
        this._password = pass;
        this._fullname = full;

    }

    public Konobar(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this._id = Integer.parseInt(data[0]);
        this._username = data[1];
        this._password = data[2];
        this._fullname = data[3];
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_fullname() {
        return _fullname;
    }

    public void set_fullname(String _fullname) {
        this._fullname = _fullname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this._id), this._username, this._password, this._fullname
        });
    }

    public static final Creator<Konobar> CREATOR = new Creator<Konobar>() {
        @Override
        public Konobar createFromParcel(Parcel source) {
            return new Konobar(source);
        }

        @Override
        public Konobar[] newArray(int size) {
            return new Konobar[0];
        }
    };
}
