package ua.zp.yurarud.e_mobi;

import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
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
    Message msg;
    SharedPreferences sp;


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
        //srv1.setUrl("http://192.168.1.140/UTP/ws/WebService1/WebService1SoapBinding/");
        srv1.setUrl("http://91.237.7.170/UTP/ws/WebService1/WebService1SoapBinding/");
        //srv1.setUrl("http://192.168.1.101/UTP/ws/WebService1/WebService1SoapBinding/");
        srv1.setTimeOut(1500);
        sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String tp=sp.getString("tp", "");
        String s1="",s2="",s3="",s4="";
        if (act==1)
        {
            Snackbar.make(view, "Загрузка начата!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            msg = MainActivity.h.obtainMessage(10, 1,0);
            MainActivity.h.sendMessage(msg);
            s1=srv1.getTovary(tp);
            ParserJson p1= new ParserJson();
            p1.parsTovary(s1, view);
            s1=null;
            msg = MainActivity.h.obtainMessage(10, 2,0);
            MainActivity.h.sendMessage(msg);
            s2=srv1.getClienty(tp);
            p1.parsClienty(s2,view);
            s2=null;
            msg = MainActivity.h.obtainMessage(10, 3,0);
            MainActivity.h.sendMessage(msg);
            s3=srv1.getCeny(tp);
            p1.parsCeny(s3,view);
            s3=null;
            msg = MainActivity.h.obtainMessage(10, 4,0);
            MainActivity.h.sendMessage(msg);
            s4= srv1.getOstatki(tp);
            p1.parsOstatki(s4,view);
        }
        if(act==2){
            msg = MainActivity.h.obtainMessage(10, 4,0);
            MainActivity.h.sendMessage(msg);
            s4= srv1.getOstatki(tp);
            ParserJson p1= new ParserJson();
            p1.parsOstatki(s4,view);
        }
        if(act==4){
            ParserJson p1= new ParserJson();
            String json=p1.parsZakaz(ZakazActivity.nomer,view,tp);
            ZakazActivity.otpravlen=srv1.setTovary(json);
        }
        if(act==5){
            ParserJson p1= new ParserJson();
            String json=p1.parsZakaz(ZakazSpisokActivity.nomer,view,tp);
            ZakazSpisokActivity.otpravlen=srv1.setTovary(json);
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
