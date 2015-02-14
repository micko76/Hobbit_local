package rs.gajdar_design.hobbit_local;

/**
 * Created by Micko on 1/31/2015.
 */
public class Narudzbina {
    private int _id;
    private String _idKorisnika;
    private String _idStola;
    private String _sifraStavke;
    private String _nazivStavke;
    private String _cena;
    private String _kolicina;

    public Narudzbina() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getIdKorisnika() {
        return _idKorisnika;
    }

    public void setIdKorisnika(String idKorisnika) {
        this._idKorisnika = idKorisnika;
    }

    public String getIdStola() {
        return _idStola;
    }

    public void setIdStola(String idStola) {
        this._idStola = idStola;
    }

    public String getSifraStavke() {
        return _sifraStavke;
    }

    public void setSifraStavke(String sifraStavke) {
        this._sifraStavke = sifraStavke;
    }

    public String getNazivStavke() {
        return _nazivStavke;
    }

    public void setNazivStavke(String nazivStavke) {
        this._nazivStavke = nazivStavke;
    }

    public String getCena() {
        return _cena;
    }

    public void setCena(String cena) {
        this._cena = cena;
    }

    public String getKolicina() {
        return _kolicina;
    }

    public void setKolicina(String kolicina) {
        this._kolicina = kolicina;
    }
}
