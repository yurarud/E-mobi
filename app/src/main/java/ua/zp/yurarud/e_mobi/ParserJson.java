package ua.zp.yurarud.e_mobi;

import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import io.realm.Realm;
import ua.zp.yurarud.e_mobi.model.GroupProducs;
import ua.zp.yurarud.e_mobi.model.Products;

/**
 * Created by Админ on 14.08.2017.
 */

public class ParserJson {
    private int act;
    private StringBuilder sb1;
    private View view;



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
        StringBuilder sb1=new StringBuilder();

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

            Snackbar.make(view, "Загрузка завершена!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //tv1=sb1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
