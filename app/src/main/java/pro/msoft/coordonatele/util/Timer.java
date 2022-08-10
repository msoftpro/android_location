package pro.msoft.coordonatele.util;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;


public class Timer {
    public static void showLoadingDialog(final Context context, final int time) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Asteptati actualizarea datelor...", "Loading data ...", true);

        new Thread() {
            public void run() {
                try{
                    sleep(time);
                } catch (Exception e) {
                }
                new Thread() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }.start();
            };
        }.start();

    }

    public static boolean loading(int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("EEEEEEEEEEEEEEEEEEEEEE");
            }
        }, time);
        return true;
    }

}
