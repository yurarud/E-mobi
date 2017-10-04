package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Админ on 25.08.2017.
 */

public class Clients extends RealmObject {

    @Required
    private String kod;

    private String kod_rod;

    public String getKod_rod() {
        return kod_rod;
    }

    public void setKod_rod(String kod_rod) {
        this.kod_rod = kod_rod;
    }

    @Required
    private String name;

    private String adress;
    private String telephone;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

}
