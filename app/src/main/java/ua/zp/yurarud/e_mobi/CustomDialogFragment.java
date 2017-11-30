package ua.zp.yurarud.e_mobi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment{

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Загрузка данных")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Загрузка завершена!")
                .setPositiveButton("OK", null)
                .create();
    }
}
