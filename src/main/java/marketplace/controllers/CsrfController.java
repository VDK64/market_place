package marketplace.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class need to give away csrf.
 */
@RestController
public class CsrfController {

    @RequestMapping("csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
