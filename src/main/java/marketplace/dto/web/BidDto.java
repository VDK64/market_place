package marketplace.dto.web;

import lombok.Data;
import marketplace.validation.IsFloat;
import marketplace.validation.IsLong;

import javax.validation.constraints.NotBlank;

@Data
public class BidDto {

    @IsFloat
    @NotBlank
    private String amount;

    @IsLong
    @NotBlank
    private String itemId;

}
