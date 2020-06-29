package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.bundle.Bundle;
import com.zone24x7.rac.configservice.bundle.BundleRepository;
import com.zone24x7.rac.configservice.bundle.BundleValidations;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.recslot.RecSlot;
import com.zone24x7.rac.configservice.recslot.RecSlotRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.REC_ADD_SUCCESS;
import static com.zone24x7.rac.configservice.util.Strings.REC_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.REC_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecService {

    @Autowired
    private RecRepository recRepository;

    @Autowired
    private RecSlotRepository recSlotRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    @Lazy
    private RecEngineService recEngineService;

    /**
     * Get all recommendations.
     *
     * @return List of recommendations
     */
    public RecList getAllRecs() {

        // Rec details list.
        List<RecDetail> recDetails = new ArrayList<>();

        // Get all recs from the db.
        List<Rec> allRecs = recRepository.findAllByOrderByIdDesc();

        // Get details for each rec.
        allRecs.forEach(rec -> {
            Optional<Bundle> optionalBundle = bundleRepository.findById(rec.getBundleID());
            if (optionalBundle.isPresent()) {
                RecBundle recBundle = new RecBundle(rec.getBundleID(), optionalBundle.get().getName());
                recDetails.add(new RecDetail(rec.getId(), rec.getName(), recBundle));
            }
        });

        return new RecList(recDetails);
    }

    /**
     * Get rec by ID.
     *
     * @param id Rec ID
     * @return Recommendation
     * @throws ValidationException Exception to throw
     */
    public RecDetail getRec(int id) throws ValidationException {

        // Validate id.
        RecValidations.validateID(id);

        // Find given rec id from db.
        Optional<Rec> recOptional = recRepository.findById(id);
        if (!recOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }

        // Get rec.
        Rec rec = recOptional.get();

        // Get bundle details for given rec id.
        Optional<Bundle> optionalBundle = bundleRepository.findById(rec.getBundleID());
        if (optionalBundle.isPresent()) {
            RecBundle recBundle = new RecBundle(rec.getBundleID(), optionalBundle.get().getName());
            return new RecDetail(rec.getId(), rec.getName(), recBundle);
        }

        // Else, throw server error.
        throw new ValidationException(Strings.REC_BUNDLE_DETAILS_NOT_FOUND);
    }

    /**
     * Add new rec.
     *
     * @param rec Rec to be saved
     * @return CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addRec(Rec rec) throws ValidationException, ServerException {

        // Validate name.
        RecValidations.validateRecName(rec.getName());

        // Validate bundle id.
        BundleValidations.validateID(rec.getBundleID());
        Optional<Bundle> bundleOptional = bundleRepository.findById(rec.getBundleID());
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Save rec.
        recRepository.save(rec);

        // Update recs config for rec engine.
        recEngineService.updateRecConfig();

        // Return status.
        String[] arr = REC_ADD_SUCCESS.split(":");
        return new CSResponse(SUCCESS, arr[0], arr[1], String.valueOf(rec.getId()));
    }

    /**
     * Edit rec.
     *
     * @param rec Rec to be saved
     * @return CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse editRec(int id, Rec rec) throws ValidationException, ServerException {

        // Validate id.
        RecValidations.validateID(id);
        Optional<Rec> recOptional = recRepository.findById(id);
        if (!recOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }

        // Validate name.
        RecValidations.validateRecName(rec.getName());

        // Validate bundle id.
        BundleValidations.validateID(rec.getBundleID());
        Optional<Bundle> bundleOptional = bundleRepository.findById(rec.getBundleID());
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Set id.
        rec.setId(id);

        // Save rec.
        recRepository.save(rec);

        // Update recs config for rec engine.
        recEngineService.updateRecConfig();

        // Return status.
        return new CSResponse(SUCCESS, REC_UPDATED_SUCCESSFULLY);
    }


    /**
     * Delete rec for given id.
     *
     * @param id Rec ID
     * @return CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Server exception to throw
     */
    public CSResponse deleteRec(int id) throws ValidationException, ServerException {

        // Validate id.
        RecValidations.validateID(id);

        // Check given rec id is exists.
        Optional<Rec> recOptional = recRepository.findById(id);
        if (!recOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }

        // Check rec id already use in rec slots.
        List<RecSlot> recSlots = recSlotRepository.findAllByRecID(id);
        if (!recSlots.isEmpty()) {
            throw new ValidationException(Strings.REC_ID_ALREADY_USE_IN_REC_SLOTS);
        }


        // Delete rec.
        recRepository.deleteById(id);

        // Update recs config for rec engine.
        recEngineService.updateRecConfig();


        // Return status.
        return new CSResponse(SUCCESS, Strings.REC_DELETED_SUCCESSFULLY);
    }




}
