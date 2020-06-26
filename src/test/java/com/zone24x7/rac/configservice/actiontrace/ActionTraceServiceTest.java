package com.zone24x7.rac.configservice.actiontrace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zone24x7.rac.configservice.util.Strings.METHOD;
import static com.zone24x7.rac.configservice.util.Strings.ORIGIN;
import static com.zone24x7.rac.configservice.util.Strings.REQUEST_ID;
import static com.zone24x7.rac.configservice.util.Strings.URI;
import static com.zone24x7.rac.configservice.util.Strings.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ActionTraceServiceTest {

    @Mock
    private ActionTraceRepository actionTraceRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ActionTraceService actionTraceService;


    @Nested
    @DisplayName("add method")
    class Add {

        @Test
        @DisplayName("test for valid data")
        void testAddMethodForValidData() throws JsonProcessingException {

            // Add mdc values.
            MDC.put(REQUEST_ID, "123");
            MDC.put(METHOD, "GET");
            MDC.put(URI, "/v1/algorithms");
            MDC.put(ORIGIN, "0:0:0:0:0:0:0:1");
            MDC.put(USER, "test");
            when(objectMapper.writeValueAsString(any())).thenReturn("{}");

            // Mock
            ActionTrace actionTrace = new ActionTrace();
            actionTrace.setId(1);
            actionTrace.setDatetime("2020-06-26 16:12:29");
            actionTrace.setUuid(MDC.get(REQUEST_ID));
            actionTrace.setMethod(MDC.get(METHOD));
            actionTrace.setUri(MDC.get(URI));
            actionTrace.setOrigin(MDC.get(ORIGIN));
            actionTrace.setUser(MDC.get(USER));
            actionTrace.setBody("{}");
            actionTraceRepository.save(actionTrace);

            // Verify action trace repository save method is called.
            verify(actionTraceRepository, times(1)).save(actionTrace);

            // Add
            actionTraceService.add(any());

            // Assert action trace.
            assertEquals(1, actionTrace.getId());
            assertEquals("2020-06-26 16:12:29", actionTrace.getDatetime());
            assertEquals("GET", actionTrace.getMethod());
            assertEquals("123", actionTrace.getUuid());
            assertEquals("/v1/algorithms", actionTrace.getUri());
            assertEquals("0:0:0:0:0:0:0:1", actionTrace.getOrigin());
            assertEquals("test", actionTrace.getUser());
            assertEquals("{}", actionTrace.getBody());

            // Remove mdc values.
            MDC.remove(REQUEST_ID);
            MDC.remove(METHOD);
            MDC.remove(URI);
            MDC.remove(ORIGIN);
            MDC.remove(USER);

        }


        @Test
        @DisplayName("test for empty body")
        void testAddMethodForEmptyBody() throws JsonProcessingException {

            // Add mdc values.
            MDC.put(REQUEST_ID, "123");
            MDC.put(METHOD, "GET");
            MDC.put(URI, "/v1/algorithms");
            MDC.put(ORIGIN, "0:0:0:0:0:0:0:1");
            MDC.put(USER, "test");
            when(objectMapper.writeValueAsString(any())).thenReturn(null);

            // Mock
            ActionTrace actionTrace = new ActionTrace();
            actionTrace.setId(1);
            actionTrace.setDatetime("2020-06-26 16:12:29");
            actionTrace.setUuid(MDC.get(REQUEST_ID));
            actionTrace.setMethod(MDC.get(METHOD));
            actionTrace.setUri(MDC.get(URI));
            actionTrace.setOrigin(MDC.get(ORIGIN));
            actionTrace.setUser(MDC.get(USER));
            actionTraceRepository.save(actionTrace);

            // Verify action trace repository save method is called.
            verify(actionTraceRepository, times(1)).save(actionTrace);

            // Add
            actionTraceService.add();

            // Assert action trace.
            assertEquals(1, actionTrace.getId());
            assertEquals("2020-06-26 16:12:29", actionTrace.getDatetime());
            assertEquals("GET", actionTrace.getMethod());
            assertEquals("123", actionTrace.getUuid());
            assertEquals("/v1/algorithms", actionTrace.getUri());
            assertEquals("0:0:0:0:0:0:0:1", actionTrace.getOrigin());
            assertEquals("test", actionTrace.getUser());

            // Remove mdc values.
            MDC.remove(REQUEST_ID);
            MDC.remove(METHOD);
            MDC.remove(URI);
            MDC.remove(ORIGIN);
            MDC.remove(USER);

        }


    }

}