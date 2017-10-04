package ua.zp.yurarud.e_mobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Created by Админ on 02.10.2017.
 */

public class ZakazActivity extends AppCompatActivity {

    private Realm mRealm;

    static String kod_tov;
    static String naim_tov;

    TextView naimenov;

    @Override
    protected void onResume(){
        super.onResume();
        naimenov= (TextView) findViewById(R.id.tvVybKlioent);
        if(naim_tov!=null){
            naimenov.setText(naim_tov);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_zakaz);

        ImageButton btnClients = (ImageButton) findViewById(R.id.btnVybKlioent);
        btnClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                intent.putExtra("zakaz",1);
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