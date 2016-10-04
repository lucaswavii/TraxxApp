package br.com.traxx.traxxapp.Activitys;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ShowNotificationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //startService(new Intent(this, ShowNotificationService.class));
       // gerarNotificacao();
    }


    public void btnClickCatalogo(View view) {

        Intent i = new Intent(MainActivity.this, CatalogoActivity.class);
        startActivity(i);
    }

    public void btnClickModelos(View view) {
        Intent i = new Intent(MainActivity.this, ModelosActivity.class);
        startActivity(i);
    }

    public void btnClickCotacoes(View view) {
        Intent i = new Intent(MainActivity.this, AcompanhamentoOrcamentoActivity.class);
        startActivity(i);
    }

    public void btnClickRevendas(View view) {
        Intent i = new Intent(MainActivity.this, MapsRevendasActivity.class);
        startActivity(i);
    }


    public void btnClickFaleConosco(View view) {
        Intent i = new Intent(MainActivity.this, ContatoActivity.class);
        startActivity(i);
    }

    public void gerarNotificacao(){

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, AcompanhamentoOrcamentoActivity.class);
        PendingIntent p = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Ticker Texto");
        builder.setContentTitle("Título");
        //builder.setContentText("Descrição");
        builder.setSmallIcon(R.mipmap.ic_traxx);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_traxx));
        builder.setContentIntent(p);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String [] descs = new String[]{"Descrição 1", "Descrição 2", "Descrição 3", "Descrição 4"};
        for(int i = 0; i < descs.length; i++){
            style.addLine(descs[i]);
        }
        builder.setStyle(style);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_traxx, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();
        }
        catch(Exception e){}
    }


}
