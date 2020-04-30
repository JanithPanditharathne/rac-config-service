package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.zone24x7.rac.configservice.utils.Strings.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Class testing RecEngineService.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RecEngineServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineService recEngineService;

    /**
     * Unit test when adding a bundle json.
     *
     */
    @Test
    public void should_succeed_when_adding_a_bundle_json() {
        String bundleJson = "{\"bundles\":[{\"id\":\"1\",\"name\":\"Bundle 1\",\"type\":null,\"defaultLimit\":5,\"algorithms\":[{\"rank\":0,\"algorithm\":{\"id\":\"100\",\"name\":\"TopTrending\",\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Top Trending\",\"customDisplayText\":\"Top Trending Products\"}},{\"rank\":1,\"algorithm\":{\"id\":\"101\",\"name\":\"BestSellers\",\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Best Sellers\",\"customDisplayText\":null}}],\"algoCombineInfo\":{\"enableCombine\":false,\"combineDisplayText\":null}}]}";
        Response expectedResponse = new Response(SUCCESS,"N/A", "Bundle JSON successfully added");

        RecEngine recEngine = Mockito.mock(RecEngine.class);

        Mockito.when(recEngineRepository.findByConfigType(BUNDLES)).thenReturn(recEngine);

        Response actualResponse = recEngineService.addBundleJson(bundleJson);

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Mockito.verify(recEngineRepository).findByConfigType(anyString());
    }

    /**
     * Unit test when adding a rule json.
     *
     */
    @Test
    public void should_succeed_when_adding_a_rule_json() {
        String ruleJson = "{\"rules\":[{\"id\":\"133\",\"name\":\"Test Rule 1\",\"type\":\"BOOST\",\"isGlobal\":false,\"matchingCondition\":\"(department == \\\"Shoes\\\" || (department == \\\"Clothing\\\" && brand == \\\"Tommy\\\"))\",\"actionCondition\":\"(brand == \\\"Nike\\\")\"}]}";
        Response expectedResponse = new Response(SUCCESS,"N/A", "Rule JSON successfully added");

        RecEngine recEngine = Mockito.mock(RecEngine.class);

        Mockito.when(recEngineRepository.findByConfigType(RULES)).thenReturn(recEngine);

        Response actualResponse = recEngineService.addRuleJson(ruleJson);

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Mockito.verify(recEngineRepository).findByConfigType(anyString());
    }

    /**
     * Unit test when adding a rec json.
     *
     */
    @Test
    public void should_succeed_when_adding_a_rec_json() {
        String recJson = "{\"recs\":[{\"id\":\"100\",\"name\":\"Sample Rec Config 1\",\"type\":\"REGULAR\",\"matchingCondition\":null,\"regularConfig\":{\"bundleId\":\"1201\"},\"testConfig\":null}]}";
        Response expectedResponse = new Response(SUCCESS,"N/A", "Recommendation JSON successfully added");

        RecEngine recEngine = Mockito.mock(RecEngine.class);

        Mockito.when(recEngineRepository.findByConfigType(RECS)).thenReturn(recEngine);

        Response actualResponse = recEngineService.addRecJson(recJson);

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Mockito.verify(recEngineRepository).findByConfigType(anyString());
    }

    /**
     * Unit test when adding a rec slot json.
     *
     */
    @Test
    public void should_succeed_when_adding_a_rec_slot_json() {
        String recSlotJson = "{\"recSlots\":[{\"channel\":\"TCom\",\"page\":\"PDP\",\"placeholder\":\"Grid\",\"ruleIds\":[\"700\",\"701\"],\"recIds\":[\"100\"]}]}";
        Response expectedResponse = new Response(SUCCESS,"N/A", "Recommendation slots JSON successfully added");

        RecEngine recEngine = Mockito.mock(RecEngine.class);

        Mockito.when(recEngineRepository.findByConfigType(REC_SLOTS)).thenReturn(recEngine);

        Response actualResponse = recEngineService.addRecSlotJson(recSlotJson);

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Mockito.verify(recEngineRepository).findByConfigType(anyString());
    }
}
