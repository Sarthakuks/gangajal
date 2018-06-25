package gangajal.app.project.uks.gangajal.Utils;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;

import gangajal.app.project.uks.gangajal.R;


public class DialogUtil {
    private static AlertDialog alertDialog;

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.MyGravity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        return dialog;
    }

    public static void hideProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    //    public static void showAlertWithoutButton(Context context, final Runnable handler){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setTitle(context.getString(R.string.alert));
//        alertDialogBuilder.setMessage(R.string.network_not_available).setCancelable(true);
//        alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//
//    }
    public static void showAlertWithAlertMessage(Context context, String message,
                                                 final Runnable handler) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (handler != null) {
                    handler.run();
                }
            }
        });
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
            }
        });
        dialog.show();
    }

    public static void showAlertWithSingleButtonMessage(Context context, String message,
                                                        final Runnable handler) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (handler != null) {
                    handler.run();
                }
            }
        });
        dialog.show();
    }
}

