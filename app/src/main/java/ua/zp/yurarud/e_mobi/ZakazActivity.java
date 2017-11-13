package ua.zp.yurarud.e_mobi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.Ceny;
import ua.zp.yurarud.e_mobi.model.Clients;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 02.10.2017.
 */

public class ZakazActivity extends FragmentActivity {

    private Realm mRealm;

    static int vozvrat=0;

    static String kod_kli;
    static String naim_kli;
    static int tipCeny;
    static int nomer;
    static boolean otpravlen;
    TextView naimenov;
    TextView shapka;
    TextView komm;


    AlertDialog.Builder ad;
    private final int IDD_THREE_BUTTONS = 0;



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(nomer == -1){
            naim_kli = null;
            kod_kli = null;
            otpravlen = false;
            if (!mRealm.isInTransaction()) {
                mRealm.beginTransaction();
            }
            RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
            RealmResults<Zakaz> zak1 = zak0.findAll();
            if (zak1.size() > 0) {
                for (Zakaz zak : zak1) {
                    ArrayList<ZakazTable> lzt=new ArrayList<>();
                    for(ZakazTable zt2:zak.producty){
                        lzt.add(zt2);
                        //zt2.deleteFromRealm();
                    }
                    for(int i=0;i<lzt.size();i++){
                        lzt.get(i).deleteFromRealm();
                    }
                    zak.deleteFromRealm();
                }
            }

        }



    }

    @Override
    protected void onResume(){
        super.onResume();
        naimenov= (TextView) findViewById(R.id.tvVybKlioent);
        komm = (TextView) findViewById(R.id.ed_komm);
        TextView sumac = (TextView) findViewById(R.id.tvVybKlioent1);



        if(naim_kli !=null){
            naimenov.setText(naim_kli);

        }
        if(kod_kli!=null) {
            if(! mRealm.isInTransaction()){
                mRealm.beginTransaction();}
            RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
            Zakaz zak = zak0.findFirst();
            if (zak != null) {
                RealmQuery<Clients> cli0 = mRealm.where(Clients.class).equalTo("kod", kod_kli);
                Clients cli1 = cli0.findFirst();
                zak.setKlient(cli1);
                zak.setTipCeny(tipCeny);
                zak.setKlient_name(cli1.getName());

                if(zak.producty.size()>0){
                    double sum=0.0;
                    zak.setSumma(0.0);
                    for(ZakazTable zt1:zak.producty){
                        sum+=(zt1.getCena()*zt1.getOstatok());
                    }

                    zak.setSumma(zak.getSumma()+Math.round(sum*100.0)/100.0);

                    //mRealm.commitTransaction();

                }
                if (sumac != null) {
                    sumac.setText("Итого: "+String.valueOf(zak.getSumma()));
                }

            }
            mRealm.commitTransaction();

            shapka = (TextView) findViewById(R.id.tv_zakaz_shapka);
            shapka.setText("Заказ № "+String.valueOf(nomer));
            komm.setText(zak.getKomment());

            if(nomer != -1) {
                Spinner spinner = (Spinner) findViewById(R.id.spTypCeny);
                spinner.setSelection(zak.getTipCeny()-1);
            }


            Spisok_list fragment1 = new Spisok_list();
           /* getSupportFragmentManager().beginTransaction()
                    .detach(fragment1).commit();*/
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_containerSpisok, fragment1).commit();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        vozvrat = intent.getIntExtra("zakaz",0);

        if(vozvrat==0 && savedInstanceState == null) {
            nomer = -1;
            naim_kli = null;
            kod_kli = null;
            otpravlen = false;
            if (!mRealm.isInTransaction()) {
                mRealm.beginTransaction();
            }
            RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
            RealmResults<Zakaz> zak1 = zak0.findAll();
            if (zak1.size() > 0) {
                for (Zakaz zak : zak1) {
                    ArrayList<ZakazTable> lzt=new ArrayList<>();
                    for(ZakazTable zt2:zak.producty){
                        lzt.add(zt2);
                        //zt2.deleteFromRealm();
                    }
                    for(int i=0;i<lzt.size();i++){
                        lzt.get(i).deleteFromRealm();
                    }
                    zak.deleteFromRealm();
                }
            }

            Zakaz zak = mRealm.createObject(Zakaz.class);
            zak.setNomer(nomer);
            zak.setKomment(" ");
            mRealm.commitTransaction();
        }
        if(vozvrat==1){
            nomer=intent.getIntExtra("nomer",-1);
            RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
            Zakaz zak = zak0.findFirst();
            naim_kli = zak.getKlient_name();
            kod_kli = zak.getKlient().getKod();
            tipCeny = zak.getTipCeny();
            otpravlen = zak.isOtpravlen();
        }

        setContentView(R.layout.activity_zakaz);

        shapka = (TextView) findViewById(R.id.tv_zakaz_shapka);
        shapka.setText("Заказ № "+String.valueOf(nomer));

        Button btnClients = (Button) findViewById(R.id.btnVybKlioent);
        btnClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                intent.putExtra("zakaz",1);
                startActivity(intent);
            }
        });


        Button btnProdicts = (Button) findViewById(R.id.btnPodbor);
        btnProdicts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("zakaz",1);
                startActivity(intent);
            }
        });

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //if (nomer < 1) {
                if (!otpravlen) {
                    Ceny cen1;
                    RealmQuery<Ceny> cen0 = mRealm.where(Ceny.class).equalTo("id", 0);
                    cen1 = cen0.findFirst();
                    if (cen1 == null) {

                        //nomer = 1;
                        if (!mRealm.isInTransaction()) {
                            mRealm.beginTransaction();
                        }
                        cen1 = mRealm.createObject(Ceny.class);
                        cen1.setId(0);
                        cen1.setLastNomer(0);
                        mRealm.commitTransaction();

                    }
                        if (nomer == -1) {

                        nomer = cen1.getLastNomer() + 1;
                        if (!mRealm.isInTransaction()) {
                            mRealm.beginTransaction();
                        }
                        cen1.setLastNomer(nomer);
                        RealmQuery<Zakaz> zak2 = mRealm.where(Zakaz.class).equalTo("nomer", -1);
                        Zakaz zak3 = zak2.findFirst();
                        zak3.setNomer(nomer);
                        komm = (TextView) findViewById(R.id.ed_komm);
                        zak3.setKomment(String.valueOf(komm.getText()));
                        mRealm.commitTransaction();
                            Snackbar.make(view, "Документ сохранен!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            shapka = (TextView) findViewById(R.id.tv_zakaz_shapka);
                            shapka.setText("Заказ № "+String.valueOf(nomer));
                        }
                        else{

                            if (!mRealm.isInTransaction()) {
                                mRealm.beginTransaction();
                            }
                            RealmQuery<Zakaz> zak2 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
                            Zakaz zak3 = zak2.findFirst();
                            zak3.setTipCeny(tipCeny);
                            komm = (TextView) findViewById(R.id.ed_komm);
                            zak3.setKomment(String.valueOf(komm.getText()));
                            mRealm.commitTransaction();
                            Snackbar.make(view, "Документ сохранен!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                }
                else{
                    Snackbar.make(view, "Доступен только для чтения!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                /*if(otpravlen==false){
                    SOAP_Go sg =new SOAP_Go(4,view);
                    sg.start();
                }*/
            }
        });

        Button btnGo = (Button) findViewById(R.id.btn_otpravit);
        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            if(nomer!=-1) {
                if (otpravlen == false) {
                    SOAP_Go sg = new SOAP_Go(4, view);
                    sg.start();
                    try {
                        sg.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!mRealm.isInTransaction()) {
                        mRealm.beginTransaction();
                    }
                    RealmQuery<Zakaz> zak2 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
                    Zakaz zak3 = zak2.findFirst();
                    zak3.setOtpravlen(otpravlen);
                    mRealm.commitTransaction();
                } else {
                    Snackbar.make(view, "Заказ с таким номером уже был выгружен!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            else{
                Snackbar.make(view, "Сначала сохраните заказ!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            }
        });

        String[] data={"Наличные","НДС","ФОП"};
        final int[] typCeny={1,2,3};
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spTypCeny);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tipCeny=typCeny[position];
                double cena=0;
                if (!mRealm.isInTransaction()) {
                    mRealm.beginTransaction();
                }
                RealmQuery<Zakaz> zak2 = mRealm.where(Zakaz.class).equalTo("nomer", nomer);
                Zakaz zak3 = zak2.findFirst();
                for(ZakazTable zt1:zak3.producty){
                    switch (tipCeny){
                        case 1: {
                            cena = zt1.getTovar().getCenaGrn();
                            break;
                        }
                        case 2: {
                            cena = zt1.getTovar().getCenaNDS();
                            break;
                        }
                        case 3: {
                            cena = zt1.getTovar().getCenaFOP();
                            break;
                        }
                    }
                    zt1.setCena(cena);
                }
                mRealm.commitTransaction();
                Spisok_list fragment1 = new Spisok_list();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containerSpisok, fragment1).commit();

                        //.add(R.id.fragment_containerSpisok, fragment1).commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if(findViewById(R.id.fragment_containerSpisok) != null){

            if (savedInstanceState != null) {
                return;
            }

            Spisok_list fragment1 = new Spisok_list();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_containerSpisok, fragment1).commit();



        }

    }
}