package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Админ on 18.09.2017.
 */

public class GroupClients extends RealmObject {

    @Required
    private String kod;


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

       @Required
    private String name;

    public String getKod_rod() {
        return kod_rod;
    }

    public void setKod_rod(String kod_rod) {
        this.kod_rod = kod_rod;
    }

    private String kod_rod;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;

    public RealmList<Clients> clienty;

    public RealmList<GroupClients> groupProducy;
}
