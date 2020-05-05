package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Class testing RecEngineController.
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class RecEngineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecEngineService recEngineService;

    /**
     * Unit test when adding a bundle json.
     *
     * @throws Exception Exception to throw
     */
    @Test
    public void should_succeed_when_bundle_details_are_added() throws Exception {
        String uri = "/v1/recEngine/bundles";
        String inputJson = "{\"bundles\":[{\"id\":\"1\",\"name\":\"Bundle 1\",\"type\":null,\"defaultLimit\":5,\"algorithms\":[{\"rank\":0,\"algorithm\":{\"id\":\"100\",\"name\":\"TopTrending\",\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Top Trending\",\"customDisplayText\":\"Top Trending Products\"}},{\"rank\":1,\"algorithm\":{\"id\":\"101\",\"name\":\"BestSellers\",\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Best Sellers\",\"customDisplayText\":null}}],\"algoCombineInfo\":{\"enableCombine\":false,\"combineDisplayText\":null}}]}";

        Response response = new Response(SUCCESS, "N/A", "Bundle JSON successfully added");

        Mockito.when(recEngineService.addBundleJson(anyString())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(uri)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("{\"status\":\"success\",\"code\":\"N/A\",\"message\":\"Bundle JSON successfully added\"}", mvcResult.getResponse().getContentAsString());

        Mockito.verify(recEngineService).addBundleJson(anyString());
    }

    /**
     * Unit test when adding a rule json.
     *
     * @throws Exception Exception to throw
     */
    @Test
    public void should_succeed_when_rule_details_are_added() throws Exception {
        String uri = "/v1/recEngine/rules";
        String inputJson = "{\"rules\":[{\"id\":\"133\",\"name\":\"Test Rule 1\",\"type\":\"BOOST\",\"isGlobal\":false,\"matchingCondition\":\"(department == \\\"Shoes\\\" || (department == \\\"Clothing\\\" && brand == \\\"Tommy\\\"))\",\"actionCondition\":\"(brand == \\\"Nike\\\")\"}]}";

        Response response = new Response(SUCCESS, "N/A", "Rule JSON successfully added");

        Mockito.when(recEngineService.addRuleJson(anyString())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(uri)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("{\"status\":\"success\",\"code\":\"N/A\",\"message\":\"Rule JSON successfully added\"}", mvcResult.getResponse().getContentAsString());

        Mockito.verify(recEngineService).addRuleJson(anyString());
    }

    /**
     * Unit test when adding a rec json.
     *
     * @throws Exception Exception to throw
     */
    @Test
    public void should_succeed_when_rec_details_are_added() throws Exception {
        String uri = "/v1/recEngine/recs";
        String inputJson = "{\"recs\":[{\"id\":\"100\",\"name\":\"Sample Rec Config 1\",\"type\":\"REGULAR\",\"matchingCondition\":null,\"regularConfig\":{\"bundleId\":\"1201\"},\"testConfig\":null}]}";

        Response response = new Response(SUCCESS, "N/A", "Rec JSON successfully added");

        Mockito.when(recEngineService.addRecJson(anyString())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(uri)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("{\"status\":\"success\",\"code\":\"N/A\",\"message\":\"Rec JSON successfully added\"}", mvcResult.getResponse().getContentAsString());

        Mockito.verify(recEngineService).addRecJson(anyString());
    }

    /**
     * Unit test when adding a rec slot json.
     *
     * @throws Exception Exception to throw
     */
    @Test
    public void should_succeed_when_rec_slot_details_are_added() throws Exception {
        String uri = "/v1/recEngine/recSlots";
        String inputJson = "{\"recSlots\":[{\"channel\":\"TCom\",\"page\":\"PDP\",\"placeholder\":\"Grid\",\"ruleIds\":[\"700\",\"701\"],\"recIds\":[\"100\"]}]}";

        Response response = new Response(SUCCESS, "N/A", "Rec slot JSON successfully added");

        Mockito.when(recEngineService.addRecSlotJson(anyString())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(uri)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("{\"status\":\"success\",\"code\":\"N/A\",\"message\":\"Rec slot JSON successfully added\"}", mvcResult.getResponse().getContentAsString());

        Mockito.verify(recEngineService).addRecSlotJson(anyString());
    }
}
