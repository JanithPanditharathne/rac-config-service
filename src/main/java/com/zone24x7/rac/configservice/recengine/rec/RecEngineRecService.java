package com.zone24x7.rac.configservice.recengine.rec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.rec.RecList;
import com.zone24x7.rac.configservice.rec.RecService;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.RECS;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecEngineRecService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private RecService recService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int REC_CONFIG_ID = 3;

    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineRecService.class);


    /**
     * Get recs config json.
     *
     * @return Recs config json
     */
    public String getRecsConfig() {
        return recEngineRepository.findByConfigType(RECS).getConfigJson();
    }





    /**
     * Update rec config json.
     *
     * @throws ServerException when rec config update failed.
     */
    @Async("recTaskExecutor")
    public void updateRecConfig() throws ServerException {

        // Get all recs.
        RecList allRecs = recService.getAllRecs();

        // Rec list for rec engine.
        List<RecEngineRec> recList = new ArrayList<>();

        // Update rec details for each recs.
        allRecs.getRecs().forEach(r -> {
            RecEngineRecRegularConfig regularConfig = new RecEngineRecRegularConfig(r.getBundle().getId());
            recList.add(new RecEngineRec(r.getId(), r.getName(), "REGULAR", null, regularConfig, null));
        });


        try {

            // Get rec config string.
            RecEngineRecList recEngineRecList = new RecEngineRecList(recList);
            String recConfigString = objectMapper.writeValueAsString(recEngineRecList);

            // Update rec config.
            recEngineRepository.save(new RecEngine(REC_CONFIG_ID, RECS, recConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_REC_CONFIG_UPDATE_FAILED);
        }
    }





    /**
     * Add rec Json.
     *
     * @param recConfig Rec config json
     * @return Response
     */
    public CSResponse addRecConfig(String recConfig) {
        recEngineRepository.save(new RecEngine(REC_CONFIG_ID, RECS, recConfig));
        return new CSResponse(SUCCESS,"CS-0000: Rec config json successfully added");
    }


}
