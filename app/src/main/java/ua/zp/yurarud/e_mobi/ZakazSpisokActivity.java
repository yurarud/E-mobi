package ua.zp.yurarud.e_mobi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.Ceny;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 16.10.2017.
 */

public class ZakazSpisokActivity extends FragmentActivity {
    private Realm mRealm;

    static int nomer;
    static boolean otpravlen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_spisok);

        if(findViewById(R.id.fragment_containerSpisokZakazov) != null){

            if (savedInstanceState != null) {
                return;
            }

            Spisok_zakaz fragment1 = new Spisok_zakaz();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_containerSpisokZakazov, fragment1).commit();

        }

        Button btnGo = (Button) findViewById(R.id.btn_optpr_optom);
        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RealmQuery<Zakaz> qzt = mRealm.where(Zakaz.class).equalTo("box", true);
                RealmResults<Zakaz> zt = qzt.findAll();
                if (zt.size() > 0) {
                    for(Zakaz zt1:zt){
                        if (!zt1.isOtpravlen()) {
                            nomer=zt1.getNomer();
                            otpravlen=zt1.isOtpravlen();
                            SOAP_Go sg = new SOAP_Go(5, view);
                            sg.start();
                            try {
                                sg.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mRealm.beginTransaction();
                            zt1.setOtpravlen(otpravlen);
                            zt1.setBox(false);
                            mRealm.commitTransaction();
                        } else {
                            Snackbar.make(view, "Заказ с таким номером уже был выгружен!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                    finish();

                }


            }
        });

        Button btnOpen = (Button) findViewById(R.id.btn_open);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RealmQuery<Zakaz> qzt = mRealm.where(Zakaz.class).equalTo("box", true);
                Zakaz zt1 = qzt.findFirst();
                if (zt1!=null) {

                        //if (!zt1.isOtpravlen()) {
                            Intent intent = new Intent(getApplicationContext(), ZakazActivity.class);
                            intent.putExtra("zakaz",1);
                            intent.putExtra("nomer",zt1.getNomer());
                            startActivity(intent);
                       /* } *//*else {
                            Snackbar.make(view, "Заказ с таким номером уже был выгружен!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }*//*
*/

                }


            }
        });

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RealmQuery<Zakaz> qzt = mRealm.where(Zakaz.class).equalTo("box", true);
                RealmResults<Zakaz> zt = qzt.findAll();
                if (!mRealm.isInTransaction()) {
                    mRealm.beginTransaction();
                }
                if (zt.size() > 0) {
                    for(Zakaz zt1:zt){
                        ArrayList<ZakazTable> lzt=new ArrayList<>();
                        for(ZakazTable zt2:zt1.producty){
                            //zt2.deleteFromRealm();
                            lzt.add(zt2);
                        }
                        for(int i=0;i<lzt.size();i++){
                            lzt.get(i).deleteFromRealm();
                        }
                        zt1.deleteFromRealm();
                    }
                }
                mRealm.commitTransaction();
                finish();
            }
        });

        Button btnKopy = (Button) findViewById(R.id.btn_kopy);
        btnKopy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RealmQuery<Zakaz> qzt = mRealm.where(Zakaz.class).equalTo("box", true);
                Zakaz zt1 = qzt.findFirst();
                if (zt1!=null) {
                    if (!mRealm.isInTransaction()) {
                        mRealm.beginTransaction();
                    }
                    Zakaz zak = mRealm.createObject(Zakaz.class);
                    Ceny cen1;
                    RealmQuery<Ceny> cen0 = mRealm.where(Ceny.class).equalTo("id", 0);
                    cen1 = cen0.findFirst();
                    nomer = cen1.getLastNomer() + 1;
                    zak.setNomer(nomer);
                    zak.setKomment(" ");
                    zak.setKlient(zt1.getKlient());
                    zak.setTipCeny(zt1.getTipCeny());
                    zak.setKlient_name(zt1.getKlient().getName());
                    zak.setOtpravlen(false);
                    zak.setSumma(zt1.getSumma());
                    for(ZakazTable zt2:zt1.producty){
                        ZakazTable zt=mRealm.createObject(ZakazTable.class);
                        zt.setTovar(zt2.getTovar());
                        zt.setTovar_name(zt2.getTovar().getName());
                        zt.setCena(zt2.getCena());
                        zt.setOstatok(zt2.getOstatok());
                        zak.producty.add(zt);
                    }
                    mRealm.commitTransaction();
                    Intent intent = new Intent(getApplicationContext(), ZakazActivity.class);
                    intent.putExtra("zakaz",1);
                    intent.putExtra("nomer",zak.getNomer());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
