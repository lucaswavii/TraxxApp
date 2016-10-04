package br.com.traxx.traxxapp.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by lucas.ricarte on 17/08/2016.
 */
public class LoadingTask extends AsyncTask<String, Integer, Integer> {

    private final ProgressBar progressBar;
    private final LoadingTaskFinishedListener finishedListener;
    private Context context;

    public interface LoadingTaskFinishedListener {
        void onTaskFinished(); // Se você quer passar algo de volta para o ouvinte adicionar um parâmetro para este método
    }

    public LoadingTask(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener, Context context) {
        this.progressBar = progressBar;
        this.finishedListener = finishedListener;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.i("Tutorial", "Starting task with url: "+params[0]);
        if(resourcesDontAlreadyExist()){
            downloadResources();
        }

        // Talvez você queira retornar algo para o seu post executar
        return 1234;
    }

    private boolean resourcesDontAlreadyExist() {
        // Aqui você iria consultar o estado interno de seu aplicativo para ver se esta transferência tinha sido realizada antes
        // Talvez uma vez controlado , salvo isso em uma preferência compartilhada para velocidade de acesso da próxima vez
        return true; // Retornando true para que mostram o respingo de cada vez
    }


    private void downloadResources() {
        // We are just imitating some process thats takes a bit of time (loading of resources / downloading)
        CacheLocal cache = new CacheLocal(this.context);

        int count = 10;
        for (int i = 0; i < count; i++) {

            // Update the progress bar after every step
            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);
            try {
                if ( i == 1 ) {
                    cache.BaixaModeloMotos();
                } else if ( i == 2 ){
                    cache.BaixaModeloGrupo();
                }else if ( i == 3 ) {
                    cache.BaixaModeloConjunto();
                } else if ( i == 4){
                    cache.BaixaModeloPecas();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Do some long loading things
            try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }
}
