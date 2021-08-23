package controller.database;

import java.util.logging.Logger;

public class DBManager {
    private static Logger LOGGER = Logger.getLogger(DBManager.class.getSimpleName());
    private static volatile DBManager dbManager;

    public static DBManager getInstance() {
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager();
                }
            }
        }
        return dbManager;
    }


}
