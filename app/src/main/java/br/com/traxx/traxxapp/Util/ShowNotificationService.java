package br.com.traxx.traxxapp.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.traxx.traxxapp.Activitys.AcompanhamentoOrcamentoActivity;
import br.com.traxx.traxxapp.R;

/**
 * Created by lucas.ricarte on 23/09/2016.
 */

public class ShowNotificationService extends Service {

    public static final String PREFS_NAME = "Preferences";
    private SharedPreferences settings;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            tarefas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tarefas() throws Exception {
        settings = getSharedPreferences(PREFS_NAME, 0);
        Integer cliente = Integer.valueOf(settings.getString("cliente", ""));
        Ion.with(getApplicationContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaOrcamentoPendentesCliente")
                .addQuery("referrer", "app traxx")
                .addQuery("pessoa", String.valueOf(cliente))

                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    return;
                }

                String title = "Cotação de Peças/Motos";
                if ( result != null) {
                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();

                        Integer chave = object.get("chave").getAsInt();
                        String revenda = object.get("revenda").getAsString();
                        String status = object.get("status").getAsString();
                        String recurso = object.get("recurso").getAsString();
                        String quantidade = object.get("quantidade").getAsString();
                        String atendido = object.get("atendido").getAsString();
                        String valor = object.get("valor").getAsString();

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(1000);

                        showNotification(title, revenda, chave, 1);
                    }
                }
            }
        });

    }

    public void showNotification(String title, String content, int id, int tipoNotificacao) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);;

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if (tipoNotificacao == 1) {
            mBuilder.setSmallIcon(R.mipmap.ic_traxx);

            Intent resultIntent = new Intent(this, AcompanhamentoOrcamentoActivity.class);
            //resultIntent.putExtra(Constantes.TAREFA_ID, String.valueOf(id));

            stackBuilder.addParentStack(AcompanhamentoOrcamentoActivity.class);
            stackBuilder.addNextIntent(resultIntent);
        }
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_CANCEL_CURRENT );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
