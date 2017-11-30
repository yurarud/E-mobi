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

import java.util.ArrayList;

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
        //srv1.setUrl("http://91.237.7.170/UTP/ws/WebService1/WebService1SoapBinding/");
        //srv1.setUrl("http://192.168.1.101/UTP/ws/WebService1/WebService1SoapBinding/");
        srv1.setUrl("http://1c.tm-etalon.com.ua/UTP/ws/WebService1/WebService1SoapBinding/");
        srv1.setTimeOut(1500);
        sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String tp=sp.getString("tp", "");
        String s1="",s2="",s3="",s4="";
        ArrayList<String> arStr = new ArrayList<>();
        int nach,kon;
        if (act==1)
        {
            //Загрузка товаров
            Snackbar.make(view, "Загрузка начата!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            msg = MainActivity.h.obtainMessage(10, 1,0);
            MainActivity.h.sendMessage(msg);
            ParserJson p1= new ParserJson();
            int pakKolvo=5000;
            //String dd=srv1.testObmen(tp);
            String pakS=String.valueOf(pakKolvo);
            String paketovS=srv1.getTovaryK(tp,pakS);
            String [] stM;
            if(paketovS==""){
                paketovS="0_0";
            }
            stM=paketovS.split("_");
            int paketov=Integer.parseInt(stM[0]);
            //int paketov=5;
            int okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(5, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getTovary(tp,nach,kon));
            }
            p1.clearTovary();
            int sm=0;
            for(String st1:arStr) {
                p1.parsTovary(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            s1=null;
            arStr.clear();
            //arStr=null;
            p1.sortTovary();

            //Загрузка клиентов
            msg = MainActivity.h.obtainMessage(10, 2,0);
            MainActivity.h.sendMessage(msg);
            paketovS=srv1.getClientyK(tp,pakS);
            stM=null;
            if(paketovS==""){
                paketovS="0_0";
            }
            stM =paketovS.split("_");
            paketov=Integer.parseInt(stM[0]);
            okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(6, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getClienty(tp,nach,kon));
            }
            p1.clearClienty();
            sm=0;
            for(String st1:arStr) {
                p1.parsClienty(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            arStr.clear();
            //arStr=null;
            p1.sortClienty(view);

            //Загрузка цен
            msg = MainActivity.h.obtainMessage(10, 3,0);
            MainActivity.h.sendMessage(msg);
            paketovS=srv1.getCenyK(tp,pakS);
            stM=null;
            if(paketovS==""){
                paketovS="0_0";
            }
            stM =paketovS.split("_");
            paketov=Integer.parseInt(stM[0]);
            okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(7, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getCeny(tp,nach,kon));
            }
            sm=0;
            for(String st1:arStr) {
                p1.parsCeny(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            arStr.clear();

            //Загрузка остатков
            msg = MainActivity.h.obtainMessage(10, 4,0);
            MainActivity.h.sendMessage(msg);
            paketovS=srv1.getOstatkiK(tp,pakS);
            stM=null;
            if(paketovS==""){
                paketovS="0_0";
            }
            stM =paketovS.split("_");
            paketov=Integer.parseInt(stM[0]);
            okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(8, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getOstatki(tp,nach,kon));
            }
            sm=0;
            for(String st1:arStr) {
                p1.parsOstatki(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            arStr.clear();
            msg = MainActivity.h.obtainMessage(20, 1,0, view);
            MainActivity.h.sendMessage(msg);

        }
        if(act==2){
            //Загрузка остатков
            msg = MainActivity.h.obtainMessage(10, 4,0);
            MainActivity.h.sendMessage(msg);
            ParserJson p1= new ParserJson();
            int pakKolvo=5000;
            String pakS=String.valueOf(pakKolvo);
            String paketovS = srv1.getOstatkiK(tp, pakS);
            if(paketovS==""){
                paketovS="0_0";
            }
            String [] stM =paketovS.split("_");
            int paketov=Integer.parseInt(stM[0]);
            int okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(8, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getOstatki(tp,nach,kon));
            }
            int sm=0;
            for(String st1:arStr) {
                p1.parsOstatki(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            arStr.clear();

            //Загрузка цен
            msg = MainActivity.h.obtainMessage(10, 3,0);
            MainActivity.h.sendMessage(msg);
            paketovS=srv1.getCenyK(tp,pakS);
            stM=null;
            if(paketovS==""){
                paketovS="0_0";
            }
            stM =paketovS.split("_");
            paketov=Integer.parseInt(stM[0]);
            okon=Integer.parseInt(stM[1]);
            for(int r=0;r<paketov;r++){
                nach=r*pakKolvo;
                kon=(r+1)*pakKolvo-1;
                int zakach=r*pakKolvo;
                msg = MainActivity.h.obtainMessage(7, zakach,okon);
                MainActivity.h.sendMessage(msg);
                arStr.add(srv1.getCeny(tp,nach,kon));
            }
            sm=0;
            for(String st1:arStr) {
                p1.parsCeny(st1, view,sm,stM[1]);
                sm+=pakKolvo;
            }
            arStr.clear();

            msg = MainActivity.h.obtainMessage(20, 1,0, view);
            MainActivity.h.sendMessage(msg);
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
