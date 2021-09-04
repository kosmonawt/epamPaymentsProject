import controller.database.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) {

        DBManager dbManager = DBManager.getInstance();

        try (Connection connection = dbManager.getConnection()) {

            System.out.println(connection.getClientInfo());

        }catch (SQLException e){
            System.out.println(e);
        }

    }

}
