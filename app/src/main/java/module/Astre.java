package module;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class Astre {
    private final String rootUrl="http://"+WebOperations.LOCAL_HOST+"/spaceship/image/";
    private int id;
    private String nom;
    private int taille;
    private  boolean status;
    private String url;
    private Drawable image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Astre(int id,String nom, int taille,boolean status,String url){
        this.id=id;
        this.nom=nom;
        this.taille=taille;
        this.status=status;
        this.url=url;
        Drawable image = WebOperations.LoadImageFromWebOperations(rootUrl + url);
        setImage(image);


    }


    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
