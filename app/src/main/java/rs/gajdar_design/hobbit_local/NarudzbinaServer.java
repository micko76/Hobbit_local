package rs.gajdar_design.hobbit_local;

/**
 * Created by Micko on 2/3/2015.
 */
public class NarudzbinaServer {
    private int _id,_status;
    private String _username;
    private String _sifra;
    private String _naziv;
    private String _kolicina;
    private String _cena;

    public String get_brojstola() {
        return _brojstola;
    }

    public void set_brojstola(String _brojstola) {
        this._brojstola = _brojstola;
    }

    private String _brojstola;

    public NarudzbinaServer()
    {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_sifra() {
        return _sifra;
    }

    public void set_sifra(String _sifra) {
        this._sifra = _sifra;
    }

    public String get_naziv() {
        return _naziv;
    }

    public void set_naziv(String _naziv) {
        this._naziv = _naziv;
    }

    public String get_kolicina() {
        return _kolicina;
    }

    public void set_kolicina(String _kolicina) {
        this._kolicina = _kolicina;
    }

    public String get_cena() {
        return _cena;
    }

    public void set_cena(String _cena) {
        this._cena = _cena;
    }
}
