package com.zone24x7.rac.configservice.rule.expression;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleExprConverterTest {


    private final ObjectMapper objectMapper = new ObjectMapper();



    @Nested
    @DisplayName("convert json expression to string method")
    class ConvertJsonExprToString {


        /**
         * Test: rule expression converter for "Brand".
         * @throws IOException while parsing JSON.
         */
        @Test
        @DisplayName("test for brand")
        public void testForBrand() throws IOException {
            String json = "{\n" +
                    "  \"type\": \"Brand\",\n" +
                    "  \"condition\": \"AND\",\n" +
                    "  \"operator\": \"eq\",\n" +
                    "  \"value\": [\n" +
                    "    \"Nike\",\n" +
                    "    \"Puma\"\n" +
                    "  ]\n" +
                    "}";

            List<BaseExpr> exprJson = new ArrayList<>();
            exprJson.add(objectMapper.readValue(json, BaseExpr.class));
            String actualResult = RuleExprConverter.convertJsonExprToString(exprJson);
            String expectedResult = "((brand == \"Nike\") || (brand == \"Puma\"))";
            assertEquals(expectedResult, actualResult);
        }





        /**
         * Test: rule expression converter for "ProductNumber".
         * @throws IOException while parsing JSON.
         */
        @Test
        @DisplayName("test for product number")
        public void testForProductNumber() throws IOException {
            String json = "{\n" +
                    "  \"type\": \"ProductNumber\",\n" +
                    "  \"condition\": \"AND\",\n" +
                    "  \"operator\": \"eq\",\n" +
                    "  \"value\": [\n" +
                    "    \"111\",\n" +
                    "    \"222\",\n" +
                    "    \"4e5ff\"\n" +
                    "  ]\n" +
                    "}";

            List<BaseExpr> exprJson = new ArrayList<>();
            exprJson.add(objectMapper.readValue(json, BaseExpr.class));
            String actualResult = RuleExprConverter.convertJsonExprToString(exprJson);
            String expectedResult = "((productNumber == \"111\") || (productNumber == \"222\") || (productNumber == \"4e5ff\"))";
            assertEquals(expectedResult, actualResult);
        }



        /**
         * Test: rule expression converter for "Price".
         * @throws IOException while parsing JSON.
         */
        @Test
        @DisplayName("test for price")
        public void testForPrice() throws IOException {
            String json = "{\n" +
                    "  \"type\": \"Price\",\n" +
                    "  \"condition\": \"AND\",\n" +
                    "  \"operator\": \"eq\",\n" +
                    "  \"value\": {\n" +
                    "    \"price\": 23.45\n" +
                    "  }\n" +
                    "}";

            List<BaseExpr> exprJson = new ArrayList<>();
            exprJson.add(objectMapper.readValue(json, BaseExpr.class));
            String actualResult = RuleExprConverter.convertJsonExprToString(exprJson);
            String expectedResult = "(regularPrice == 23.45)";
            assertEquals(expectedResult, actualResult);
        }
    }
}