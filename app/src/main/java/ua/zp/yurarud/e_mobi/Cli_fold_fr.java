package ua.zp.yurarud.e_mobi;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
import ua.zp.yurarud.e_mobi.model.GroupClients;

/**
 * Created by Админ on 28.08.2017.
 */

public class Cli_fold_fr extends ListFragment {

    private Realm mRealm;
    List<String> cli_Folders;
    List<String> kod_Cli_Folders;
    static String KOD_TOV="";
    static int Folder=0;
    static Map<String,String[]> Uroven = new HashMap<String,String[]>();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        if(Uroven.size()==0){
            KOD_TOV="";
        }

        int level=100;

        RealmQuery<GroupClients> gr0;
        gr0 = mRealm.where(GroupClients.class);
        RealmResults<GroupClients> lev0 = gr0.findAll();
        for (GroupClients gr4 : lev0){
            if(gr4.getLevel()<level){
                level=gr4.getLevel();
            }
        }


        if(Cli_fold_fr.KOD_TOV==""){
            gr0 = mRealm.where(GroupClients.class).equalTo("level", level);
            RealmResults<GroupClients> nab0 = gr0.findAll();
            cli_Folders = new ArrayList<String>();
            kod_Cli_Folders = new ArrayList<String>();

            for (GroupClients gp0 : nab0) {
                if (gp0.groupProducy.size() > 0) {
                    Folder=0;
                    cli_Folders.add(gp0.getName());
                    kod_Cli_Folders.add(gp0.getKod());
                }
            }

        }
        else {
            //kod_Cli_Folders
            gr0 = mRealm.where(GroupClients.class).equalTo("kod", Cli_fold_fr.KOD_TOV);
            RealmResults<GroupClients> nab0 = gr0.findAll();
            cli_Folders = new ArrayList<String>();
            kod_Cli_Folders = new ArrayList<String>();

            for (GroupClients gp0 : nab0) {
                if (gp0.groupProducy.size() > 0) {
                    Folder=0;
                    RealmResults<GroupClients> podgruppa = gp0.groupProducy.where().findAll();
                    for (GroupClients gp1 : podgruppa) {
                       // if (gp1.clienty.size() > 0) {
                            cli_Folders.add(gp1.getName());
                            kod_Cli_Folders.add(gp1.getKod());
                       // }
                    }
                }
                else{
                    if(gp0.clienty.size()>0){
                        Folder=1;
                        RealmResults<Clients> podgruppa = gp0.clienty.where().findAll();
                        for (Clients gp1 : podgruppa) {
                            cli_Folders.add(gp1.getName());
                            kod_Cli_Folders.add(gp1.getKod());
                        }
                    }
                }
            }

        }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, cli_Folders);
            setListAdapter(adapter);


        ImageButton btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            int level;
            @Override
            public void onClick(View view) {
                String key=Cli_fold_fr.KOD_TOV;
                if(Uroven.size()>0){
                    String[] mas=Uroven.remove(key);
                    String newkey=mas[0];
                    Cli_fold_fr.KOD_TOV=newkey;
                    TextView twName = (TextView) getActivity().findViewById(R.id.tw_kod_elem_znach);
                    twName.setText(mas[1]);
                    TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
                    twKod.setText(mas[0]);


                    Cli_fold_fr fragment2 = new Cli_fold_fr();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_containerF, fragment2);
                    //transaction.addToBackStack(kod_tov);
                    transaction.commit();
                }

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

        String kod_tov = kod_Cli_Folders.get(position);
        String naim_tov = cli_Folders.get(position);

        if(Folder==0) {

            TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
            TextView twName = (TextView) getActivity().findViewById(R.id.tw_kod_elem_znach);

            String kod_rod = (String) twKod.getText();
            String kod_rod_name = (String) twName.getText();
            twKod.setText(kod_tov);
            twName.setText(naim_tov);
            String[] mas={kod_rod,kod_rod_name};

            Uroven.put(kod_tov,mas);

            Cli_fold_fr.KOD_TOV = kod_tov;

            Cli_fold_fr fragment2 = new Cli_fold_fr();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_containerF, fragment2);
            //transaction.addToBackStack(kod_tov);
            transaction.commit();
        }
        else{
            if(ClientActivity.vozvrat==1){
                ZakazActivity.kod_kli=kod_tov;
                ZakazActivity.naim_kli =naim_tov;
                getActivity().finish();
            }
        }
    }

}
