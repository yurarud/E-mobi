package ua.zp.yurarud.e_mobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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




import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 09.10.2017.
 */

public class Spisok_list extends ListFragment implements AdapterView.OnItemClickListener{
    private Realm mRealm;

    List<ZakazTable> ZTable;
    ZakazAdapter<ZakazTable> b1;
    Zakaz zak;
    int tekposition;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();
        double sum=0;
        mRealm.beginTransaction();
        RealmQuery<Zakaz> zak0 = mRealm.where(Zakaz.class).equalTo("nomer", ZakazActivity.nomer);
        zak = zak0.findFirst();
        RealmResults<ZakazTable> zt=zak.producty.where().findAll();
        ZTable = new ArrayList<ZakazTable>();
        if(zt.size()>0){
            for(ZakazTable zt1:zt){
                ZTable.add(zt1);

            }
            mRealm.commitTransaction();
        }

        b1 = new ZakazAdapter<ZakazTable>(getActivity(), R.layout.item_zakaz, ZTable);
        setListAdapter(b1);

        getListView().setOnItemClickListener(this);
        registerForContextMenu(this.getListView());


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(getActivity().getApplicationContext(), KolvoActivity.class);
        startActivityForResult(intent,1);
        tekposition=position;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


            if (!mRealm.isInTransaction()) {
                mRealm.beginTransaction();
            }
            zak.producty.remove(acmi.position);
            ZTable.get(acmi.position).deleteFromRealm();
            mRealm.commitTransaction();
            ZTable.remove(acmi.position);
            //b1.notifyDataSetChanged();


        //return super.onContextItemSelected(item);
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int kolvo = data.getIntExtra("kolvo", 0);
        int nom_zakaza = ZakazActivity.nomer;
        boolean otpravlen = ZakazActivity.otpravlen;
        if(!otpravlen){
            RealmQuery<Zakaz> zak0=mRealm.where(Zakaz.class).equalTo("nomer", nom_zakaza);
            Zakaz zak=zak0.findFirst();
            if(! mRealm.isInTransaction()){
                mRealm.beginTransaction();}
            ZakazTable zt= zak.producty.get(tekposition);
            zt.setOstatok(kolvo);
            mRealm.commitTransaction();
            //b1.notifyDataSetChanged();
            //Intent intent = new Intent();
            //intent.putExtra("obnovit",1);
        }
    }
}
