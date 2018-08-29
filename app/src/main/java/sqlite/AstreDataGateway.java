package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import module.Astre;

public class AstreDataGateway {

    DbHelper dbHelper;


    public AstreDataGateway(Context context){

        dbHelper = new DbHelper(context);
    }

    public List<Astre> getAstres(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor cursorForColumnNames=db.query(AstreContract.Astre.TABLE_NAME,null,null,null,null,null,null);
        String[] projection=cursorForColumnNames.getColumnNames();

        Cursor cursor=db.query(
                AstreContract.Astre.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Astre> listAstres=new ArrayList<>();
        while(cursor.moveToNext()){
            String nom=cursor.getString(cursor.getColumnIndexOrThrow(AstreContract.Astre.COLUMN_NAME_NOM));
            int id =cursor.getInt(cursor.getColumnIndexOrThrow(AstreContract.Astre.COLUMN_NAME_ID));
            int taille=cursor.getInt(cursor.getColumnIndexOrThrow(AstreContract.Astre.COLUMN_NAME_TAILLE));
            boolean status=(cursor.getInt(cursor.getColumnIndexOrThrow(AstreContract.Astre.COLUMN_NAME_STATUS))==1?true:false);
            String url=cursor.getString(cursor.getColumnIndexOrThrow(AstreContract.Astre.COLUMN_NAME_URL));

            Astre a=new Astre(id,nom,taille,status,url);

            listAstres.add(a);
        }
        cursor.close();


        return listAstres;
    }


}
