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

public class AlertActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Button btnYes = (Button) findViewById(R.id.btn_alertY);
        btnYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("otvet",1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button btnNo = (Button) findViewById(R.id.btn_alertN);
        btnNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("otvet",2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


}
