package marketplace.controllers;

import marketplace.dto.web.BidDto;
import marketplace.entities.User;
import marketplace.services.BidService;
import marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static marketplace.constants.ApplicationConstants.SHOW_ITEMS_PAGE;

/**
 * This a bid servlet - entry point for requests related to bids.
 */
@Controller
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    /**
     * Handler request for making bid.
     *
     * @param principal which tried to made bid.
     * @param bidDto    is data of user request for bid.
     * @param result    is a storage of errors which could occur due validation.
     * @return name of view to representation.
     * @throws IOException    would be thrown if exception occur.
     * @throws SQLException   would be thrown if exception occur.
     * @throws ParseException would be thrown if exception occur.
     */
    @PostMapping("bid")
    public String makeBid(@Valid BidDto bidDto,
                          BindingResult result,
                          @AuthenticationPrincipal User principal)
            throws IOException, SQLException, ParseException {
        bidService.makeBid(principal, bidDto, result);
        return SHOW_ITEMS_PAGE;
    }

}
