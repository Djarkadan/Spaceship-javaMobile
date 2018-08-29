package sqlite;

import android.provider.BaseColumns;

public final class AstreContract {

    private AstreContract(){}

    public  static final String DB_NAME="Spaceship.db";

    public static class  Astre{
        public static final String TABLE_NAME="astre";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_NOM="nom";
        public static final String COLUMN_NAME_TAILLE="taille";
        public static final String COLUMN_NAME_STATUS="status";
        public static final String COLUMN_NAME_URL="url";

    }

    public static final String SQL_CREATE_TABLE_CLIENT=
            "CREATE TABLE "+ Astre.TABLE_NAME+" ("+
                    Astre.COLUMN_NAME_ID+" INTEGER PRIMARY KEY,"+
                    Astre.COLUMN_NAME_NOM+" TEXT,"+
                    Astre.COLUMN_NAME_TAILLE+" INTEGER,"+
                    Astre.COLUMN_NAME_STATUS+" INTEGER,"+
                    Astre.COLUMN_NAME_URL+" TEXT)";

    public  static final String SQL_DROP_TABLE_CLIENT=
            "DROP TABLE IF EXISTS "+ Astre.TABLE_NAME;

}
