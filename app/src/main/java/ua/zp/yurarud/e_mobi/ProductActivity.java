package ua.zp.yurarud.e_mobi;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import io.realm.Realm;


public class ProductActivity extends FragmentActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_client);

        if (findViewById(R.id.fragment_containerF) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Pro_fold_fr fragment1 = new Pro_fold_fr();
            //fragment1.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_containerF, fragment1).commit();


        }





    }





}
