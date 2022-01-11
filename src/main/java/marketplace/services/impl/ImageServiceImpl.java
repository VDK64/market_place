package marketplace.services.impl;

import marketplace.persistence.ItemDao;
import marketplace.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Implementation of {@link ImageService}.
 */
@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    @Qualifier("ItemDaoHibernateImpl")
    private ItemDao itemDao;

    @Override
    public byte[] getImageById(long id) throws SQLException {
        return itemDao.getItemImageById(id);
    }

}
