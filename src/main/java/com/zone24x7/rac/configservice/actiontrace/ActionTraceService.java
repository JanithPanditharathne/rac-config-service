package com.zone24x7.rac.configservice.actiontrace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zone24x7.rac.configservice.util.Strings.METHOD;
import static com.zone24x7.rac.configservice.util.Strings.ORIGIN;
import static com.zone24x7.rac.configservice.util.Strings.REQUEST_ID;
import static com.zone24x7.rac.configservice.util.Strings.URI;
import static com.zone24x7.rac.configservice.util.Strings.USER;

@Service
public class ActionTraceService {

    @Autowired
    private ActionTraceRepository actionTraceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionTraceService.class);


    /**
     * Add action trace entry with empty the body.
     */
    public void add() {
        add(null);
    }


    /**
     * Add given data to the action trace table.
     *
     * @param data data need to add.
     */
    public void add(Object data) {
        ActionTrace actionTrace = new ActionTrace();

        // Set date time.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        actionTrace.setDatetime(formatter.format(new Date()));

        // Set uuid.
        actionTrace.setUuid(MDC.get(REQUEST_ID));

        // Set method.
        actionTrace.setMethod(MDC.get(METHOD));

        // Set uri.
        actionTrace.setUri(MDC.get(URI));

        // Set origin
        actionTrace.setOrigin(MDC.get(ORIGIN));

        // Set user.
        actionTrace.setUser(MDC.get(USER));

        // Set body.
        try {
            actionTrace.setBody(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException jpe) {
            LOGGER.info("", jpe);
            actionTrace.setBody("-");
        }

        // Add action trace.
        actionTraceRepository.save(actionTrace);
    }
}
