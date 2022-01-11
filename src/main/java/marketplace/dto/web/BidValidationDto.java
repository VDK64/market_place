package marketplace.dto.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.validation.AmountConstraint;
import marketplace.validation.IsClosed;
import marketplace.validation.IsOwnItem;

@Data
@AllArgsConstructor
@IsOwnItem
@AmountConstraint
public class BidValidationDto {
    private float amount;
    private User user;

    @IsClosed
    private Item item;

}
