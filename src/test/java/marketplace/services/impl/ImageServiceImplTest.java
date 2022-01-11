package marketplace.services.impl;

import marketplace.persistence.ItemDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private ImageServiceImpl test;

    @Test
    public void getImageById() throws SQLException {
        when(itemDao.getItemImageById(anyLong()))
                .thenReturn(new byte[0]);
        test.getImageById(anyLong());
    }
}