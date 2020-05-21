package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.bundle.Bundle;
import com.zone24x7.rac.configservice.bundle.BundleRepository;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;

@Service
public class RecService {

    @Autowired
    private RecRepository recRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Lazy
    private RecEngineService recEngineService;

    /**
     * Get all recommendations.
     *
     * @return List of recommendations
     */
    public RecDetailList getAllRecs() {
        RecDetailList recDetailList = new RecDetailList();

        // Get all recs from the db.
        List<Rec> allRecs = recRepository.findAll();
        List<RecDetail> recDetails = new ArrayList<>();

        // Iterate the list.
        allRecs.forEach(rec -> {

            // Map the object.
            RecDetail recDetail = modelMapper.map(rec, RecDetail.class);

            // Retrieve bundle.
            Optional<Bundle> optionalBundle = bundleRepository.findById(rec.getBundleID());

            if (optionalBundle.isPresent()) {

                Bundle bundle = optionalBundle.get();
                RecBundle recBundle = new RecBundle();
                recBundle.setId(bundle.getId());
                recBundle.setName(bundle.getName());

                recDetail.setBundle(recBundle);

                // Add to the list.
                recDetails.add(recDetail);
            }
        });

        recDetailList.setRecs(recDetails);

        return recDetailList;
    }

    /**
     * Get rec by ID.
     *
     * @param id Rec ID
     * @return   Recommendation
     * @throws ValidationException Exception to throw
     */
    public RecDetail getRec(int id) throws ValidationException {

        // Find given rec ID from DB.
        Optional<Rec> recOptional = recRepository.findById(id);

        // If rec not found in db, return invalid rec id error.
        if (!recOptional.isPresent()) {
            throw new ValidationException(REC_ID_INVALID);
        }

        // Get rec.
        Rec rec = recOptional.get();

        // Map to object.
        RecDetail recDetail = modelMapper.map(rec, RecDetail.class);

        // Retrieve bundle.
        Optional<Bundle> optionalBundle = bundleRepository.findById(rec.getBundleID());

        if (optionalBundle.isPresent()) {

            Bundle bundle = optionalBundle.get();
            RecBundle recBundle = new RecBundle();
            recBundle.setId(bundle.getId());
            recBundle.setName(bundle.getName());

            recDetail.setBundle(recBundle);
        }

        return recDetail;
    }

    /**
     * Add new rec.
     *
     * @param rec Rec to be saved
     * @return    CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addRec(Rec rec) throws ValidationException, ServerException {

        // Validate name.
        RecValidations.validateRecName(rec.getName());

        // Validate bundle ID.
        Optional<Bundle> bundleOptional = bundleRepository.findById(rec.getBundleID());

        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Save rec.
        recRepository.save(rec);

        // Update recs config for rec engine.
        recEngineService.updateRecConfig();

        return new CSResponse(SUCCESS, REC_ADD_SUCCESS);
    }
}
