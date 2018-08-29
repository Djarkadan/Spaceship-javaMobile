package module;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public  class  WebOperations {

    public static String LOCAL_HOST="10.0.2.2";
//    10.0.2.2
//    192.168.2.5
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
