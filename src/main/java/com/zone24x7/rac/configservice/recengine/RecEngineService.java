package com.zone24x7.rac.configservice.recengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.bundle.BundleDetail;
import com.zone24x7.rac.configservice.bundle.BundleList;
import com.zone24x7.rac.configservice.bundle.BundleService;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.rec.RecList;
import com.zone24x7.rac.configservice.rec.RecService;
import com.zone24x7.rac.configservice.recengine.algorithm.RecEngineAlgorithm;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundle;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithm;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithmCombineInfo;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleList;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRec;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecList;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecRegularConfig;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlot;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlotList;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRule;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRuleList;
import com.zone24x7.rac.configservice.recslot.RecSlotList;
import com.zone24x7.rac.configservice.recslot.RecSlotService;
import com.zone24x7.rac.configservice.rule.RuleList;
import com.zone24x7.rac.configservice.rule.RuleService;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.*;

/**
 * Service class relating to Rec Engine.
 *
 */
@Service
public class RecEngineService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private BundleService bundleService;

    @Autowired
    private RecService recService;

    @Autowired
    private RecSlotService recSlotService;

    @Autowired
    private RuleService ruleService;

    private static final int BUNDLE_CONFIG_ID = 1;
    private static final int RULE_CONFIG_ID = 2;
    private static final int REC_CONFIG_ID = 3;
    private static final int REC_SLOT_CONFIG_ID = 4;


    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineService.class);



    /**
     * Get bundle config json.
     *
     * @return Bundle config json
     */
    public String getBundleConfig() {
        return recEngineRepository.findByConfigType(BUNDLES).getConfigJson();
    }

    /**
     * Get rule config json.
     *
     * @return Rule config json
     */
    public String getRuleConfig() {
        return recEngineRepository.findByConfigType(RULES).getConfigJson();
    }

    /**
     * Get recs config json.
     *
     * @return Recs config json
     */
    public String getRecsConfig() {
        return recEngineRepository.findByConfigType(RECS).getConfigJson();
    }

    /**
     * Get rec slots config json.
     *
     * @return Rec slots config json
     */
    public String getRecSlotsConfig() {
        return recEngineRepository.findByConfigType(REC_SLOTS).getConfigJson();
    }


    /**
     * Update bundle config json.
     *
     * @throws ServerException when bundle config update failed.
     */
    @Async("bundleTaskExecutor")
    public void updateBundleConfig() throws ServerException {

        // Get all bundles.
        BundleList allBundles = bundleService.getAllBundles();

        // Bundle list for rec engine.
        List<RecEngineBundle> bundleList = new ArrayList<>();

        // Update bundle details for each bundle.
        allBundles.getBundles().forEach(b -> {

            try {

                // Get bundle details.
                BundleDetail bundleDetail = bundleService.getBundle(b.getId());

                // Algorithm combine info.
                RecEngineBundleAlgorithmCombineInfo algorithmCombineInfo = new RecEngineBundleAlgorithmCombineInfo();
                algorithmCombineInfo.setEnableCombine(bundleDetail.isCombineEnabled());
                algorithmCombineInfo.setCombineDisplayText(bundleDetail.getCombineDisplayText());

                // Bundle algorithm list.
                List<RecEngineBundleAlgorithm> algorithms = new ArrayList<>();

                // Add details for each algorithm.
                bundleDetail.getAlgorithms().forEach(a ->
                    algorithms.add(new RecEngineBundleAlgorithm(
                            a.getRank(),
                            new RecEngineAlgorithm(a.getId(), a.getName(), "FLAT_ALGO", a.getDefaultDisplayText(), a.getCustomDisplayText())
                    ))
                );

                // Add rec engine bundle config to the list.
                bundleList.add(new RecEngineBundle(b.getId(), b.getName(), "FLAT", bundleDetail.getDefaultLimit(), algorithms, algorithmCombineInfo));

            } catch (ValidationException e) {
                LOGGER.info(e.getMessage(), e);
            }
        });

        try {

            // Get bundle config string.
            RecEngineBundleList recEngineBundleList = new RecEngineBundleList(bundleList);
            ObjectMapper objectMapper = new ObjectMapper();
            String bundleConfigString = objectMapper.writeValueAsString(recEngineBundleList);

            // Update bundle config.
            recEngineRepository.save(new RecEngine(BUNDLE_CONFIG_ID, BUNDLES, bundleConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_BUNDLE_CONFIG_UPDATE_FAILED);
        }
    }

    /**
     * Update rule config json.
     *
     * @throws ServerException when rule config update failed.
     */
    @Async("ruleTaskExecutor")
    public void updateRuleConfig() throws ServerException {

        // Get all bundles.
        RuleList allRules = ruleService.getAllRules();

        // rule list
        List<RecEngineRule> ruleList = new ArrayList<>();

        // Update rule details for each rule.
        allRules.getRules().forEach(rule ->
            ruleList.add(new RecEngineRule(rule.getId(), rule.getName(), rule.getType(), rule.getIsGlobal(),
                                           rule.getMatchingCondition(), rule.getActionCondition())));

        try {

            // Get rule config string.
            RecEngineRuleList recEngineRuleList = new RecEngineRuleList(ruleList);
            ObjectMapper objectMapper = new ObjectMapper();
            String ruleConfigString = objectMapper.writeValueAsString(recEngineRuleList);

            // Update rule config.
            recEngineRepository.save(new RecEngine(RULE_CONFIG_ID, RULES, ruleConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_RULE_CONFIG_UPDATE_FAILED);
        }
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
            ObjectMapper objectMapper = new ObjectMapper();
            String recConfigString = objectMapper.writeValueAsString(recEngineRecList);

            // Update rec config.
            recEngineRepository.save(new RecEngine(REC_CONFIG_ID, RECS, recConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_REC_CONFIG_UPDATE_FAILED);
        }
    }










    /**
     * Update rec slot config json.
     *
     * @throws ServerException when rec slot config update failed.
     */
    @Async("recSlotTaskExecutor")
    public void updateRecSlotConfig() throws ServerException {

        // Get all rec slots.
        RecSlotList allRecSlots = recSlotService.getAllRecSlots();

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
            ObjectMapper objectMapper = new ObjectMapper();
            String recSlotConfigString = objectMapper.writeValueAsString(recEngineRecSlotList);

            // Update rec slot config.
            recEngineRepository.save(new RecEngine(REC_SLOT_CONFIG_ID, REC_SLOTS, recSlotConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_REC_SLOT_CONFIG_UPDATE_FAILED);
        }
    }








    /**
     * Add bundle config json.
     *
     * @param bundleConfig Bundle Json
     * @return Response
     */
    public CSResponse addBundleConfig(String bundleConfig) {
        recEngineRepository.save(new RecEngine(BUNDLE_CONFIG_ID, BUNDLES, bundleConfig));
        return new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");
    }

    /**
     * Add rule config json.
     *
     * @param ruleConfig Rule config json
     * @return  Response
     */
    public CSResponse addRuleConfig(String ruleConfig) {
        recEngineRepository.save(new RecEngine(RULE_CONFIG_ID, RULES, ruleConfig));
        return new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");
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
