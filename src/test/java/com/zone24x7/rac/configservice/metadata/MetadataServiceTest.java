package com.zone24x7.rac.configservice.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class MetadataServiceTest {


    @Mock
    private MetadataRepository metadataRepository;

    @InjectMocks
    private MetadataService metadataService;


    @Nested
    @DisplayName("get metadata method")
    class GetMetadata {

        @Test
        @DisplayName("test for channels")
        void testGetMetadataForChannels() {

            // Expected
            List<Metadata> metadataList = new ArrayList<>();
            metadataList.add(new Metadata("channels", "Web"));
            metadataList.add(new Metadata("channels", "Mobile"));

            // Setup repository method findAllByType() return value.
            when(metadataRepository.findAllByType(anyString())).thenReturn(metadataList);

            // Actual
            MetadataList actualMetadata = metadataService.getMetadata("channels");

            // Assert
            assertEquals(metadataList.size(), actualMetadata.getMetadata().size());
        }


        @Test
        @DisplayName("test for pages")
        void testGetMetadataForPages() {

            // Expected
            List<Metadata> metadataList = new ArrayList<>();
            metadataList.add(new Metadata("pages", "Home"));
            metadataList.add(new Metadata("pages", "PDP"));

            // Setup repository method findAllByType() return value.
            when(metadataRepository.findAllByType(anyString())).thenReturn(metadataList);

            // Actual
            MetadataList actualMetadata = metadataService.getMetadata("pages");

            // Assert
            assertEquals(metadataList.size(), actualMetadata.getMetadata().size());
        }


        @Test
        @DisplayName("test for placeholders")
        void testGetMetadataForPlaceholders() {

            // Expected
            List<Metadata> metadataList = new ArrayList<>();
            metadataList.add(new Metadata("placeholders", "Horizontal1"));
            metadataList.add(new Metadata("placeholders", "Horizontal2"));

            // Setup repository method findAllByType() return value.
            when(metadataRepository.findAllByType(anyString())).thenReturn(metadataList);

            // Actual
            MetadataList actualMetadata = metadataService.getMetadata("placeholders");

            // Assert
            assertEquals(metadataList.size(), actualMetadata.getMetadata().size());
        }


        @Test
        @DisplayName("test for brands")
        void testGetMetadataForBrands() {

            // Expected
            List<Metadata> metadataList = new ArrayList<>();
            metadataList.add(new Metadata("brands", "Nike"));
            metadataList.add(new Metadata("brands", "Puma"));

            // Setup repository method findAllByType() return value.
            when(metadataRepository.findAllByType(anyString())).thenReturn(metadataList);

            // Actual
            MetadataList actualMetadata = metadataService.getMetadata("brands");

            // Assert
            assertEquals(metadataList.size(), actualMetadata.getMetadata().size());
        }

    }



}
