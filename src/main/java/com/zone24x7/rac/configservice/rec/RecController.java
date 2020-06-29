package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.zone24x7.rac.configservice.util.Strings.HEADER_CS_META;

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
    public RecList getAllRecommendations() {
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
    public CSResponse addRec(@RequestBody RecDetail recDetail, HttpServletResponse response) throws ValidationException, ServerException {
        // Add rec.
        CSResponse csResponse = recService.addRec(modelMapper.map(recDetail, Rec.class));

        // Set new rec id as response header.
        response.setHeader(HEADER_CS_META, "{recID: " + MDC.get(HEADER_CS_META) + "}");
        MDC.remove(HEADER_CS_META);

        // Return response.
        return csResponse;
    }

    /**
     * Edit rec.
     * @param recDetail Rec details
     * @return          CS Response
     * @throws ValidationException Exception to throw
     */
    @PutMapping("/recs/{id}")
    public CSResponse editRec(@PathVariable int id, @RequestBody RecDetail recDetail) throws ValidationException, ServerException {
        return recService.editRec(id, modelMapper.map(recDetail, Rec.class));
    }



    /**
     * Delete rec.
     *
     * @param id Rec ID
     * @return   CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Service exception to throw
     */
    @DeleteMapping("/recs/{id}")
    public CSResponse deleteRec(@PathVariable int id) throws ValidationException, ServerException {
        return recService.deleteRec(id);
    }
}
