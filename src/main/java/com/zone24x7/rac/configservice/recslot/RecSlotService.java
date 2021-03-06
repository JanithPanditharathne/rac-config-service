package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.Metadata;
import com.zone24x7.rac.configservice.metadata.MetadataRepository;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.rule.Rule;
import com.zone24x7.rac.configservice.rule.RuleRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.CHANNELS;
import static com.zone24x7.rac.configservice.util.Strings.CHANNEL_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.CHANNEL_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.PAGES;
import static com.zone24x7.rac.configservice.util.Strings.PAGE_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.PAGE_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.PLACEHOLDERS;
import static com.zone24x7.rac.configservice.util.Strings.PLACEHOLDER_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.PLACEHOLDER_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.REC_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.REC_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOT_ADDED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOT_DELETED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOT_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOT_RULES_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOT_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.RULE_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.SIMILAR_REC_SLOT_ALREADY_EXISTS;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecSlotService {

    @Autowired
    private RecSlotRepository recSlotRepository;

    @Autowired
    private RecRepository recRepository;

    @Autowired
    private RecSlotRuleRepository recSlotRuleRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    @Lazy
    private RecEngineService recEngineService;

    @Autowired
    private MetadataRepository metadataRepository;

    /**
     * Get all rec slots.
     *
     * @return Rec slots
     */
    public RecSlotList getAllRecSlots(boolean includeGlobalRules) {

        // Retrieve all rec slots.
        List<RecSlot> allRecSlots = recSlotRepository.findAllByOrderByIdDesc();

        // If "includeGlobalRules" is true, then get all global rules.
        List<RecSlotRuleDetail> globalRuleList = new ArrayList<>();
        if (includeGlobalRules) {
            globalRuleList = getGlobalRuleList();
        }

        // Rec slot details.
        List<RecSlotDetail> recSlotDetailList = new ArrayList<>();

        // Get detail for each rec slot.
        List<RecSlotRuleDetail> finalGlobalRuleList = globalRuleList;
        allRecSlots.forEach(recSlot -> recSlotDetailList.add(getRecSlotDetail(recSlot, finalGlobalRuleList)));

        // Return list.
        return new RecSlotList(recSlotDetailList);
    }

    /**
     * Get rec slot detail.
     *
     * @param id Rec slot id
     * @return Rec slot details
     * @throws ValidationException Exception to throw
     */
    public RecSlotDetail getRecSlot(int id) throws ValidationException {

        // Validate id.
        RecSlotValidations.validateID(id);

        // Find given rec slot id from db.
        // If rec slot not found in db, return invalid rec slot id error.
        Optional<RecSlot> recSlotOptional = recSlotRepository.findById(id);
        if (!recSlotOptional.isPresent()) {
            throw new ValidationException(REC_SLOT_ID_INVALID);
        }

        // Return rec slot detail.
        return getRecSlotDetail(recSlotOptional.get(), getGlobalRuleList());
    }


    /**
     * Return global rule list.
     *
     * @return global rule list.
     */
    private List<RecSlotRuleDetail> getGlobalRuleList() {
        List<RecSlotRuleDetail> globalRuleList = new ArrayList<>();
        List<Rule> globalRules = ruleRepository.findAllByIsGlobal(true);
        globalRules.forEach(gr -> globalRuleList.add(new RecSlotRuleDetail(gr.getId(), gr.getName())));
        return globalRuleList;
    }

    /**
     * Get rec slot detail for given rec slot.
     *
     * @param recSlot Rec slot
     * @return Rec slot detail
     */
    private RecSlotDetail getRecSlotDetail(RecSlot recSlot,  List<RecSlotRuleDetail> globalRuleList) {

        // Get channel.
        Metadata channel = metadataRepository.findByTypeAndId(CHANNELS, recSlot.getChannelID());

        // Get page.
        Metadata page = metadataRepository.findByTypeAndId(PAGES, recSlot.getPageID());

        // Get placeholder.
        Metadata placeholder = metadataRepository.findByTypeAndId(PLACEHOLDERS, recSlot.getPlaceholderID());

        // Get rec.
        Optional<Rec> optionalRec = recRepository.findById(recSlot.getRecID());
        RecSlotRecDetail recSlotRecDetail = new RecSlotRecDetail();
        if (optionalRec.isPresent()) {
            recSlotRecDetail = new RecSlotRecDetail(optionalRec.get().getId(), optionalRec.get().getName());
        }

        // Get all rec_slot-rule associations for given rec slot id.
        List<RecSlotRuleDetail> recSlotRuleDetails = new ArrayList<>();
        List<RecSlotRule> recSlotRules = recSlotRuleRepository.findAllByRecSlotID(recSlot.getId());
        recSlotRules.forEach(rsr -> {
            Optional<Rule> optionalRule = ruleRepository.findById(rsr.getRuleID());
            optionalRule.ifPresent(rule -> recSlotRuleDetails.add(new RecSlotRuleDetail(rsr.getRuleID(), rule.getName())));
        });

        // Include global rules if available.
        if (!globalRuleList.isEmpty()) {
            recSlotRuleDetails.addAll(globalRuleList);
        }

        // Return rec slot detail.
        return new RecSlotDetail(recSlot.getId(), channel, page, placeholder, recSlotRecDetail, recSlotRuleDetails);
    }

    /**
     * Add new rec slot.
     *
     * @param recSlotDetail Rec slot detail
     * @return CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addRecSlot(RecSlotDetail recSlotDetail) throws ValidationException, ServerException {

        // Validate rec slot detail.
        validateRecSlotDetail(recSlotDetail);

        // Check whether given channel, page, placeholder combination is already exists.
        List<RecSlot> recSlots = recSlotRepository.findAllByChannelIDAndPageIDAndPlaceholderID(recSlotDetail.getChannel().getId(),
                recSlotDetail.getPage().getId(), recSlotDetail.getPlaceholder().getId());

        if (!recSlots.isEmpty()) {
            throw new ValidationException(SIMILAR_REC_SLOT_ALREADY_EXISTS);
        }

        // Save rec slot.
        RecSlot recSlot = recSlotRepository.save(new RecSlot(recSlotDetail.getChannel().getId(),
                recSlotDetail.getPage().getId(), recSlotDetail.getPlaceholder().getId(), recSlotDetail.getRec().getId()));

        // Get detail for each rec slot rule.
        List<RecSlotRule> recSlotRules = new ArrayList<>();
        recSlotDetail.getRules().forEach(rule -> recSlotRules.add(new RecSlotRule(recSlot.getId(), rule.getId())));

        // Save all new rec_slot-rule associations.
        if (!recSlotRules.isEmpty()) {
            recSlotRuleRepository.saveAll(recSlotRules);
        }

        // Update rec engine rec slot config.
        recEngineService.updateRecSlotConfig();

        // Return status.
        String[] arr = REC_SLOT_ADDED_SUCCESSFULLY.split(":");
        return new CSResponse(SUCCESS, arr[0], arr[1], String.valueOf(recSlot.getId()));
    }

    /**
     * Edit rec slot.
     *
     * @param id            Rec slot ID
     * @param recSlotDetail Rec slot details
     * @return CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse editRecSlot(int id, RecSlotDetail recSlotDetail) throws ValidationException, ServerException {

        // Validate id.
        RecSlotValidations.validateID(id);

        // Find given rec slot id from db.
        // If rec slot not found in db, return invalid rec slot id error.
        Optional<RecSlot> optionalRecSlot = recSlotRepository.findById(id);
        if (!optionalRecSlot.isPresent()) {
            throw new ValidationException(REC_SLOT_ID_INVALID);
        }

        // Check whether given channel, page, placeholder combination is already exists.
        List<RecSlot> recSlots = recSlotRepository.findAllByChannelIDAndPageIDAndPlaceholderID(recSlotDetail.getChannel().getId(),
                recSlotDetail.getPage().getId(), recSlotDetail.getPlaceholder().getId());
        if (!recSlots.isEmpty() && (recSlots.size() > 1 || recSlots.get(0).getId() != id)) {
            throw new ValidationException(SIMILAR_REC_SLOT_ALREADY_EXISTS);
        }


        // Validate rec slot detail.
        validateRecSlotDetail(recSlotDetail);

        // Update rec slot detail in db.
        RecSlot recSlot = new RecSlot(recSlotDetail.getChannel().getId(), recSlotDetail.getPage().getId(),
                recSlotDetail.getPlaceholder().getId(), recSlotDetail.getRec().getId());
        recSlot.setId(id);
        recSlotRepository.save(recSlot);


        // Find all existing rec_slot-rule associations for given rec_slot id and delete them.
        List<RecSlotRule> existingRecSlotRules = recSlotRuleRepository.findAllByRecSlotID(id);
        recSlotRuleRepository.deleteAll(existingRecSlotRules);

        // Save all new rec_slot-rule associations.
        List<RecSlotRule> newRecSlotRules = new ArrayList<>();
        recSlotDetail.getRules().forEach(recSlotRule -> newRecSlotRules.add(new RecSlotRule(id, recSlotRule.getId())));
        recSlotRuleRepository.saveAll(newRecSlotRules);


        // Update rec engine rec slot config.
        recEngineService.updateRecSlotConfig();

        // Return status.
        return new CSResponse(SUCCESS, REC_SLOT_UPDATED_SUCCESSFULLY);
    }

    /**
     * Delete rec slot.
     *
     * @param id Rec slot ID
     * @return CS Response
     * @throws ValidationException Validation exception to throw
     * @throws ServerException     Server exception to throw
     */
    public CSResponse deleteRecSlot(int id) throws ValidationException, ServerException {

        // Validate id.
        RecSlotValidations.validateID(id);

        // Check given rec slot id is exists.
        Optional<RecSlot> optionalRecSlot = recSlotRepository.findById(id);
        if (!optionalRecSlot.isPresent()) {
            throw new ValidationException(REC_SLOT_ID_INVALID);
        }


        // Delete rec slot - rule associations.
        List<RecSlotRule> recSlotRules = recSlotRuleRepository.findAllByRecSlotID(id);
        recSlotRuleRepository.deleteAll(recSlotRules);


        // Delete rec slot.
        recSlotRepository.deleteById(id);

        // Update rec engine rec slot config.
        recEngineService.updateRecSlotConfig();

        // Return status.
        return new CSResponse(SUCCESS, REC_SLOT_DELETED_SUCCESSFULLY);
    }


    /**
     * Validate given rec slot detail are correct.
     *
     * @param recSlotDetail Rec slot detail
     * @throws ValidationException If validation failed
     */
    private void validateRecSlotDetail(RecSlotDetail recSlotDetail) throws ValidationException {

        // Channel should exist.
        Metadata channelMetadata = recSlotDetail.getChannel();
        if (channelMetadata == null) {
            throw new ValidationException(CHANNEL_CANNOT_BE_NULL);
        }

        // Validate channel id.
        Metadata channel = metadataRepository.findByTypeAndId(CHANNELS, channelMetadata.getId());
        if (channel == null) {
            throw new ValidationException(CHANNEL_ID_INVALID);
        }

        // Page should exist.
        Metadata pageMetadata = recSlotDetail.getPage();
        if (pageMetadata == null) {
            throw new ValidationException(PAGE_CANNOT_BE_NULL);
        }

        // Validate page id.
        Metadata page = metadataRepository.findByTypeAndId(PAGES, pageMetadata.getId());
        if (page == null) {
            throw new ValidationException(PAGE_ID_INVALID);
        }

        // Placeholder should exist.
        Metadata placeholderMetadata = recSlotDetail.getPlaceholder();
        if (placeholderMetadata == null) {
            throw new ValidationException(PLACEHOLDER_CANNOT_BE_NULL);
        }

        // Validate placeholder id.
        Metadata placeholder = metadataRepository.findByTypeAndId(PLACEHOLDERS, placeholderMetadata.getId());
        if (placeholder == null) {
            throw new ValidationException(PLACEHOLDER_ID_INVALID);
        }

        // Rec should exist.
        RecSlotRecDetail rec = recSlotDetail.getRec();
        if (rec == null) {
            throw new ValidationException(REC_CANNOT_BE_NULL);
        }

        // Validate rec id.
        Optional<Rec> optionalRec = recRepository.findById(rec.getId());
        if (!optionalRec.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }



        // Rules cannot be null.
        List<RecSlotRuleDetail> rules = recSlotDetail.getRules();
        if (rules == null) {
            throw new ValidationException(REC_SLOT_RULES_CANNOT_BE_NULL);
        }

        // Keep rule list without globals.
        List<RecSlotRuleDetail> rulesWithoutGlobal = new ArrayList<>();

        // Check whether rule ids are valid.
        for (RecSlotRuleDetail r : rules) {
            Optional<Rule> ruleOptional = ruleRepository.findById(r.getId());
            if (!ruleOptional.isPresent()) {
                throw new ValidationException(RULE_ID_INVALID + " " + r.getId());
            }

            // Add rec slot rule detail to the list without global rules.
            if (!ruleOptional.get().getIsGlobal()) {
                rulesWithoutGlobal.add(r);
            }
        }

        // Update the rec slot detail rules without the global ones.
        recSlotDetail.setRules(rulesWithoutGlobal);
    }
}
