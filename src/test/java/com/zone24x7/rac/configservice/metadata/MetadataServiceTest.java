package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
            List<Metadata> metadata = new ArrayList<>();
            metadata.add(new Metadata("brands", "Nike"));
            metadata.add(new Metadata("brands", "Puma"));
            MetadataList metadataList = new MetadataList();
            metadataList.setMetadata(metadata);

            // Setup repository method findAllByType() return value.
            when(metadataRepository.findAllByType(anyString())).thenReturn(metadataList.getMetadata());

            // Actual
            MetadataList actualMetadata = metadataService.getMetadata("brands");

            // Assert
            assertEquals(metadataList.getMetadata().size(), actualMetadata.getMetadata().size());
        }

    }




    @Nested
    @DisplayName("add metadata method")
    class AddMetadata {

        @Test
        @DisplayName("test for null metadata type")
        void testAddMetadataForNullMetadataType() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Add null metadata type.
                metadataService.addMetadata(new Metadata(null, "Test"));
            });

            // Expected
            String expected = Strings.METADATA_TYPE_CANNOT_BE_NULL;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for empty metadata type")
        void testAddMetadataForEmptyMetadataType() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Add null metadata type.
                metadataService.addMetadata(new Metadata("", "Test"));
            });

            // Expected
            String expected = Strings.METADATA_TYPE_CANNOT_BE_EMPTY;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for null metadata name")
        void testAddMetadataForNullMetadataName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Add null metadata name.
                metadataService.addMetadata(new Metadata("Test", null));
            });

            // Expected
            String expected = Strings.METADATA_NAME_CANNOT_BE_NULL;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for empty metadata name")
        void testAddMetadataForEmptyMetadataName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Add empty metadata name.
                metadataService.addMetadata(new Metadata("Test", ""));
            });

            // Expected
            String expected = Strings.METADATA_NAME_CANNOT_BE_EMPTY;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for similar metadata")
        void testAddMetadataForSimilarMetadata() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock
                Metadata metadata = new Metadata("brands", "test");
                List<Metadata> metadataList = new ArrayList<>();
                metadataList.add(metadata);
                when(metadataRepository.findByTypeAndName(anyString(), anyString())).thenReturn(metadataList);

                // Add metadata.
                metadataService.addMetadata(metadata);
            });

            // Expected
            String expected = Strings.SIMILAR_METADATA_ALREADY_EXISTS;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);


        }


        @Test
        @DisplayName("test for correct values")
        void testAddMetadataForCorrectValues() throws ValidationException {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, Strings.METADATA_ADDED_SUCCESSFULLY);

            // Setup repository method findByTypeAndName() return value.
            when(metadataRepository.findByTypeAndName(anyString(), anyString())).thenReturn(null);

            // Mock metadata
            Metadata metadata = new Metadata("brands", "test");
            metadataRepository.save(metadata);

            // Verify metadata save method is called.
            verify(metadataRepository, times(1)).save(metadata);


            // Actual
            CSResponse actual = metadataService.addMetadata(metadata);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());


        }




    }



}
