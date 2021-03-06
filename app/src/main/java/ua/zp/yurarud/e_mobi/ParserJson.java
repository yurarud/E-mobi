package ua.zp.yurarud.e_mobi;

import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import ua.zp.yurarud.e_mobi.model.Clients;
import ua.zp.yurarud.e_mobi.model.GroupClients;
import ua.zp.yurarud.e_mobi.model.GroupProducs;
import ua.zp.yurarud.e_mobi.model.Products;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 14.08.2017.
 */

public class ParserJson {
    private int act;
    private StringBuilder sb1;
    private View view;
    Message msg;


    /*public ParserJson(int act,StringBuilder sb1)
    {
        this.act=act;
        this.sb1=sb1;
    }*/

    protected void parsJstr(String strJson)  {
        StringBuilder sb1=new StringBuilder();

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            for(int i=0;i<tovary.length();i++)
            {
                JSONObject tovar=tovary.getJSONObject(i);
                sb1.append("Товар "+i+": \n");
                String kod = tovar.getString("kod");
                sb1.append("код "+kod+" \n");
                String cena = tovar.getString("cena");
                sb1.append("цена "+cena+" \n");
            }

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected  void clearTovary(){
        Realm mRealm = Realm.getDefaultInstance();
        //Очищаем таблицы БД
        GroupProducs gp;
        Products pr;


        //Очищаем таблицы БД
        mRealm.beginTransaction();
        RealmQuery<Products> cr0 = mRealm.where(Products.class);
        cr0.findAll().deleteAllFromRealm();
        RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class);
        gr0.findAll().deleteAllFromRealm();
        RealmQuery<ZakazTable> zt0 = mRealm.where(ZakazTable.class);
        zt0.findAll().deleteAllFromRealm();
        RealmQuery<Zakaz> z0 = mRealm.where(Zakaz.class);
        z0.findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    protected void parsTovary(String strJson, View view,int smesh, String kon)  {
        //StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();

        GroupProducs gp;
        Products pr;
        int okon=Integer.parseInt(kon);

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {
                msg = MainActivity.h.obtainMessage(1, i+smesh, okon);
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                //sb1.append("Товар "+i+": \n");

                boolean gruppa = tovar.getBoolean("gruppa");

                if(gruppa)
                {
                    gp = mRealm.createObject(GroupProducs.class);
                    String kod_rod = tovar.getString("kod_rod").trim();
                    gp.setKod_rod(kod_rod);
                    String kod = tovar.getString("kod");
                    gp.setKod(kod);
                    String name = tovar.getString("name");
                    gp.setName(name);
                    int level = tovar.getInt("level");
                    gp.setLevel(level);



                }
                else
                {
                    pr=mRealm.createObject(Products.class);
                    String kod = tovar.getString("kod");
                    pr.setKod(kod);
                    String name = tovar.getString("name");
                    pr.setName(name);
                    String kod_rod = tovar.getString("kod_rod");
                    pr.setKod_rod(kod_rod);
                    String vpachke = tovar.getString("vpachke");
                    pr.setVpachke(vpachke);

                }


                mRealm.commitTransaction();
            }

        mRealm.close();

            /*Snackbar.make(view, "Загрузка товаров завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void  sortTovary(){
        Realm mRealm = Realm.getDefaultInstance();
        RealmQuery<Products> cr0;
        RealmResults<Products> cr1;
        RealmQuery<GroupProducs> gr0;
        RealmResults<GroupProducs> gr1;

        mRealm.beginTransaction();
        cr0 = mRealm.where(Products.class);
        cr1 = cr0.findAll();
        for(Products cli:cr1){
            GroupProducs gr_rod = mRealm.where(GroupProducs.class).equalTo("kod", cli.getKod_rod()).findFirst();
            if(gr_rod!=null) {
                gr_rod.producty.add(cli);
            }
        }
        gr0 = mRealm.where(GroupProducs.class);
        gr1 = gr0.findAll();
        for(GroupProducs gk: gr1){
            if(gk.getKod_rod()!=null) {
                GroupProducs gr_rod = mRealm.where(GroupProducs.class).equalTo("kod", gk.getKod_rod()).findFirst();
                if (gr_rod != null) {
                    gr_rod.groupProducy.add(gk);
                }
            }
        }

        mRealm.commitTransaction();

        /*Snackbar.make(view, "Загрузка товаров завершена!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }

    protected void clearClienty(){

        Realm mRealm = Realm.getDefaultInstance();
        //Очищаем таблицы БД
        mRealm.beginTransaction();
        RealmQuery<Clients> cr0 = mRealm.where(Clients.class);
        cr0.findAll().deleteAllFromRealm();
        RealmQuery<GroupClients> gr0 = mRealm.where(GroupClients.class);
        gr0.findAll().deleteAllFromRealm();
        RealmResults<Clients> cr1;
        RealmResults<GroupClients> gr1;

        mRealm.commitTransaction();

    }

    protected void parsClienty(String strJson, View view, int smesh, String kon)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();
        //Очищаем таблицы БД

        RealmQuery<Clients> cr0 = mRealm.where(Clients.class);

        RealmQuery<GroupClients> gr0 = mRealm.where(GroupClients.class);

        RealmResults<Clients> cr1;
        RealmResults<GroupClients> gr1;
        int okon=Integer.parseInt(kon);

        gr0 = mRealm.where(GroupClients.class);
        RealmResults<GroupClients> lev0 = gr0.findAll();
        GroupClients gp;
        Clients pr;
        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {
                msg = MainActivity.h.obtainMessage(2, i+smesh, okon);
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                //sb1.append("Товар "+i+": \n");

                boolean gruppa = tovar.getBoolean("gruppa");

                if(gruppa)
                {
                    gp = mRealm.createObject(GroupClients.class);
                    String kod_rod = tovar.getString("kod_rod").trim();
                    gp.setKod_rod(kod_rod);
                    String kod = tovar.getString("kod");
                    gp.setKod(kod);
                    String name = tovar.getString("name");
                    gp.setName(name);
                    int level = tovar.getInt("level");
                    gp.setLevel(level);
                }
                else
                {
                    pr=mRealm.createObject(Clients.class);
                    String kod = tovar.getString("kod");
                    pr.setKod(kod);
                    String name = tovar.getString("name");
                    pr.setName(name);
                    String kod_rod = tovar.getString("kod_rod");
                    pr.setKod_rod(kod_rod);
                    String adress = tovar.getString("adress");
                    pr.setAdress(adress);
                    String telephone = tovar.getString("telephone");
                    pr.setTelephone(telephone);
                    String email = tovar.getString("email");
                    pr.setEmail(email);
                }


                mRealm.commitTransaction();
            }


            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected  void sortClienty(View view){

        Realm mRealm = Realm.getDefaultInstance();

        RealmQuery<Clients> cr0 = mRealm.where(Clients.class);

        RealmQuery<GroupClients> gr0 = mRealm.where(GroupClients.class);

        RealmResults<Clients> cr1;
        RealmResults<GroupClients> gr1;

        mRealm.beginTransaction();
        cr0 = mRealm.where(Clients.class);
        cr1 = cr0.findAll();
        for(Clients cli:cr1){
            GroupClients gr_rod = mRealm.where(GroupClients.class).equalTo("kod", cli.getKod_rod()).findFirst();
            if(gr_rod!=null) {
                gr_rod.clienty.add(cli);
            }
        }
        gr0 = mRealm.where(GroupClients.class);
        gr1 = gr0.findAll();
        for(GroupClients gk: gr1){
            GroupClients gr_rod = mRealm.where(GroupClients.class).equalTo("kod", gk.getKod_rod()).findFirst();
            if(gr_rod!=null) {
                gr_rod.groupProducy.add(gk);
            }
        }
        mRealm.commitTransaction();


        mRealm.close();

        /*Snackbar.make(view, "Загрузка клиентов завершена!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/

    }

    protected void parsCeny(String strJson, View view, int smesh, String kon)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();
        int okon=Integer.parseInt(kon);

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {

                msg = MainActivity.h.obtainMessage(3, i+smesh, okon);
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                String kod = tovar.getString("kod");
                Products pr_rod=mRealm.where(Products.class).equalTo("kod", kod).findFirst();
                String cena = tovar.getString("cena");
                if (pr_rod != null)
                {

                    Double cen_grn=Double.valueOf(cena);
                    pr_rod.setCenaGrn(cen_grn);
                    Double cen_nds=Math.round((cen_grn+cen_grn*0.06d)*100d)/100d;
                    pr_rod.setCenaNDS(cen_nds);
                    Double cen_fop=Math.round((cen_grn+cen_grn*0.01d)*100d)/100d;
                    pr_rod.setCenaFOP(cen_fop);

                }




                mRealm.commitTransaction();
            }

            mRealm.close();

            /*Snackbar.make(view, "Загрузка цен завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

/*
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText("Загрузка товаров завершена!");
*/

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void parsOstatki(String strJson, View view, int smesh, String kon)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();
        int okon=Integer.parseInt(kon);

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            mRealm.beginTransaction();
            for(int i=0;i<tovary.length();i++)
            {

                msg = MainActivity.h.obtainMessage(4, i+smesh, okon);
                MainActivity.h.sendMessage(msg);

                JSONObject tovar=tovary.getJSONObject(i);
                String kod = tovar.getString("kod");
                if(kod!=null) {
                    Products pr_rod = mRealm.where(Products.class).equalTo("kod", kod).findFirst();
                    double os = Double.parseDouble(tovar.getString("ostatok"));
                    int ostatok = (int) os;

                    if (pr_rod != null) {
                        pr_rod.setOstatok(ostatok);
                    }
                }

            }
            mRealm.commitTransaction();

            mRealm.close();

            /*Snackbar.make(view, "Загрузка остатков завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

/*
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText("Загрузка товаров завершена!");
*/

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String parsZakaz(int nomer, View view, String tp){
        JSONObject dokum= new JSONObject();
        JSONObject stroka= new JSONObject();
        JSONArray products = new JSONArray();

        Realm mRealm = Realm.getDefaultInstance();

        String json="";

        RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
        Zakaz zak = zak0.findFirst();
        try {
            dokum.put("idUzla",tp);
            dokum.put("nomer",tp+" "+nomer);
            dokum.put("komment",zak.getKomment());
            dokum.put("klient",zak.getKlient().getKod());
            dokum.put("tipCeny",zak.getTipCeny());
            RealmResults<ZakazTable> zt=zak.producty.where().findAll();
            if(zt.size()>0){
                int ind=0;
                for(ZakazTable zt1:zt){
                    stroka.put("tovar",zt1.getTovar().getKod());
                    stroka.put("ostatok",zt1.getOstatok());
                    stroka.put("cena",zt1.getCena());
                    products.put(ind,stroka);
                    stroka=null;
                    stroka=new JSONObject();
                    ind++;
                }
              }
            dokum.put("PRODUCT",products);
            json=dokum.toString();
            Snackbar.make(view, "Выгрузка заказа завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return json;
    }

}
