package ua.zp.yurarud.e_mobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import io.realm.Realm;

/**
 * Created by Админ on 02.10.2017.
 */

public class ZakazActivity extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_zakaz);

        ImageButton btnClients = (ImageButton) findViewById(R.id.btnVybKlioent);
        btnClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
            }
        });

        Button btnProdicts = (Button) findViewById(R.id.btnPodbor);
        btnProdicts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(intent);
            }
        });


    }
}