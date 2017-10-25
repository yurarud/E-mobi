package ua.zp.yurarud.e_mobi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Админ on 10.10.2017.
 */

public class KolvoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolvo);

        Button btnClients = (Button) findViewById(R.id.btn_kolvo);
        btnClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText ed_kolvo = (EditText) findViewById(R.id.et_Kolvo);
                int kolvo= Integer.parseInt(ed_kolvo.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("kolvo",kolvo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


}
