package ua.zp.yurarud.e_mobi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.Clients;
import ua.zp.yurarud.e_mobi.model.GroupProducs;
import ua.zp.yurarud.e_mobi.model.Products;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 28.08.2017.
 */

public class Pro_fold_fr extends ListFragment {

    private Realm mRealm;
    List<String> cli_Folders;
    List<String> kod_Cli_Folders;
    List<Products> productes;
    int tekposition;
    static String KOD_TOV="";
    static int Folder=0;
    static Map<String,String[]> Uroven = new HashMap<String,String[]>();
    EditText strFind;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        if(Uroven.size()==0){
            KOD_TOV="";
        }


       // RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);
        RealmQuery<GroupProducs> gr0;// = mRealm.where(GroupProducs.class).equalTo("kod", Cli_fold_fr.KOD_TOV);
        if(Pro_fold_fr.KOD_TOV==""){
            gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);

            RealmResults<GroupProducs> nab0 = gr0.findAllSorted("name");

            cli_Folders = new ArrayList<String>();
            kod_Cli_Folders = new ArrayList<String>();

            for (GroupProducs gp0 : nab0) {
                if (gp0.groupProducy.size() > 0) {
                    Folder=0;
                    cli_Folders.add(gp0.getName());
                    kod_Cli_Folders.add(gp0.getKod());
                }
            }

        }
        else {
            //kod_Cli_Folders
            gr0 = mRealm.where(GroupProducs.class).equalTo("kod", Pro_fold_fr.KOD_TOV);
            RealmResults<GroupProducs> nab0 = gr0.findAllSorted("name");
            cli_Folders = new ArrayList<String>();
            kod_Cli_Folders = new ArrayList<String>();

            for (GroupProducs gp0 : nab0) {
                if (gp0.groupProducy.size() > 0) {
                    Folder=0;
                    RealmResults<GroupProducs> podgruppa = gp0.groupProducy.where().findAllSorted("name");
                    for (GroupProducs gp1 : podgruppa) {
                        if (gp1.producty.size() > 0) {
                            cli_Folders.add(gp1.getName());
                            kod_Cli_Folders.add(gp1.getKod());
                        }
                    }
                }
                else{
                    if(gp0.producty.size()>0){
                        Folder=1;
                        productes = new ArrayList<Products>();
                        RealmResults<Products> podgruppa = gp0.producty.where().findAllSorted("name");
                        for (Products gp1 : podgruppa) {
                            cli_Folders.add(gp1.getName());
                            kod_Cli_Folders.add(gp1.getKod());
                            productes.add(gp1);
                        }
                    }
                }
            }

        }

        if(Folder==0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, cli_Folders);
            setListAdapter(adapter);
        }
        else
        {
            ProductAdapter<Products> b1 = new ProductAdapter<Products>(getActivity(), R.layout.item_clients, productes,ZakazActivity.tipCeny);
            setListAdapter(b1);
        }



        ImageButton btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            int level;
            @Override
            public void onClick(View view) {
                String key= Pro_fold_fr.KOD_TOV;
                if(Uroven.size()>0){
                    String[] mas=Uroven.remove(key);
                    String newkey=mas[0];
                    Pro_fold_fr.KOD_TOV=newkey;
                    TextView twName = (TextView) getActivity().findViewById(R.id.tw_kod_elem_znach);
                    twName.setText(mas[1]);
                    TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
                    twKod.setText(mas[0]);


                    Pro_fold_fr fragment2 = new Pro_fold_fr();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_containerF, fragment2);
                    //transaction.addToBackStack(kod_tov);
                    transaction.commit();
                }
                else {
                    getActivity().finish();
                }

            }
        });

        strFind = (EditText) getActivity().findViewById(R.id.etFind);

        Button btnClear = (Button) getActivity().findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            int level;
            @Override
            public void onClick(View view) {
                strFind.setText("");

                if(Uroven.size()==0){
                    KOD_TOV="";
                }


                // RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);
                RealmQuery<GroupProducs> gr0;// = mRealm.where(GroupProducs.class).equalTo("kod", Cli_fold_fr.KOD_TOV);
                if(Pro_fold_fr.KOD_TOV==""){
                    gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);

                    RealmResults<GroupProducs> nab0 = gr0.findAllSorted("name");

                    cli_Folders = new ArrayList<String>();
                    kod_Cli_Folders = new ArrayList<String>();

                    for (GroupProducs gp0 : nab0) {
                        if (gp0.groupProducy.size() > 0) {
                            Folder=0;
                            cli_Folders.add(gp0.getName());
                            kod_Cli_Folders.add(gp0.getKod());
                        }
                    }

                }
                else {
                    //kod_Cli_Folders
                    gr0 = mRealm.where(GroupProducs.class).equalTo("kod", Pro_fold_fr.KOD_TOV);
                    RealmResults<GroupProducs> nab0 = gr0.findAllSorted("name");
                    cli_Folders = new ArrayList<String>();
                    kod_Cli_Folders = new ArrayList<String>();

                    for (GroupProducs gp0 : nab0) {
                        if (gp0.groupProducy.size() > 0) {
                            Folder=0;
                            RealmResults<GroupProducs> podgruppa = gp0.groupProducy.where().findAllSorted("name");
                            for (GroupProducs gp1 : podgruppa) {
                                if (gp1.producty.size() > 0) {
                                    cli_Folders.add(gp1.getName());
                                    kod_Cli_Folders.add(gp1.getKod());
                                }
                            }
                        }
                        else{
                            if(gp0.producty.size()>0){
                                Folder=1;
                                productes = new ArrayList<Products>();
                                RealmResults<Products> podgruppa = gp0.producty.where().findAllSorted("name");
                                for (Products gp1 : podgruppa) {
                                    cli_Folders.add(gp1.getName());
                                    kod_Cli_Folders.add(gp1.getKod());
                                    productes.add(gp1);
                                }
                            }
                        }
                    }

                }

                if(Folder==0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, cli_Folders);
                    setListAdapter(adapter);
                }
                else
                {
                    ProductAdapter<Products> b1 = new ProductAdapter<Products>(getActivity(), R.layout.item_clients, productes,ZakazActivity.tipCeny);
                    setListAdapter(b1);
                }


            }
        });

        Button btnFind = (Button) getActivity().findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {

            int level;
            @Override
            public void onClick(View view) {

                String stF = String.valueOf(strFind.getText());
                RealmQuery<Products> gr0;
                gr0 = mRealm.where(Products.class).contains("name", stF);
                        //.beginsWith("name", stF);
                RealmResults<Products> nab0 = gr0.findAllSorted("name");
                cli_Folders = new ArrayList<String>();
                kod_Cli_Folders = new ArrayList<String>();
                Folder=1;
                productes = new ArrayList<Products>();
                for (Products gp1 : nab0) {
                    cli_Folders.add(gp1.getName());
                    kod_Cli_Folders.add(gp1.getKod());
                    productes.add(gp1);
                }



                    ProductAdapter<Products> b1 = new ProductAdapter<Products>(getActivity(), R.layout.item_clients, productes,ZakazActivity.tipCeny);
                setListAdapter(b1);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cli_fold_fr, null);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(Folder==0) {
            String kod_tov = kod_Cli_Folders.get(position);
            String naim_tov = cli_Folders.get(position);

            TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
            TextView twName = (TextView) getActivity().findViewById(R.id.tw_kod_elem_znach);

            String kod_rod = (String) twKod.getText();
            String kod_rod_name = (String) twName.getText();
            twKod.setText(kod_tov);
            twName.setText(naim_tov);
            String[] mas={kod_rod,kod_rod_name};

            Uroven.put(kod_tov,mas);

            Pro_fold_fr.KOD_TOV = kod_tov;

            Pro_fold_fr fragment2 = new Pro_fold_fr();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_containerF, fragment2);
            //transaction.addToBackStack(kod_tov);
            transaction.commit();
        }
        else {
            if(ProductActivity.vozvrat==1){
                Intent intent;
                intent = new Intent(getActivity().getApplicationContext(), KolvoActivity.class);
                startActivityForResult(intent,1);
                tekposition=position;

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int kolvo=data.getIntExtra("kolvo",0);
        int nom_zakaza=ZakazActivity.nomer;
        boolean otpravlen = ZakazActivity.otpravlen;
        if(nom_zakaza==-1 || !otpravlen){
            if(! mRealm.isInTransaction()){
            mRealm.beginTransaction();}
            RealmQuery<Zakaz> zak0=mRealm.where(Zakaz.class).equalTo("nomer", nom_zakaza);
            Zakaz zak=zak0.findFirst();
            Products pr=productes.get(tekposition);
            if(zak!=null){
                ZakazTable zt = null;
                if(zak.producty.size()>0){
                    RealmResults<ZakazTable> zp = zak.producty.where().findAll();
                    for(ZakazTable zt1:zp){
                        if(zt1.getTovar().equals(pr)){
                            zt=zt1;
                            break;
                        }
                    }
                }
                if(zt==null){
                    zt=mRealm.createObject(ZakazTable.class);

                }

                zt.setTovar(pr);
                zt.setTovar_name(pr.getName());



                zt.setOstatok(kolvo);
                switch (ZakazActivity.tipCeny){
                    case 1: {
                        zt.setCena(pr.getCenaGrn());
                        break;
                    }
                    case 2: {
                        zt.setCena(pr.getCenaNDS());
                        break;
                    }
                    case 3: {
                        zt.setCena(pr.getCenaFOP());
                        break;
                    }
                }
                zak.producty.add(zt);
            }
            mRealm.commitTransaction();
        }
        //getActivity().finish();
    }
}
