package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;
import io.realm.RealmList;

/**
 * Created by Админ on 14.08.2017.
 */

public class Products extends RealmObject {
    @Required
    private String kod;

    @Required
    private String name;

    private String kod_rod;
    private String vpachke;
    private int ostatok;
    private double CenaGrn;
    private double CenaNDS;
    private double CenaFOP;

    public double getCenaGrn() {
        return CenaGrn;
    }

    public void setCenaGrn(double cenaGrn) {
        CenaGrn = cenaGrn;
    }

    public double getCenaNDS() {
        return CenaNDS;
    }

    public void setCenaNDS(double cenaNDS) {
        CenaNDS = cenaNDS;
    }

    public double getCenaFOP() {
        return CenaFOP;
    }

    public void setCenaFOP(double cenaFOP) {
        CenaFOP = cenaFOP;
    }


    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKod_rod() {
        return kod_rod;
    }

    public void setKod_rod(String kod_rod) {
        this.kod_rod = kod_rod;
    }

    public String getVpachke() {
        return vpachke;
    }

    public void setVpachke(String vpachke) {
        this.vpachke = vpachke;
    }

    public int getOstatok() {
        return ostatok;
    }

    public void setOstatok(int ostatok) {
        this.ostatok = ostatok;
    }
}
