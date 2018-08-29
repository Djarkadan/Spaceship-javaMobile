package module;



import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import java.io.IOError;
import java.io.IOException;

/**
 * Created by nnaija on 2018-05-29.
 */

public class DbWorker extends AsyncTask {

    private  IWebDatabaseRequest webQuery;
    private  IActivityUIUpdateWebQuery c;


    @Override
    protected Object doInBackground(Object[] objects){
        Thread.currentThread().setName("WebQuerying");
        try {
            return webQuery.forwardWebDataBaseReq(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();


    }
    @Override
    protected void onPostExecute(Object o){

        super.onPostExecute(o);
        c.updateUI(o);


    }






    public DbWorker(IActivityUIUpdateWebQuery c,IWebDatabaseRequest webQuery){
        this.c=c;
        this.webQuery=webQuery;

    }


}
