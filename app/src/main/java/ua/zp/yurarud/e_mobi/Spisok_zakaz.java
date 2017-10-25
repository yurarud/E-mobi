package ua.zp.yurarud.e_mobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 09.10.2017.
 */

public class Spisok_zakaz extends ListFragment {
    private Realm mRealm;

    List<Zakaz> ZTable;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

       // mRealm.beginTransaction();
        RealmQuery<Zakaz> qzt = mRealm.where(Zakaz.class);
        RealmResults<Zakaz> zt = qzt.findAll();
        if(zt.size()>0){
            ZTable = new ArrayList<Zakaz>();
            for(Zakaz zt1:zt){
                ZTable.add(zt1);
            }

            ZakazSpisokAdapter<Zakaz> b1 = new ZakazSpisokAdapter<Zakaz>(getActivity(), R.layout.item_spisok_zakazov, ZTable);
            setListAdapter(b1);
        }

        /*TextView tvItem = (TextView) getActivity().findViewById(R.id.tv_zakaz_summa);
        tvItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("zakaz",1);
                startActivity(intent);
            }
        });*/

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity().getApplicationContext(), ZakazActivity.class);
        intent.putExtra("zakaz",1);
        Zakaz z1= ZTable.get(position);
        intent.putExtra("nomer",z1.getNomer());
        startActivity(intent);

    }

}
