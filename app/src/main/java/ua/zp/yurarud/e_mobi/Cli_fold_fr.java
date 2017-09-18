package ua.zp.yurarud.e_mobi;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.GroupProducs;

/**
 * Created by Админ on 28.08.2017.
 */

public class Cli_fold_fr extends ListFragment {

    private Realm mRealm;
    List<String> cli_Folders;
    List<String> kod_Cli_Folders;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);
        RealmResults<GroupProducs> nab0 = gr0.findAll();
        cli_Folders = new ArrayList<String>();
        kod_Cli_Folders = new ArrayList<String>();

        for (GroupProducs gp0 : nab0) {
            if (gp0.groupProducy.size() > 0) {
                cli_Folders.add(gp0.getName());
                kod_Cli_Folders.add(gp0.getKod());
            }
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, cli_Folders);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cli_fold_fr, null);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String kod_tov=kod_Cli_Folders.get(position);
        TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
        twKod.setText(kod_tov);
        Fragment fragment =  getFragmentManager().findFragmentById(R.id.fragment2);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();

    }

}
