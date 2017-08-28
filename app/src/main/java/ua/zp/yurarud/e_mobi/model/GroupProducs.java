package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Админ on 15.08.2017.
 */

public class GroupProducs extends RealmObject {
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

    @Required
    private String kod;

    @Required
    private String name;

    private String kod_rod;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;

    public RealmList<GroupProducs> groupProducy;

    public RealmList<Products> producty;

}
