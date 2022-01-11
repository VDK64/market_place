package marketplace.dto.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.entities.Item;
import marketplace.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidPersistenceDto {
    private long id;
    private float amount;
    private String timeStamp;
    private User user;
    private Item item;

}
