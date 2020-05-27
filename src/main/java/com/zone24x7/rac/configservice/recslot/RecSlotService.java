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
import com.zone24x7.rac.configservice.recslot.recslotrule.RecSlotRule;
import com.zone24x7.rac.configservice.recslot.recslotrule.RecSlotRuleRepository;
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

        List<RecSlotDetail> recSlotDetailsList = new ArrayList<>();

        // Iterate through the models list.
        allRecSlots.forEach(recSlot -> {

            // Fill rec slot details.
            RecSlotDetail recSlotDetail = fillRecSlotDetails(recSlot);

            // Add to list.
            recSlotDetailsList.add(recSlotDetail);
        });

        return new RecSlotList(recSlotDetailsList);
    }

    /**
     * Get rec slot details.
     *
     * @param id Rec slot ID
     * @return   Rec slot details
     * @throws ValidationException Exception to throw
     */
    public RecSlotDetail getRecSlot(int id) throws ValidationException {

        // Find given rec slot id from db.
        Optional<RecSlot> recSlotOptional = recSlotRepository.findById(id);

        // If rec slot not found in db, return invalid rec slot id error.
        if (!recSlotOptional.isPresent()) {
            throw new ValidationException(REC_SLOT_ID_INVALID);
        }

        // Fill rec slot details.
        return fillRecSlotDetails(recSlotOptional.get());
    }

    /**
     * Fill rec slot detail object.
     *
     * @param recSlot Rec slot
     * @return        Rec slot detail
     */
    private RecSlotDetail fillRecSlotDetails(RecSlot recSlot) {

        // Get channel.
        Optional<Channel> channelOptional = channelRepository.findById(recSlot.getChannelID());

        // Get page.
        Optional<Page> pageOptional = pageRepository.findById(recSlot.getPageID());

        // Get placeholder.
        Optional<Placeholder> placeholderOptional = placeholderRepository.findById(recSlot.getPlaceholderID());

        // Get rec.
        Optional<Rec> recOptional = recRepository.findById(recSlot.getRecID());
        RecSlotRec recSlotRec = modelMapper.map(recOptional.get(), RecSlotRec.class);

        return new RecSlotDetail(recSlot.getId(), channelOptional.get(), pageOptional.get(),
                                          placeholderOptional.get(), recSlotRec, null); // TODO: Set rules
    }

    /**
     * Add new rec slot.
     *
     * @param recSlotSummary Rec slot summary
     * @return               CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addNewRecSlot(RecSlotSummary recSlotSummary) throws ValidationException {

        // Validate channel ID.
        Optional<Channel> channelOptional = channelRepository.findById(recSlotSummary.getChannelId());

        if (!channelOptional.isPresent()) {
            throw new ValidationException(CHANNEL_ID_INVALID);
        }

        // Validate page ID.
        Optional<Page> pageOptional = pageRepository.findById(recSlotSummary.getPageId());

        if (!pageOptional.isPresent()) {
            throw new ValidationException(PAGE_ID_INVALID);
        }

        // Validate placeholder ID.
        Optional<Placeholder> placeholderOptional = placeholderRepository.findById(recSlotSummary.getPlaceholderId());

        if (!placeholderOptional.isPresent()) {
            throw new ValidationException(PLACEHOLDER_ID_INVALID);
        }

        // Validate rec ID.
        Optional<Rec> recOptional = recRepository.findById(recSlotSummary.getRecId());

        if (!recOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }

        // Check whether rule IDs exist.
        List<Integer> ruleIds = recSlotSummary.getRuleIds();
        if (ruleIds == null) {
            throw new ValidationException(REC_SLOT_RULE_IDS_CANNOT_BE_NULL);
        }

        if (ruleIds.isEmpty()) {
            throw new ValidationException(REC_SLOT_RULE_IDS_CANNOT_BE_EMPTY);
        }

        // Check whether rule IDs are valid.
        ruleIds.forEach(ruleID -> {
            // TODO: Check whether rule IDs are valid.
        });

        // Set values to rec slot model.
        RecSlot recSlot = new RecSlot();
        recSlot.setChannelID(recSlotSummary.getChannelId());
        recSlot.setPageID(recSlotSummary.getPageId());
        recSlot.setPlaceholderID(recSlotSummary.getPlaceholderId());
        recSlot.setRecID(recSlotSummary.getRecId());

        // Save rec slot.
        recSlotRepository.save(recSlot);

        // Iterate rule list.
        ruleIds.forEach(ruleID -> {

            RecSlotRule recSlotRule = new RecSlotRule();
            recSlotRule.setRecSlotID(recSlot.getId());
            recSlotRule.setRuleID(ruleID);

            // Save rec slot - rule association.
            recSlotRuleRepository.save(recSlotRule);
        });

        return new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);
    }
}
