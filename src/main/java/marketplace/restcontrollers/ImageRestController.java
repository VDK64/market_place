package marketplace.restcontrollers;

import marketplace.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Rest controller to handling image requests.
 */
@RestController
public class ImageRestController {

    @Autowired
    private ImageService imageService;

    @GetMapping("webapi/image/item/{id}")
    public byte[] getImageAsByteArray(@PathVariable String id)
            throws IOException, SQLException {
        final byte[] imageById = imageService.getImageById(Long.parseLong(id));
        if (imageById != null) {
            return imageById;
        }
        InputStream input = ImageRestController.class
                .getResourceAsStream("/static/logos/defaultImage.png");
        byte[] targetArray = new byte[input.available()];
        input.read(targetArray);
        return targetArray;
    }

}
