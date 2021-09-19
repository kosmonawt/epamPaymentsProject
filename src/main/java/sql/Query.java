package sql;

public class Query {
    public static final String USER_EXIST_BY_LOGIN = "select * from user_entity where email = ?";
    public static final String USER_CREATE = "insert into user_entity values(default,?,?,?,?,?)";
    public static final String USER_UPDATE = "update user_entity set name = ?, surname = ?, email = ? , password = ?, role =?  where id = ?";
    public static final String USER_DELETE = "delete from user_entity where  id = ?";
    public static final String USER_GET_BY_ID = "select * from user_entity where id= ?";
    public static final String USER_GET_ALL = "select * from user_entity ";
    public static final String ROLE_GET_BY_NAME = "select * from user_role where name = ?";
    public static final String ROLE_GET_BY_ID = "select * from user_role where id = ?";
    public static final String ROLE_GET_ALL = "select * from user_role";
    public static final String CARD_GET_BY_ID = "select * from card where id = ?";
    public static final String CARD_GET_ALL = "select * from card";
    public static final String CARD_GET_BY_CARD_NUMBER = "select * from card where card_number = ?";
    public static final String CARD_CREATE = "insert into card values(default,?,?,?,?,?,?)";
    public static final String CARD_DELETE = "delete from card where id = ?";
    public static final String CARD_UPDATE = "update card set card_number = ?, pin_num = ?, cvv_num = ?, expiry_date = ?, card_type = ? where id = ?";
    public static final String PAYMENT_CREATE = "insert into payment values(default,?,?,?,?,?)";

    public static final String PAYMENT_GET_BY_ACCOUNT_FROM_ID = "select * from payment where payment_from_account_id = ?";
    public static final String PAYMENT_GET_BY_ACCOUNT_TO_ID = "select * from payment where payment_to_account_id = ?";

    public static final String ACCOUNT_GET_ALL_BY_USER_ID = "select * from account where card_holder_id = ?";
    public static final String ACCOUNT_CREATE = "insert into account values (default, ?,?,?,?,?)";
    public static final String VAULT = "insert into vault values (default) returning number";
    public static final String ACCOUNT_GET_ALL_BY_USER_EMAIL = "select * from account where user_login like ? ";
}
