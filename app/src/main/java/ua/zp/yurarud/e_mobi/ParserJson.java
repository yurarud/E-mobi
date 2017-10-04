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
import ua.zp.yurarud.e_mobi.model.Ceny;
import ua.zp.yurarud.e_mobi.model.Clients;
import ua.zp.yurarud.e_mobi.model.GroupClients;
import ua.zp.yurarud.e_mobi.model.GroupProducs;
import ua.zp.yurarud.e_mobi.model.Products;

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

    protected void parsTovary(String strJson, View view)  {
        //StringBuilder sb1=new StringBuilder();



        Realm mRealm = Realm.getDefaultInstance();
        //Очищаем таблицы БД
        mRealm.beginTransaction();
        GroupProducs gp = mRealm.createObject(GroupProducs.class);
        gp.deleteFromRealm();
        Products pr=mRealm.createObject(Products.class);
        pr.deleteFromRealm();
        mRealm.commitTransaction();
        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {
                msg = MainActivity.h.obtainMessage(1, i, tovary.length());
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                //sb1.append("Товар "+i+": \n");

                boolean gruppa = tovar.getBoolean("gruppa");

                if(gruppa)
                {
                    gp = mRealm.createObject(GroupProducs.class);
                    String kod_rod = tovar.getString("kod_rod").trim();

                    GroupProducs gr_rod = mRealm.where(GroupProducs.class).equalTo("kod", kod_rod).findFirst();
                        if (gr_rod != null) {
                            String kod = tovar.getString("kod");
                            gp.setKod(kod);
                            String name = tovar.getString("name");
                            gp.setName(name);
                            int level = tovar.getInt("level");
                            gp.setLevel(level);
                            gp.setKod_rod(kod_rod);
                            gr_rod.groupProducy.add(gp);
                        }

                        else
                        {
                            String kod = tovar.getString("kod");
                            gp.setKod(kod);
                            String name = tovar.getString("name");
                            gp.setName(name);
                            int level = tovar.getInt("level");
                            gp.setLevel(level);

                        }
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

                    GroupProducs gr_rod = mRealm.where(GroupProducs.class).equalTo("kod", kod_rod).findFirst();
                    if (gr_rod != null)
                    {
                        gr_rod.producty.add(pr);
                    }

                }


                mRealm.commitTransaction();
            }

        mRealm.close();

            Snackbar.make(view, "Загрузка товаров завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


          //  MainActivity.tablo.setText("Загрузка товаров завершена!");

/*          Main
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText("Загрузка товаров завершена!");
*/

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void parsClienty(String strJson, View view)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();
        //Очищаем таблицы БД
        mRealm.beginTransaction();
        RealmQuery<Clients> cr0 = mRealm.where(Clients.class);
        RealmResults<Clients> cr1 = cr0.findAll();
        for(Clients cli:cr1){
            cli.deleteFromRealm();
        }
        RealmQuery<GroupClients> gr0 = mRealm.where(GroupClients.class);
        RealmResults<GroupClients> gr1 = gr0.findAll();
        for(GroupClients gk: gr1){
            gk.deleteFromRealm();
        }
        mRealm.commitTransaction();



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
                msg = MainActivity.h.obtainMessage(2, i, tovary.length());
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

            Snackbar.make(view, "Загрузка клиентов завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void parsCeny(String strJson, View view)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {

                msg = MainActivity.h.obtainMessage(3, i, tovary.length());
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                String kod = tovar.getString("kod");
                Products pr_rod=mRealm.where(Products.class).equalTo("kod", kod).findFirst();
                String cena = tovar.getString("cena");
                if (pr_rod != null)
                {
                    Ceny cen=mRealm.createObject(Ceny.class);
                    Double cen_grn=Double.valueOf(cena);
                    cen.setCenaGrn(cen_grn);
                    Double cen_nds=Math.round((cen_grn+cen_grn*0.06d)*100d)/100d;
                    cen.setCenaNDS(cen_nds);
                    Double cen_fop=Math.round((cen_grn+cen_grn*0.01d)*100d)/100d;
                    cen.setCenaFOP(cen_fop);
                    pr_rod.Cena.add(cen);
                }




                mRealm.commitTransaction();
            }

            mRealm.close();

            Snackbar.make(view, "Загрузка цен завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

/*
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText("Загрузка товаров завершена!");
*/

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void parsOstatki(String strJson, View view)  {
        StringBuilder sb1=new StringBuilder();

        Realm mRealm = Realm.getDefaultInstance();

        try {
            JSONObject dataJsonObj = null;
            dataJsonObj = new JSONObject(strJson);
            JSONArray tovary = dataJsonObj.getJSONArray("PRODUCT");
            int j=0;
            for(int i=0;i<tovary.length();i++)
            {

                msg = MainActivity.h.obtainMessage(4, i, tovary.length());
                MainActivity.h.sendMessage(msg);

                mRealm.beginTransaction();

                JSONObject tovar=tovary.getJSONObject(i);
                String kod = tovar.getString("kod");
                Products pr_rod=mRealm.where(Products.class).equalTo("kod", kod).findFirst();
                int ostatok = Integer.valueOf(tovar.getString("ostatok"));

                if (pr_rod != null)
                {
                    pr_rod.setOstatok(ostatok);
                }




                mRealm.commitTransaction();
            }

            mRealm.close();

            Snackbar.make(view, "Загрузка остатков завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

/*
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText("Загрузка товаров завершена!");
*/

            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
