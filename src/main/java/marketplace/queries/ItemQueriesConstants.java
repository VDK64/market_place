package marketplace.queries;

/**
 * SQL queries applying to item table.
 */
public class ItemQueriesConstants {
    public static final String SELECT_FROM_ITEM_BY_ID = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id WHERE i.Id = ?";
    public static final String INSERT_INTO_ITEM = "INSERT INTO Item (Title, Description, Seller, Start_Price, Bid_Inc, Best_Offer, Bidder, Stop_Date, User_Id, Image) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ITEM = "UPDATE Item SET Title = ?, Description = ?, Best_Offer = ?, Bidder = ? WHERE Id = ?";
    public static final String SELECT_FROM_ITEM = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id ORDER BY i.Id DESC";
    public static final String SELECT_FROM_ITEM_BY_USER_ID = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id WHERE u.Email = ? ORDER BY i.Id DESC";
    public static final String SELECT_PROPER_ITEMS = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id WHERE UPPER(i.Title) LIKE UPPER(?) OR UPPER(i.Description) LIKE UPPER(?) ORDER BY i.Id DESC";
    public static final String SELECT_ITEM_IMAGE = "SELECT Image FROM Item WHERE Id = ?";


    public static final String SELECT_ALL_ITEMS = "SELECT i FROM Item i ORDER BY i.id DESC";
    public static final String SELECT_CURRENT_USER_ITEMS = "SELECT i FROM Item i JOIN i.user u on u.email = ?1 ORDER BY i.id DESC";
    public static final String SELECT_PROPER_ITEM = "SELECT i FROM Item i WHERE UPPER(i.title) LIKE UPPER(?1) OR UPPER(i.description) LIKE UPPER(?2) ORDER BY i.id DESC";

}
