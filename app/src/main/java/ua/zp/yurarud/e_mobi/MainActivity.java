package ua.zp.yurarud.e_mobi;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    static Handler h;
    TextView tablo1;
    TextView tablo2;
    TextView tablo3;
    TextView tablo4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ImageButton btnClients = (ImageButton) findViewById(R.id.clientsButton);
        btnClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnTovary = (ImageButton) findViewById(R.id.tovaryButton);
        btnTovary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnZakaz = (ImageButton) findViewById(R.id.orderButton);
        btnZakaz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZakazActivity.class);
                startActivity(intent);
            }
        });



        ImageButton btnZakazSpisok = (ImageButton) findViewById(R.id.ordersButton);
        btnZakazSpisok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakazSpisokActivity.class);
                startActivity(intent);
            }
        });

        tablo1 = (TextView) findViewById(R.id.tvTovary);
        tablo2 = (TextView) findViewById(R.id.tvClienty);
        tablo3 = (TextView) findViewById(R.id.tvCeny);
        tablo4 = (TextView) findViewById(R.id.tvOstatki);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
                switch (msg.what){
                    case 20: {
                        showDialog((View) msg.obj);
                        break;
                    }
                    case 1:
                        tablo1.setText("Обработано товаров: " + (msg.arg1+1)+" из "+msg.arg2);
                        break;
                    case 2:{
                        tablo2.setText("Обработано клиентов: " + (msg.arg1+1)+" из "+msg.arg2);
                        break;}
                    case 3:{
                        tablo3.setText("Обработано цен: " + (msg.arg1+1)+" из "+msg.arg2);
                        break;}
                    case 4:{
                        tablo4.setText("Обработано остатков: " + (msg.arg1+1)+" из "+msg.arg2);
                        break;}
                    case 5:{
                        tablo1.setText("Закачано " + (msg.arg1)+" товаров из "+msg.arg2);
                        break;}
                    case 6:{
                        tablo2.setText("Закачано " + (msg.arg1)+" клиентов из "+msg.arg2);
                        break;}
                    case 7:{
                        tablo3.setText("Закачано " + (msg.arg1)+" цен из "+msg.arg2);
                        break;}
                    case 8:{
                        tablo4.setText("Закачано " + (msg.arg1)+" остатков из "+msg.arg2);
                        break;}
                    case 10:
                        switch (msg.arg1) {
                            case 1:{
                                tablo1.setText("Запрос данных с сервера...");
                                break;}
                            case 2:{
                                tablo2.setText("Запрос данных с сервера...");
                                break;}
                            case 3:{
                                tablo3.setText("Запрос данных с сервера...");
                                break;}
                            case 4:{
                                tablo4.setText("Запрос данных с сервера...");
                                break;}
                        }

                }

            };
        };



        ImageButton btnDownload = (ImageButton) findViewById(R.id.DownloadTovButton);
        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /*Передаём код действия
                    1 - полная загрузка
                    2 - загрузка остатков
                    3 - выгрузка накладных
                    4 - выгрузка одной накладной
                */

                SOAP_Go sg =new SOAP_Go(1,view);
                sg.start();
               /* try {
                    sg.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                //showDialog(view);

            }
        });

        ImageButton btnOstatki = (ImageButton) findViewById(R.id.DoownloadKontrButton);
        btnOstatki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                SOAP_Go sg =new SOAP_Go(2,view);
                sg.start();
                /*try {
                    sg.join();
                    showDialog(view);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onSettingsMenuClick(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), PrefActivity.class);
        startActivity(intent);
    }

    public void showDialog(View v) {

        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
}
