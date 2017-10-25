package ua.zp.yurarud.e_mobi;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.zp.yurarud.e_mobi.model.Zakaz;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 04.10.2017.
 */

public class ZakazSpisokAdapter<T> extends ArrayAdapter<T> {

    LayoutInflater mInflater;
    Context mContext;
    int mResource;
    ArrayList<Zakaz> obj;
    private Realm mRealm;

    public ZakazSpisokAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Zakaz> objects) {
        super(context, resource, (List<T>) objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        obj= (ArrayList<Zakaz>) objects;
        mRealm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private @NonNull View createViewFromResource(@NonNull LayoutInflater inflater, int position,
                                                 @Nullable View convertView, @NonNull ViewGroup parent, int resource) {
        final View view;
        final TextView text;



        if (convertView == null) {
            view = inflater.inflate(R.layout.item_spisok_zakazov, parent, false);
        }
        else{
            view = convertView;
        }


        Zakaz p = getProduct(position);
        String nom="Заказ № "+String.valueOf(p.getNomer());

        ((TextView) view.findViewById(R.id.tv_zakaz_name)).setText(nom);
        ((TextView) view.findViewById(R.id.tv_nomer_str_zakaza)).setText(String.valueOf(position));
        ((TextView) view.findViewById(R.id.tv_zakaz_summa)).setText(p.getKlient_name()+", сумма="+String.valueOf(p.getSumma()));
        String otpr="не отправлен";
        if(p.isOtpravlen()){
            otpr="отправлен";
        }
        ((TextView) view.findViewById(R.id.tv_zakaz_otpravlen)).setText(otpr);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.chb_zakaz);
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        cbBuy.setTag(position);
        cbBuy.setChecked(p.isBox());


        return view;
    }

    Zakaz getProduct(int position) {
        return ((Zakaz) getItem(position));
    }


    /*public ArrayList<Zakaz> getBox() {
        ArrayList<Zakaz> box = new ArrayList<Zakaz>();
        for (Zakaz p : obj) {

            if (p.isBox())
                box.add(p);
        }
        return box;
    }*/

    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            mRealm.beginTransaction();
            getProduct((Integer) buttonView.getTag()).setBox(isChecked);
            mRealm.commitTransaction();
        }
    };
}
