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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.REC_ID_INVALID;

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

    public RecSlotDetail getRecSlot(int id) throws ValidationException {

        // Find given rec slot id from db.
        Optional<RecSlot> recSlotOptional = recSlotRepository.findById(id);

        // If rec slot not found in db, return invalid rec slot id error.
        if (!recSlotOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
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
}
