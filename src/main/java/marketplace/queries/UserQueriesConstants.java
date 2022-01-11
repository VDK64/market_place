package marketplace.queries;

/**
 * SQL queries applying to user table.
 */
public class UserQueriesConstants {
    public static final String SELECT_FROM_USER = "SELECT u.Id, u.Firstname, u.Lastname, u.Email, u.Password, ru.Name as \"Role\" FROM Usr u JOIN Role_User ru ON u.Id = ru.User_Id";
    public static final String INSERT_USER = "INSERT INTO Usr (Firstname, Lastname, Email, Password, Gender) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_ROLE_USER = "INSERT INTO Role_User (User_Id, Name) VALUES ((SELECT u.Id FROM Usr u WHERE u.Email = ?), ?)";
    public static final String SELECT_FROM_USER_EMAIL = "SELECT u.Id, u.Firstname, u.Lastname, u.Email, u.Gender, u.Password, ru.Name as \"Role\" FROM Usr u JOIN Role_User ru ON u.Id = ru.User_Id WHERE u.Email = ?";
    public static final String COUNT_USER_BY_EMAIL = "SELECT COUNT(Id) FROM Usr WHERE Email = ?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT u from User u WHERE u.email = ?1";

}
