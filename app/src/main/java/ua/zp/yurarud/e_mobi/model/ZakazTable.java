package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;

/**
 * Created by Админ on 10.10.2017.
 */

public class ZakazTable extends RealmObject {

    private Products tovar;
    private String tovar_name;
    private int ostatok;
    private double cena;

    public Products getTovar() {
        return tovar;
    }

    public void setTovar(Products tovar) {
        this.tovar = tovar;
    }

    public String getTovar_name() {
        return tovar_name;
    }

    public void setTovar_name(String tovar_name) {
        this.tovar_name = tovar_name;
    }

    public int getOstatok() {
        return ostatok;
    }

    public void setOstatok(int ostatok) {
        this.ostatok = ostatok;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}
