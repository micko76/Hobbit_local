package rs.gajdar_design.hobbit_local;

/**
 * Created by Milan on 2/1/2015.
 */
public class Produkt {
    private int _id;
    private String _sifra, _ime, _cena, _idslike, _tip;

    public Produkt() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_sifra() {
        return _sifra;
    }

    public void set_sifra(String _sifra) {
        this._sifra = _sifra;
    }

    public String get_ime() {
        return _ime;
    }

    public void set_ime(String _ime) {
        this._ime = _ime;
    }

    public String get_cena() {
        return _cena;
    }

    public void set_cena(String _cena) {
        this._cena = _cena;
    }

    public String get_tip() {
        return _tip;
    }

    public void set_tip(String _tip) {
        this._tip = _tip;
    }

    public String get_idslike() {
        return _idslike;
    }

    public void set_idslike(String _idslike) {
        this._idslike = _idslike;
    }
}
