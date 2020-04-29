package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zone24x7.rac.configservice.utils.Strings.BUNDLES;
import static com.zone24x7.rac.configservice.utils.Strings.SUCCESS;

/**
 * Service class relating to Rec Engine.
 *
 */
@Service
public class RecEngineService {

    private RecEngineRepository recEngineRepository;

    @Autowired
    public RecEngineService(RecEngineRepository recEngineRepository) {
        this.recEngineRepository = recEngineRepository;
    }

    /**
     * Add bundle Json.
     *
     * @param bundleJson Bundle Json
     * @return           Message DTO
     */
    MessageDTO addBundleJson(String bundleJson) {

        RecEngineModel recEngineModel = recEngineRepository.findByKey(BUNDLES);
        recEngineModel.setValue(bundleJson);

        recEngineRepository.save(recEngineModel);

        return new MessageDTO(SUCCESS, "Bundle JSON successfully added");
    }
}
