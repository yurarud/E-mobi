package ua.zp.yurarud.e_mobi;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v4.widget.TextViewCompat;

import java.util.List;

import ua.zp.yurarud.e_mobi.model.Products;
import ua.zp.yurarud.e_mobi.model.ZakazTable;

/**
 * Created by Админ on 04.10.2017.
 */

public class ZakazAdapter<T> extends ArrayAdapter<T> {

    LayoutInflater mInflater;
    Context mContext;
    int mResource;

    public ZakazAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ZakazTable> objects) {
        super(context, resource, (List<T>) objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.item_zakaz, parent, false);
        }
        else{
            view = convertView;
        }


        ZakazTable p = getProduct(position);

        double cena=0.0;
        int kolvo=0;
        double summa=0.0;
        int nom=position+1;

        cena=p.getCena();
        kolvo=p.getOstatok();
        summa=cena*kolvo;
        summa=Math.round(summa*100.0)/100.0;
        ((TextView) view.findViewById(R.id.tv_i_nomer)).setText(String.valueOf(nom));
        ((TextView) view.findViewById(R.id.tv_i_cena)).setText(String.valueOf(cena));
        ((TextView) view.findViewById(R.id.tv_i_naimenovanie)).setText(p.getTovar_name());
        ((TextView) view.findViewById(R.id.tv_i_kolvo)).setText(String.valueOf(kolvo));
        ((TextView) view.findViewById(R.id.tv_i_summa)).setText(String.valueOf(summa));

        return view;
    }

    ZakazTable getProduct(int position) {
        return ((ZakazTable) getItem(position));
    }

}
