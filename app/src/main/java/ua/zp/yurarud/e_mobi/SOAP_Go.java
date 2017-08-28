package ua.zp.yurarud.e_mobi;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import ua.zp.yurarud.e_mobi.WebService.IWsdl2CodeEvents;
import ua.zp.yurarud.e_mobi.WebService.WebService1;




/**
 * Created by Админ on 09.08.2017.
 */

public class SOAP_Go extends Thread  implements IWsdl2CodeEvents {
    private int act=1;
    private View view;


    public SOAP_Go(int act,View view){
        this.act=act;
        this.view=view;
    }


    @Override
    public void run(){
        callWebService();

    }


    public void callWebService(){

        WebService1 srv1 = new WebService1();
        srv1.setUrl("http://192.168.1.140/UTP/ws/WebService1/WebService1SoapBinding/");
        //srv1.setUrl("http://91.237.7.170:81/UTP/ws/WebService1/WebService1SoapBinding/");
        srv1.setTimeOut(500);
        String s1="";
        if (act==1)
        {
            Snackbar.make(view, "Загрузка начата!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            s1=srv1.getTovary("тест");
            ParserJson p1= new ParserJson();
            p1.parsTovary(s1, view);
        }

        //tv1=s1;

    }


    @Override
    public void Wsdl2CodeStartedRequest() {
        Log.e("Wsdl2Code", "Wsdl2CodeStartedRequest");

    }
    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        Log.e("Wsdl2Code", "Wsdl2CodeFinished");
        Log.e("Wsdl2Code",methodName);

    }
    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        Log.e("Wsdl2Code", "Wsdl2CodeFinishedWithException");

    }
    @Override
    public void Wsdl2CodeEndedRequest() {
        Log.e("Wsdl2Code", "Wsdl2CodeEndedRequest");
    }




}
