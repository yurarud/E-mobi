package ua.zp.yurarud.e_mobi.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Админ on 25.08.2017.
 */

public class Clients extends RealmObject {

    @Required
    private String kod;

    @Required
    private String name;

    private String adress;
    private String telephone;
    private String email;

}
