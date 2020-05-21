package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class RecController {

    @Autowired
    private RecService recService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all recs.
     *
     * @return All recs
     */
    @GetMapping("/recs")
    public RecDetailList getAllRecommendations() {
        return recService.getAllRecs();
    }

    /**
     * Get rec by ID.
     *
     * @param id Rec ID
     * @return   Recommendation
     * @throws ValidationException Exception to throw
     */
    @GetMapping("/recs/{id}")
    public RecDetail getRec(@PathVariable int id) throws ValidationException {
        return recService.getRec(id);
    }

    /**
     * Add new rec.
     * @param recDetail Rec details
     * @return          CS Response
     * @throws ValidationException Exception to throw
     */
    @PostMapping("/recs")
    public CSResponse addRec(@RequestBody RecDetail recDetail) throws ValidationException, ServerException {
        return recService.addRec(modelMapper.map(recDetail, Rec.class));
    }
}
