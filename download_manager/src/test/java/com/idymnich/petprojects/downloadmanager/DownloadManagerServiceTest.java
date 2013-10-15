package com.idymnich.petprojects.downloadmanager;

import com.idymnich.petprojects.downloadmanager.api.Download;
import com.idymnich.petprojects.downloadmanager.api.DownloadManagerServiceApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created in 14.10.13, 23:26
 * Author: Игорь
 */
@RunWith(MockitoJUnitRunner.class)
public class DownloadManagerServiceTest {

    @Spy
    @InjectMocks
    private DownloadManagerServiceApi service = DownloadManagerService.getInstance();
    @Mock
    private Map<String, Download> downloadsPool;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldPutDownloadInPollWhenAddDownloadAction() {
        String uri = "test";
        String directory = "test";
        service.addDownload(uri, directory);
        verify(downloadsPool).put(anyString(), Matchers.<Download>anyObject());
    }

    @Test
    public void shouldReturnDownloadInAddDownloadAction() {
        String uri = "test";
        String directory = "test";
        Download download = service.addDownload(uri, directory);
        assertNotNull(download);
    }
}
