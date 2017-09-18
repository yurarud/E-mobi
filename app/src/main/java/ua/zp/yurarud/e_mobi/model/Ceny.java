package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;

/**
 * Created by Админ on 18.09.2017.
 */

public class Ceny extends RealmObject {

    private double CenaGrn;
    private double CenaNDS;

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

    private double CenaFOP;


}
