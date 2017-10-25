package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Админ on 18.09.2017.
 */

public class Ceny extends RealmObject {

    private int lastNomer;


    private int id;

    public int getLastNomer() {
        return lastNomer;
    }

    public void setLastNomer(int lastNomer) {
        this.lastNomer = lastNomer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
