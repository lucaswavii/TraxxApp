package br.com.traxx.traxxapp.Activitys;


import android.app.Activity;

import br.com.traxx.traxxapp.Util.CacheLocal;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.Util.LoadingTask;
import br.com.traxx.traxxapp.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashActivity extends Activity implements LoadingTask.LoadingTaskFinishedListener {

    public static final String PREFS_NAME = "Preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ConexaoNet conexaoNet = new ConexaoNet(this, getBaseContext());
        //conexaoNet.VerificaServidor(true);
        try {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
            new LoadingTask(progressBar, this, getApplicationContext()).execute(ConexaoNet.host);
        }catch (Exception e){
            finish();
        }
    }


    @Override
    public void onTaskFinished() {
        completeSplash();
    }

    private void completeSplash(){
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    private void startApp() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String nome = settings.getString("nome", "");
        String sobreNome = settings.getString("sobrenome", "");
        String fone = settings.getString("fone", "");
        String dataCompra = settings.getString("dataCompra", "");
        String email = settings.getString("email", "");
        String uf = settings.getString("uf","");
        String cidade= settings.getString("cidade","");
        String modelo = settings.getString("modelo","");


        if( nome != "" && sobreNome != "" &&  fone != "" && dataCompra != "" && email != ""){
            //Intent i = new Intent(SplashActivity.this, MainActivity.class);
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }else {
            Intent i = new Intent(SplashActivity.this, RegistroActivity.class);
            startActivity(i);
        }
    }




/*
        CacheLocal cache = new CacheLocal(getBaseContext());

        try {
            cache.BaixaModeloMotos();
        } catch (Exception e) {
            e.printStackTrace();
        }
*//*
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String nome = settings.getString("nome", "");
                String sobreNome = settings.getString("sobrenome", "");
                String fone = settings.getString("fone", "");
                String dataCompra = settings.getString("dataCompra", "");
                String email = settings.getString("email", "");
                String uf = settings.getString("uf","");
                String cidade= settings.getString("cidade","");
                String modelo = settings.getString("modelo","");


                if( nome != "" && sobreNome != "" &&  fone != "" && dataCompra != "" && email != ""){
                    //Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, RegistroActivity.class);
                    startActivity(i);
                }

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }*/
}
