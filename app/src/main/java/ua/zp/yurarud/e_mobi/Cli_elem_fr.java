package ua.zp.yurarud.e_mobi;


import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class Cli_elem_fr extends ListFragment {

    private Realm mRealm;
    List<String> currentProduct;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        TextView twKod = (TextView) getActivity().findViewById(R.id.tw_kod_elem);
        String kod_tov= (String) twKod.getText();
        RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class).equalTo("kod", kod_tov);
        if(kod_tov==""){
            gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);
        }
        else {
             gr0 = mRealm.where(GroupProducs.class).equalTo("kod", kod_tov);
        }
        RealmResults<GroupProducs> nab0 = gr0.findAll();
        currentProduct = new ArrayList<String>();
        for (GroupProducs gp0 : nab0) {
            if (gp0.groupProducy.size() > 0) {
                RealmResults<GroupProducs> podgruppa = gp0.groupProducy.where().findAll();
                for (GroupProducs gp1 : podgruppa) {
                    if (gp1.producty.size() > 0) {
                        currentProduct.add(gp1.getName());
                    }
                }
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, currentProduct);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cli_elem_fr, null);
    }

}
