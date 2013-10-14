package com.idymnich.petprojects.downloadmanager;

import com.idymnich.petprojects.downloadmanager.api.Download;
import com.idymnich.petprojects.downloadmanager.api.DownloadManagerServiceApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created in 14.10.13, 23:26
 * Author: Игорь
 */
@RunWith(MockitoJUnitRunner.class)
public class DownloadManagerServiceTest {

    @Spy
    @InjectMocks
    private DownloadManagerServiceApi service = DownloadManagerService.getInstance();
    private Map<String, Download> pool = new HashMap<String, Download>();

    @Before
    public void setUp() {
        when(service.addDownload(anyString(), anyString())).
                thenReturn(pool.put(anyString(), ((Download) anyObject())));
    }

    @Test
    public void shouldPutDownloadInPollWhenAddDownloadAction() {
        String uri = "test";
        String directory = "test";
        service.addDownload(uri, directory);
        assertTrue(pool.containsKey(uri));
    }

    @Test
    public void shouldReturnDownloadInAddDownloadAction() {
    }
}
