package com.zone24x7.rac.configservice.recengine.recslot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.recslot.RecSlotList;
import com.zone24x7.rac.configservice.recslot.RecSlotService;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.REC_SLOTS;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecEngineRecSlotService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private RecSlotService recSlotService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int REC_SLOT_CONFIG_ID = 4;


    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineRecSlotService.class);


    /**
     * Get rec slots config json.
     *
     * @return Rec slots config json
     */
    public String getRecSlotsConfig() {
        return recEngineRepository.findByConfigType(REC_SLOTS).getConfigJson();
    }





    /**
     * Update rec slot config json.
     *
     * @throws ServerException when rec slot config update failed.
     */
    @Async("recSlotTaskExecutor")
    public void updateRecSlotConfig() throws ServerException {

        // Get all rec slots.
        RecSlotList allRecSlots = recSlotService.getAllRecSlots(false);

        // Rec slot list for rec engine.
        List<RecEngineRecSlot> recSlotList = new ArrayList<>();

        // Update rec slot details for each rec slot.
        allRecSlots.getRecSlots().forEach(rs -> {

            // Rule ids.
            List<Integer> ruleIds = new ArrayList<>();
            rs.getRules().forEach(rule -> ruleIds.add(rule.getId()));

            // Rec ids.
            List<Integer> recIds = new ArrayList<>();
            recIds.add(rs.getRec().getId());

            // Add rec_engine rec_slot to list.
            recSlotList.add(new RecEngineRecSlot(rs.getChannel().getName(), rs.getPage().getName(), rs.getPlaceholder().getName(), ruleIds, recIds));
        });


        try {

            // Get rec slot config string.
            RecEngineRecSlotList recEngineRecSlotList = new RecEngineRecSlotList(recSlotList);
            String recSlotConfigString = objectMapper.writeValueAsString(recEngineRecSlotList);

            // Update rec slot config.
            recEngineRepository.save(new RecEngine(REC_SLOT_CONFIG_ID, REC_SLOTS, recSlotConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_REC_SLOT_CONFIG_UPDATE_FAILED);
        }
    }





    /**
     * Add rec slot config json.
     *
     * @param recSlotConfig Rec slot config json
     * @return  Response
     */
    public CSResponse addRecSlotConfig(String recSlotConfig) {
        recEngineRepository.save(new RecEngine(REC_SLOT_CONFIG_ID, REC_SLOTS, recSlotConfig));
        return new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");
    }



}
