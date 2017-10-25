package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Админ on 10.10.2017.
 */

public class IntegerObject extends RealmObject {

    private Integer aInteger;

    public Integer getInteger() {
        return aInteger;
    }

    public void setInteger(Integer integer) {
        this.aInteger = integer;
    }
}
