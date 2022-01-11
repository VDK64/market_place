package marketplace;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration("file:src/main/webapp/WEB-INF/root-context.xml")
@WebAppConfiguration
@Sql({"/schema.sql", "/script.sql"})
public class ApplicationContextStarter {
}
