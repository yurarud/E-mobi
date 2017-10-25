package ua.zp.yurarud.e_mobi.model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Админ on 06.10.2017.
 */

public class Zakaz extends RealmObject{

    public RealmList<ZakazTable> producty;


    private int nomer;

    private boolean otpravlen;
    private String komment;
    private Clients klient;
    private String klient_name;
    private int tipCeny;
    private double summa;
    private boolean box;

    public String getKlient_name() {
        return klient_name;
    }

    public void setKlient_name(String klient_name) {
        this.klient_name = klient_name;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public Clients getKlient() {
        return klient;
    }

    public void setKlient(Clients klient) {
        this.klient = klient;
    }

    public int getTipCeny() {
        return tipCeny;
    }

    public void setTipCeny(int tipCeny) {
        this.tipCeny = tipCeny;
    }

    public int getNomer() {
        return nomer;
    }

    public void setNomer(int nomer) {
        this.nomer = nomer;
    }

    public boolean isOtpravlen() {
        return otpravlen;
    }

    public void setOtpravlen(boolean otpravlen) {
        this.otpravlen = otpravlen;
    }

    public String getKomment() {
        return komment;
    }

    public void setKomment(String komment) {
        this.komment = komment;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
