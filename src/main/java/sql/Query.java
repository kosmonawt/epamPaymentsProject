package sql;

public class Query {
    public static final String  USER_EXIST_BY_LOGIN = "select * from user_entity where login = ?";
    public static final String USER_SAVE = "insert into user values(?,?,?,?)";
}
