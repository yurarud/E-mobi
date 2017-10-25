package ua.zp.yurarud.e_mobi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import io.realm.Realm;


public class ProductActivity extends FragmentActivity {

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

            Pro_fold_fr fragment1 = new Pro_fold_fr();
            //fragment1.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_containerF, fragment1).commit();


        }





    }





}
