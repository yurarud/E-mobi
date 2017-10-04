package ua.zp.yurarud.e_mobi;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ua.zp.yurarud.e_mobi.model.GroupProducs;


public class ClientActivity extends FragmentActivity {

    private Realm mRealm;

    static int vozvrat=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_client);

        Intent intent = getIntent();
        vozvrat = intent.getIntExtra("zakaz",0);

        if (findViewById(R.id.fragment_containerF) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Cli_fold_fr fragment1 = new Cli_fold_fr();
            //fragment1.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_containerF, fragment1).commit();


        }





    }





}
