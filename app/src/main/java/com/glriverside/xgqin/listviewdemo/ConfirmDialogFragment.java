package com.glriverside.xgqin.listviewdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ConfirmDialogFragment extends DialogFragment {

    private Integer newsId = -1;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public interface ConfirmDialogListener {
        void onDialogPositiveClick(ConfirmDialogFragment dialog);
        void onDialogNegativeClick(ConfirmDialogFragment dialog);
    }

    ConfirmDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage("Do you really want to delete this news?")
                .setTitle("Delete news")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogPositiveClick(ConfirmDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogNegativeClick(ConfirmDialogFragment.this);
                        }
                    }
                });

        return dialogBuilder.create();
    }

    public void setDialogClickListener(ConfirmDialogListener listener) {
        this.listener = listener;
    }
}
