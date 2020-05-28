package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.channel.ChannelRepository;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.page.PageRepository;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderRepository;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;

@Service
public class RecSlotService {

    @Autowired
    private RecSlotRepository recSlotRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PlaceholderRepository placeholderRepository;

    @Autowired
    private RecRepository recRepository;

    @Autowired
    private RecSlotRuleRepository recSlotRuleRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all rec slots.
     *
     * @return Rec slots
     */
    public RecSlotList getAllRecSlots() {

        // Retrieve all rec slots.
        List<RecSlot> allRecSlots = recSlotRepository.findAllByOrderByIdDesc();

        // Rec slot details.
        List<RecSlotDetail> recSlotDetailList = new ArrayList<>();

        // Get detail for each rec slot.
        allRecSlots.forEach(recSlot -> recSlotDetailList.add(getRecSlotDetail(recSlot)));

        // Return list.
        return new RecSlotList(recSlotDetailList);
    }

    /**
     * Get rec slot detail.
     *
     * @param id Rec slot id
     * @return   Rec slot details
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
        return getRecSlotDetail(recSlotOptional.get());
    }

    /**
     * Get rec slot detail for given rec slot.
     *
     * @param recSlot Rec slot
     * @return        Rec slot detail
     */
    private RecSlotDetail getRecSlotDetail(RecSlot recSlot) {

        // Get channel.
        Optional<Channel> optionalChannel = channelRepository.findById(recSlot.getChannelID());
        Channel channel = new Channel();
        if (optionalChannel.isPresent()) {
            channel = optionalChannel.get();
        }

        // Get page.
        Optional<Page> optionalPage = pageRepository.findById(recSlot.getPageID());
        Page page = new Page();
        if (optionalPage.isPresent()) {
            page = optionalPage.get();
        }

        // Get placeholder.
        Optional<Placeholder> optionalPlaceholder = placeholderRepository.findById(recSlot.getPlaceholderID());
        Placeholder placeholder = new Placeholder();
        if (optionalPlaceholder.isPresent()) {
            placeholder = optionalPlaceholder.get();
        }

        // Get rec.
        Optional<Rec> optionalRec = recRepository.findById(recSlot.getRecID());
        RecSlotRecDetail recSlotRecDetail = new RecSlotRecDetail();
        if (optionalRec.isPresent()) {
            recSlotRecDetail = new RecSlotRecDetail(optionalRec.get().getId(), optionalRec.get().getName());
        }

        // Get all rec_slot-rule associations for given rec slot id.
        List<RecSlotRule> recSlotRules = recSlotRuleRepository.findAllByRecSlotID(recSlot.getId());
        List<RecSlotRuleDetail> recSlotRuleDetails = new ArrayList<>();
        recSlotRules.forEach(rsr -> {
            //TODO: Add rule details to the "recSlotRuleDetails" list.
        });


        // Return rec slot detail.
        return new RecSlotDetail(recSlot.getId(), channel, page, placeholder, recSlotRecDetail, recSlotRuleDetails);
    }

    /**
     * Add new rec slot.
     *
     * @param recSlotDetail Rec slot detail
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addRecSlot(RecSlotDetail recSlotDetail) throws ValidationException {

        // Validate rec slot detail.
        validateRecSlotDetail(recSlotDetail);

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

        // Return status.
        return new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);
    }

    public CSResponse editRecSlot(int id, RecSlotDetail recSlotDetail) throws ValidationException {

        // Validate id.
        RecSlotValidations.validateID(id);

        // Find given rec slot id from db.
        // If rec slot not found in db, return invalid rec slot id error.
        Optional<RecSlot> recSlotOptional = recSlotRepository.findById(id);
        if (!recSlotOptional.isPresent()) {
            throw new ValidationException(REC_SLOT_ID_INVALID);
        }

        // Validate rec slot detail.
        validateRecSlotDetail(recSlotDetail);

        // Update details.
        RecSlot recSlot = recSlotOptional.get();
        recSlot.setId(id);
        recSlot.setChannelID(recSlotDetail.getChannel().getId());
        recSlot.setPageID(recSlotDetail.getPage().getId());
        recSlot.setPlaceholderID(recSlotDetail.getPlaceholder().getId());
        recSlot.setRecID(recSlotDetail.getRec().getId());

        // Update in db.
        recSlotRepository.save(recSlot);

        // Retrieve existing rec slot - rule associations.
        List<RecSlotRule> recSlotRuleList = recSlotRuleRepository.findAllByRecSlotID(id);

        // Iterate through the list and delete associations.
        recSlotRuleList.forEach(recSlotRule -> recSlotRuleRepository.delete(recSlotRule));

        // Iterate through the new associations list.
        recSlotDetail.getRules().forEach(recSlotRule ->

                // Save to db.
                recSlotRuleRepository.save((new RecSlotRule(id, recSlotRule.getId())))
        );

        // Return status.
        return new CSResponse(SUCCESS, REC_SLOT_UPDATED_SUCCESSFULLY);
    }


















    /**
     * Validate given rec slot detail are correct.
     *
     * @param recSlotDetail rec slot detail.
     * @throws ValidationException if validation failed.
     */
    private void validateRecSlotDetail(RecSlotDetail recSlotDetail) throws ValidationException {

        // Channel should exist.
        Channel channel = recSlotDetail.getChannel();
        if (channel == null) {
            throw new ValidationException(CHANNEL_CANNOT_BE_NULL);
        }

        // Validate channel id.
        Optional<Channel> optionalChannel = channelRepository.findById(channel.getId());
        if (!optionalChannel.isPresent()) {
            throw new ValidationException(CHANNEL_ID_INVALID);
        }

        // Page should exist.
        Page page = recSlotDetail.getPage();
        if (page == null) {
            throw new ValidationException(PAGE_CANNOT_BE_NULL);
        }

        // Validate page id.
        Optional<Page> optionalPage = pageRepository.findById(page.getId());
        if (!optionalPage.isPresent()) {
            throw new ValidationException(PAGE_ID_INVALID);
        }

        // Placeholder should exist.
        Placeholder placeholder = recSlotDetail.getPlaceholder();
        if (placeholder == null) {
            throw new ValidationException(PLACEHOLDER_CANNOT_BE_NULL);
        }

        // Validate placeholder id.
        Optional<Placeholder> optionalPlaceholder = placeholderRepository.findById(placeholder.getId());
        if (!optionalPlaceholder.isPresent()) {
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

        // Check whether rule ids are valid.
        List<RecSlotRuleDetail> rules = recSlotDetail.getRules();
        rules.forEach(rule -> {
            // TODO: Check whether rule IDs are valid.
        });
    }
}
