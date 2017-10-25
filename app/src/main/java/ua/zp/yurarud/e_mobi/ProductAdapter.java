package ua.zp.yurarud.e_mobi;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import ua.zp.yurarud.e_mobi.model.Products;

/**
 * Created by Админ on 04.10.2017.
 */

public class ProductAdapter<T> extends ArrayAdapter<T> {

    LayoutInflater mInflater;
    Context mContext;
    int mResource;
    int mTipCeny;



    public ProductAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Products> objects, int tipCeny) {
        super(context, resource, (List<T>) objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTipCeny=tipCeny;
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
            view = inflater.inflate(R.layout.item_clients, parent, false);
        }
        else{
            view = convertView;
        }


        Products p = getProduct(position);

        double cena=0;


        ((TextView) view.findViewById(R.id.tv_list_tovar)).setText(p.getName());
        ((TextView) view.findViewById(R.id.tv_list_nalich)).setText("В наличии: "+String.valueOf(p.getOstatok()));
        switch (mTipCeny){
            case 1: {
                cena = p.getCenaGrn();
                break;
            }
            case 2: {
                cena = p.getCenaNDS();
                break;
            }
            case 3: {
                cena = p.getCenaFOP();
                break;
            }
        }
        ((TextView) view.findViewById(R.id.tv_list_cena)).setText("Цена= "+String.valueOf(cena));

        return view;
    }

    Products getProduct(int position) {
        return ((Products) getItem(position));
    }

}
