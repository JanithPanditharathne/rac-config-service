package com.zone24x7.rac.configservice.rule.expression;

import com.zone24x7.rac.configservice.rule.expression.brand.BrandExpr;
import com.zone24x7.rac.configservice.rule.expression.price.PriceExpr;
import com.zone24x7.rac.configservice.rule.expression.productnumber.ProductNumberExpr;
import com.zone24x7.rac.configservice.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RuleExprConverter {

    private static final String OPERATOR_OR = " || ";
    private static final Integer OPERATOR_CHAR_COUNT = 4;

    /**
     * Private constructor.
     *
     */
    private RuleExprConverter() {
        throw new IllegalStateException("RuleExprConverter");
    }

    /**
     * Convert given rule expression json list to a single expression string.
     *
     * @param ruleExprList Rule expression json list
     * @return  Converted expression string
     */
    public static String convertJsonExprToString(Iterable<BaseExpr> ruleExprList) {

        // Result string.
        StringBuilder exprString = new StringBuilder();

        // Build single expression string for each rule expression.
        ruleExprList.forEach(expr -> buildExpression(expr, exprString));

        // Remove the ending operator character.
        if (exprString.length() > OPERATOR_CHAR_COUNT) {
            exprString.replace(exprString.length() - OPERATOR_CHAR_COUNT, exprString.length(), "");
        }

        // Return the expression.
        return exprString.toString();
    }


    /**
     * Build the rule expression based on the details given by BaseRuleExprDTO.
     *
     * @param baseExpr Rule expression
     * @param exprString  String builder that use to build the expression
     */
    private static void buildExpression(BaseExpr baseExpr, StringBuilder exprString) {

        // Get condition symbol.
        String condition = getConditionSymbol(baseExpr.getCondition());

        // Get rule expression type.
        String exprType = baseExpr.getType();

        // Type = brand.
        if (Strings.BRAND.equals(exprType)) {

            // Get brand expression.
            exprString.append(getBrandExpression(baseExpr));



            // Type = product number.
        } else if (Strings.PRODUCT_NUMBER.equals(exprType)) {

            // Get product number expression.
            exprString.append(getProductNumberExpression(baseExpr));



            // Type: price.
        } else if (Strings.PRICE.equals(exprType)) {

            PriceExpr priceExpr = (PriceExpr)baseExpr;

            // eg: "(price <= 2222)"
            exprString.append(getExpr(Strings.RE_PRICE,
                    getOperatorSign(priceExpr.getValue().getOperator()),
                    String.valueOf(priceExpr.getValue().getPrice())));

            // condition: "&&" or "||"
            exprString.append(condition);

        }




    }



    /**
     * Return condition symbol.
     *
     * @param conditionText Condition value as text. I.e. "AND" or "OR".
     * @return matching symbol. "&&" or "||"
     */
    private static String getConditionSymbol(String conditionText) {
        String condition = " ?? ";
        if(Strings.AND.equals(conditionText)) {
            condition = " && ";
        } else if(Strings.OR.equals(conditionText)) {
            condition = " || ";
        }
        return condition;
    }


    /**
     * Return expression.
     * Eg: (departmentName = Clothes)
     *
     * @param name key name.
     * @param value  key value.
     * @return expression string.
     */
    private static String getExpr(String name, String value) {
        return "(" + name + " = \"" + value + "\")";
    }


    /**
     * Return expression.
     * Eg: (departmentName = Clothes)
     *
     * @param name key name.
     * @param operator operator.
     * @param value key value.
     * @return expression string.
     */
    private static String getExpr(String name, String operator, String value) {
        return "(" + name + " " + operator + " " + value + ")";
    }





    /**
     * Get brand expression.
     * Eg:
     * Single: (brandName = "AAA")
     * Multiple: ((brandName = "AAA") || (brandName = "BBB") || (brandName = "CCC"))
     *
     * @param baseExpr rule expression.
     * @return brand expression.
     */
    private static String getBrandExpression(BaseExpr baseExpr) {

        StringBuilder exprString = new StringBuilder();
        BrandExpr brandExpr = (BrandExpr)baseExpr;

        // Add brands to the list.
        List<String> brandList = new ArrayList<>();
        brandExpr.getValue().forEach(b -> brandList.add(getExpr(Strings.RE_BRAND, b.getName())));

        if(!brandList.isEmpty()) {

            // Join the list.
            exprString.append(joinStringList(brandList, OPERATOR_OR));

            // Add condition: "&&" or "||"
            exprString.append(getConditionSymbol(baseExpr.getCondition()));

        }

        return exprString.toString();
    }


    /**
     * Get product number expression.
     * Eg:
     * Single: (productNumber = "111")
     * Multiple: ((productNumber = "111") || (productNumber = "222") || (productNumber = "4e5ff"))
     *
     * @param baseExpr rule expression.
     * @return product number expression.
     */
    private static String getProductNumberExpression(BaseExpr baseExpr) {

        StringBuilder exprString = new StringBuilder();
        ProductNumberExpr productNumberExpr = (ProductNumberExpr)baseExpr;

        // Add product numbers to the list.
        List<String> productNumberList = new ArrayList<>();
        for (String productNumber : productNumberExpr.getValue()) {
            productNumberList.add(getExpr(Strings.RE_PRODUCT_NUMBER, productNumber));
        }

        if(!productNumberList.isEmpty()) {

            // Join the list.
            exprString.append(joinStringList(productNumberList, OPERATOR_OR));

            // Add condition: "&&" or "||"
            exprString.append(getConditionSymbol(baseExpr.getCondition()));

        }

        return exprString.toString();
    }















    /**
     * Join the given list using the given delimiter.
     *
     * @param list list of string use to build the expression.
     * @param delimiter joining operator.
     */
    private static String joinStringList(List<String> list, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        if(list.size() == 1) {
            stringBuilder.append(list.get(0));

        } else {
            stringBuilder.append("(");
            stringBuilder.append(String.join(delimiter, list));
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }






    /**
     * Determine the operator.
     *
     * @param sign The operator string
     * @return     The operator sign
     */
    private static String getOperatorSign(String sign) {

        // Set output result based on the http status code.
        Map<String, String> map = new HashMap<>();

        // Equals.
        map.put(Strings.EQ, "=");

        // Greater than.
        map.put(Strings.GT, ">");

        // Greater than or equals to.
        map.put(Strings.GTEQ, ">=");

        // Less than.
        map.put(Strings.LT, "<");

        // Less than or equals to.
        map.put(Strings.LTEQ, "<=");

        // Return result.
        return map.get(sign);

    }





}