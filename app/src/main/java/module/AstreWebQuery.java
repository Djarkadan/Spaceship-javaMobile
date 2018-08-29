package module;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nnaija on 2018-05-31.
 */

public class AstreWebQuery implements IWebDatabaseRequest {
    private String urlString = "http://"+WebOperations.LOCAL_HOST+"/spaceship/ASTRES.php";


    private List<Astre> listAstres;
    private final String scriptlineBreaker="<br>";


    @Override
    public List<Astre> forwardWebDataBaseReq(Object[] objects) throws IOException {
        BufferedReader br = null;
        OutputStream out = null;
        BufferedWriter bw = null;
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setConnectTimeout(5000);

            in = httpConn.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));

            String line;
            StringBuffer sb = new StringBuffer();


            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");

            }

            listAstres= getAstres(sb);


            return listAstres;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            return null;
        }finally {
            if (out != null) {
                out.close();
            }
            if (br != null) {
                br.close();
            }
            if (bw != null) {
                bw.close();
            }
            if (in != null) {

                in.close();
            }
        }
        return null;
    }

    private List<Astre> getAstres(StringBuffer sb) throws ParseException, IOException {



        List<Astre> list =new ArrayList<>();

        int cnt=0;
        int newLineIndex;


        while((newLineIndex=sb.indexOf(scriptlineBreaker,cnt))!=-1){
            String line=sb.substring(cnt,newLineIndex);
            String[] info = line.split(";");

            int id = Integer.parseInt(info[0]);
            String nom = info[1];
            int taille=Integer.parseInt(info[2]);
            boolean status = (Integer.parseInt(info[3])!=0);
            String url =info[4];



            Astre astre = new Astre(id, nom, taille, status, url);

            list.add(astre);
            cnt=newLineIndex+scriptlineBreaker.length();
        }


        return list;

    }





}

