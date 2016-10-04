package br.com.traxx.traxxapp.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.app.Activity;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by lucas.ricarte on 12/08/2016.
 */
public class ConexaoNet extends Activity {

    private Activity activity;
    private Context context;
    private Socket socket;
    public static final String host = "192.168.0.105";
    public static final int port = 90;
    public static final int timeout = 500;
    private AlertDialog alerta;

    public ConexaoNet( Activity activity, Context context ){
        this.activity = activity;
        this.context = context;
    }



    public boolean VerificaConexao(){
        boolean lblnRet = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager)
                    this.activity.getSystemService(this.context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
                lblnRet = true;
            } else {
                lblnRet = false;
            }
        }catch (Exception e) {
            Toast.makeText (this.context, e.getMessage(), Toast.LENGTH_SHORT).show ();
        }
        return lblnRet;

    }

    public boolean VerificaServidor(){
        boolean lblnRet = false;
        try
        {

            lblnRet = true;
        }catch (Exception e) {
            return lblnRet;
        }
        return lblnRet;

    }
}
