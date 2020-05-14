package com.zone24x7.rac.configservice.bundle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BundleServiceTest {

    @Mock
    private BundleRepository bundleRepository;

    @InjectMocks
    private BundleService bundleService;

    @Test
    @DisplayName("get all bundles method")
    void getAllBundles() {
        List<Bundle> expectedBundles = new ArrayList<>();
        Bundle bundle = new Bundle();

        expectedBundles.add(bundle);
        when(bundleRepository.findAll()).thenReturn(expectedBundles);

        BundleSummaryListDTO actualBundles = bundleService.getAllBundles();

        assertEquals(expectedBundles.size(), actualBundles.getBundles().size());
    }
}
