package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;

    public DbHelper(Context context){
        super(context, AstreContract.DB_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AstreContract.SQL_CREATE_TABLE_CLIENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AstreContract.SQL_DROP_TABLE_CLIENT);
        onCreate(db);
    }
}
