package marketplace.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPersistenceDto {
    private long id;
    private String title;
    private String description;
    private String seller;
    private float startPrice;
    private float bidInc;
    private float bestOffer;
    private String bidder;
    private String stopDate;
    private User user;
    private byte[] image;

}
