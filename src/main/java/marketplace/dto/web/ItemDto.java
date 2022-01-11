package marketplace.dto.web;

import lombok.Data;
import marketplace.validation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static marketplace.constants.ApplicationConstants.EMPTY_ERROR;
import static marketplace.exception.ItemErrorMessageConstants.LONG_DESCRIPTION;
import static marketplace.exception.ItemErrorMessageConstants.LONG_TITLE;

@Data
@ExistItemParameters
public class ItemDto {
    @IsLong
    private String itemId;

    @NotBlank(message = EMPTY_ERROR)
    @Size(max = 30, message = LONG_TITLE)
    private String title;

    @NotBlank(message = EMPTY_ERROR)
    @Size(max = 200, message = LONG_DESCRIPTION)
    private String description;

    @IsFloat
    private String startPrice;

    @IsFloat
    private String bidIncrement;

    @Time
    private String stopTime;

    @Date
    private String stopDate;

}
