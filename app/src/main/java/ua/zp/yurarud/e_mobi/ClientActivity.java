package ua.zp.yurarud.e_mobi;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;


import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.GroupProducs;


public class ClientActivity extends AppCompatActivity {

    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    List<String> expListTitle;
    HashMap<String, List<String>> expDetails;
    List<String> currentProduct;

    public Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_client);
        expListView = (ExpandableListView) findViewById(R.id.elvMain);


        mRealm = Realm.getDefaultInstance();

        expDetails = new HashMap<>();

        RealmQuery<GroupProducs> gr0 = mRealm.where(GroupProducs.class).equalTo("level", 0);
        RealmResults<GroupProducs> nab0 = gr0.findAll();
        for (GroupProducs gp0 : nab0) {
            if (gp0.groupProducy.size() > 0) {
                currentProduct = new ArrayList<>();
                RealmResults<GroupProducs> podgruppa = gp0.groupProducy.where().findAll();
                for (GroupProducs gp1 : podgruppa) {
                    if (gp1.producty.size() > 0) {
                        currentProduct.add(gp1.getName());
                    }
                }
                expDetails.put(gp0.getName(), currentProduct);
            }
        }


        expListTitle = new ArrayList<>(expDetails.keySet());
        expListAdapter = new ListAdapter(this, expListTitle, expDetails);

        expListView.setAdapter(expListAdapter);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список раскрыт.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список скрыт.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition)
                                + " : " + expDetails.get(expListTitle.get(groupPosition))
                                .get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }



    @Override

    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }



}
