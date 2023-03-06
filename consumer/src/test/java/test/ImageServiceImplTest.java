package test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.br.mm.model.ImageInfo;
import com.br.mm.repository.ImageRepository;
import com.br.mm.service.impl.ImageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageServiceImpl;

    @Test
    public void testSave() {
        ImageInfo imageInfo = new ImageInfo();
        when(imageRepository.insert(imageInfo)).thenReturn(imageInfo);
        imageServiceImpl.save(imageInfo);
        verify(imageRepository).insert(imageInfo);
        assertNotNull(imageInfo);
    }
}
