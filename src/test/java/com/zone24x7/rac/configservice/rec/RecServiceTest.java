package com.zone24x7.rac.configservice.rec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.bundle.Bundle;
import com.zone24x7.rac.configservice.bundle.BundleRepository;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.recslot.RecSlot;
import com.zone24x7.rac.configservice.recslot.RecSlotRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.REC_ADD_SUCCESS;
import static com.zone24x7.rac.configservice.util.Strings.REC_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecServiceTest {

    @InjectMocks
    private RecService recService;

    @Mock
    private RecRepository recRepository;

    @Mock
    private RecSlotRepository recSlotRepository;

    @Mock
    private BundleRepository bundleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RecEngineService recEngineService;

    @Test
    @DisplayName("get all recs method")
    void testGetAllRecs() {

        // Mock
        List<Rec> expectedRec = new ArrayList<>();
        Rec rec = new Rec();
        expectedRec.add(rec);
        when(recRepository.findAllByOrderByIdDesc()).thenReturn(expectedRec);

        RecDetail recDetail = new RecDetail();
        when(modelMapper.map(any(), any())).thenReturn(recDetail);

        Bundle bundle = mock(Bundle.class);
        when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

        // Actual
        RecList actualRecs = recService.getAllRecs();

        // Assert
        assertEquals(expectedRec.size(), actualRecs.getRecs().size());
    }

    @Nested
    @DisplayName("get rec method")
    class GetRec {

        @Test
        @DisplayName("test for invalid rec id")
        void testGetRecForInvalidRecID() {

            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.getRec(100));

            // Expected
            String expected = Strings.REC_ID_INVALID;

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for negative rec id")
        void testGetRecForNegativeRecID() {

            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.getRec(-100));

            // Expected
            String expected = Strings.REC_ID_INVALID;

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for valid rec id")
        void testGetRecForValidRecID() throws Exception {

            // Expected
            RecDetail recDetail = new RecDetail();
            recDetail.setId(1);
            recDetail.setName("Test");
            recDetail.setBundle(new RecBundle());

            ObjectMapper objectMapper = new ObjectMapper();
            String expected = objectMapper.writeValueAsString(recDetail);

            // Mock
            Rec rec = new Rec(1, "Test", 0);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            // Actual
            RecDetail actualRec = recService.getRec(1);
            String actual = objectMapper.writeValueAsString(actualRec);

            // Assert
            assertEquals(expected, actual);


            // Assert rec bundle get/set methods.
            RecBundle recBundle = new RecBundle();
            recBundle.setId(1);
            recBundle.setName("Test");
            assertEquals(1, recBundle.getId());
            assertEquals("Test", recBundle.getName());
        }

        @Test
        @DisplayName("test for not found bundle details")
        void testGetRecForNotFoundBundleDetails() {

            // Mock
            Rec rec = new Rec(1, "Test", 0);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            when(bundleRepository.findById(anyInt())).thenReturn(Optional.empty());

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.getRec(100));

            // Expected
            String expected = Strings.REC_BUNDLE_DETAILS_NOT_FOUND;

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("add rec method")
    class AddRec {

        @Test
        @DisplayName("test for null rec name")
        void testAddRecForNullRecName() {

            Rec rec = new Rec();

            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.addRec(rec));

            // Expected
            String expected = Strings.REC_NAME_CANNOT_BE_NULL;

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for empty rec name")
        void testAddRecForEmptyRecName() {

            // Mock
            Rec rec = new Rec();
            rec.setName("");

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.addRec(rec));

            String actual = validationException.getMessage();

            // Expected
            String expected = Strings.REC_NAME_CANNOT_BE_EMPTY;

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for invalid bundle id")
        void testAddRecForInvalidBundleID() {

            // Mock
            Rec rec = new Rec();
            rec.setName("Test 1");
            rec.setBundleID(1);

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.addRec(rec));

            String actual = validationException.getMessage();

            // Expected
            String expected = Strings.BUNDLE_ID_INVALID;

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testAddRecForCorrectValues() throws ServerException, ValidationException {

            // Mock
            Rec rec = new Rec();
            rec.setName("Test 1");
            rec.setBundleID(1);

            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(1)).thenReturn(Optional.of(bundle));

            // Actual
            CSResponse actual = recService.addRec(rec);

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_ADD_SUCCESS);

            // Assert
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateRecConfig();
        }
    }


    @Nested
    @DisplayName("edit rec method")
    class EditRec {

        @Test
        @DisplayName("test for invalid id")
        void testEditRecForInvalidID() {

            // Expected
            String expected = Strings.REC_ID_INVALID;

            Rec rec = new Rec();
            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.editRec(1, rec));

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for null rec name")
        void testAddRecForNullRecName() {

            // Expected
            String expected = Strings.REC_NAME_CANNOT_BE_NULL;

            // Mock
            Rec rec = new Rec();
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.editRec(1, rec));

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for invalid bundle id")
        void testEditRecForInvalidBundleID() {

            // Mock
            Rec rec = new Rec();
            rec.setName("Test 1");
            rec.setBundleID(1);

            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> recService.editRec(1, rec));

            String actual = validationException.getMessage();

            // Expected
            String expected = Strings.BUNDLE_ID_INVALID;

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testEditRecForCorrectValues() throws ServerException, ValidationException {

            // Mock
            Rec rec = new Rec();
            rec.setName("Test 1");
            rec.setBundleID(1);

            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(1)).thenReturn(Optional.of(bundle));

            // Actual
            CSResponse actual = recService.editRec(1, rec);

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_UPDATED_SUCCESSFULLY);

            // Assert
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateRecConfig();
        }
    }




    @Nested
    @DisplayName("delete rec method")
    class DeleteRec {

        @Test
        @DisplayName("test for invalid rec id")
        void testDeleteRecForInvalidRecID() {

            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (invalid rec id)
                Rec rec = new Rec(99999, "Invalid rec", 0);

                // Setup repository method findById() return value.
                when(recRepository.findById(rec.getId())).thenReturn(Optional.empty());

                // Delete rec.
                recService.deleteRec(rec.getId());
            });

            // Expected
            String expected = Strings.REC_ID_INVALID;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for in use rec id")
        void testDeleteRecForInuseRecID() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                // Mock
                Rec rec = mock(Rec.class);
                when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

                RecSlot recSlot = new RecSlot();
                List<RecSlot> recSlotList = new ArrayList<>();
                recSlotList.add(recSlot);
                when(recSlotRepository.findAllByRecID(anyInt())).thenReturn(recSlotList);

                // Delete rec.
                recService.deleteRec(1);
            });

            // Expected
            String expected = Strings.REC_ID_ALREADY_USE_IN_REC_SLOTS;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for correct rec id")
        void testDeleteRecForCorrectRecID() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(Strings.SUCCESS, Strings.REC_DELETED_SUCCESSFULLY);

            // Mock
            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));
            when(recSlotRepository.findAllByRecID(anyInt())).thenReturn(new ArrayList<>());

            // Call deleteById method.
            recRepository.deleteById(rec.getId());

            // Verify deleteById method is actually called.
            verify(recRepository, times(1)).deleteById(rec.getId());

            // Actual
            CSResponse actual = recService.deleteRec(1);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }
    }
}
