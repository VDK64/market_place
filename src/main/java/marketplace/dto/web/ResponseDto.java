package marketplace.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import marketplace.entities.Item;

import java.util.List;

/**
 * This is a data transfer object class to store and delivery data to frontend.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseDto {
    /**
     * List of tables where table is an object which represents item.
     */
    private List<Item> tables;
    private boolean error;

}
