package com.zone24x7.rac.configservice.util;

public final class Strings {

    private Strings() {
    }

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String ERROR = "ERROR";
    public static final String BUNDLES = "bundles";
    public static final String RULES = "rules";
    public static final String RECS = "recs";
    public static final String REC_SLOTS = "recSlots";
    public static final String CHANNELS = "channels";
    public static final String PAGES = "pages";
    public static final String PLACEHOLDERS = "placeholders";
    public static final String BRAND = "Brand";
    public static final String PRODUCT_NUMBER = "ProductNumber";
    public static final String PRICE = "Price";
    public static final String CUSTOM = "Custom";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String RE_BRAND = "brand";
    public static final String RE_PRODUCT_NUMBER = "productNumber";
    public static final String RE_PRODUCT_ID = "productId";
    public static final String RE_PRICE = "regularPrice";
    public static final String EQ = "eq";
    public static final String EQIC = "eqic";
    public static final String LT = "lt";
    public static final String GT = "gt";
    public static final String LTEQ = "lteq";
    public static final String GTEQ = "gteq";
    public static final String BOOST = "BOOST";
    public static final String BURY = "BURY";
    public static final String ONLY_RECOMMEND = "ONLY_RECOMMEND";
    public static final String DO_NOT_RECOMMEND = "DO_NOT_RECOMMEND";
    public static final String REQUEST_ID = "Request-ID";
    public static final String METHOD = "method";
    public static final String URI = "uri";
    public static final String ORIGIN = "origin";
    public static final String USER = "user";
    public static final String START_TIME = "startTime";









    // -------------------------------------------------------
    //
    // Response messages.
    //
    // -------------------------------------------------------
    // Algorithms
    public static final String ALGORITHM_ID_INVALID = "CS-1000:Invalid algorithm id";
    public static final String ALGORITHM_ADDED_SUCCESSFULLY = "CS-1001:Algorithm added successfully";
    public static final String ALGORITHM_UPDATED_SUCCESSFULLY = "CS-1002:Algorithm updated successfully";
    public static final String ALGORITHM_ID_ALREADY_EXISTS = "CS-1003:Algorithm id already exists";
    public static final String ALGORITHM_NAME_CANNOT_BE_NULL = "CS-1004:Algorithm name cannot be null";
    public static final String ALGORITHM_NAME_CANNOT_BE_EMPTY = "CS-1005:Algorithm name cannot be empty";
    public static final String ALGORITHM_DELETED_SUCCESSFULLY = "CS-1006:Algorithm deleted successfully";
    public static final String ALGORITHM_ID_ALREADY_USE_IN_BUNDLES = "CS-1007:Algorithm id already use in bundles";
    public static final String ALGORITHM_ID_DOES_NOT_EXIST = "CS-1008:Algorithm id does not exist";

    // Bundles
    public static final String BUNDLE_ID_INVALID = "CS-2000:Invalid bundle id";
    public static final String BUNDLE_ADDED_SUCCESSFULLY = "CS-2001:Bundle added successfully";
    public static final String BUNDLE_NAME_CANNOT_BE_NULL = "CS-2002:Bundle name cannot be null";
    public static final String BUNDLE_NAME_CANNOT_BE_EMPTY = "CS-2003:Bundle name cannot be empty";
    public static final String BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL = "CS-2004:Bundle combine display text cannot be null";
    public static final String BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY = "CS-2005:Bundle combine display text cannot be empty";
    public static final String ALGORITHMS_CANNOT_BE_NULL = "CS-2006:Bundle algorithms cannot be null";
    public static final String ALGORITHMS_CANNOT_BE_EMPTY = "CS-2007:Bundle algorithms cannot be empty";
    public static final String BUNDLE_UPDATED_SUCCESSFULLY = "CS-2008:Bundle updated successfully";
    public static final String BUNDLE_DELETED_SUCCESSFULLY = "CS-2009:Bundle deleted successfully";
    public static final String BUNDLE_ID_ALREADY_USE_IN_RECS = "CS-2010:Bundle id already use in recs";
    public static final String BUNDLE_DEFAULT_LIMIT_CANNOT_BE_NEGATIVE = "CS-2011:Bundle default limit cannot be negative";


    // Rules
    public static final String RULE_ID_INVALID = "CS-3000:Invalid rule id";
    public static final String RULE_ADDED_SUCCESSFULLY = "CS-3001:Rule added successfully";
    public static final String RULE_NAME_CANNOT_BE_NULL = "CS-3002:Rule name cannot be null";
    public static final String RULE_NAME_CANNOT_BE_EMPTY = "CS-3003:Rule name cannot be empty";
    public static final String RULE_TYPE_CANNOT_BE_NULL = "CS-3004:Rule type cannot be null";
    public static final String RULE_TYPE_CANNOT_BE_EMPTY = "CS-3005:Rule type cannot be empty";
    public static final String RULE_TYPE_INVALID = "CS-3006:Rule type is invalid (value should be either BOOST, BURY, ONLY_RECOMMEND or DO_NOT_RECOMMEND)";
    public static final String RULE_MATCHING_CONDITION_JSON_CANNOT_BE_NULL = "CS-3007:Rule matching condition JSON cannot be null";
    public static final String RULE_DELETED_SUCCESSFULLY = "CS-3008:Rule deleted successfully";
    public static final String RULE_ACTION_CONDITION_JSON_CANNOT_BE_NULL = "CS-3009:Rule action condition JSON cannot be null";
    public static final String RULE_ACTION_CONDITION_JSON_CANNOT_BE_EMPTY = "CS-3010:Rule action condition JSON cannot be empty";
    public static final String RULE_UPDATED_SUCCESSFULLY = "CS-3011:Rule updated successfully";
    public static final String RULE_MATCHING_CONDITION_JSON_CONDITION_VALUE_CANNOT_BE_NULL = "CS-3012:Rule matching condition JSON condition value cannot be null";
    public static final String RULE_MATCHING_CONDITION_JSON_CONDITION_VALUE_INVALID = "CS-3013:Rule matching condition JSON condition value invalid (value should be either AND or OR)";
    public static final String RULE_MATCHING_CONDITION_JSON_OPERATOR_VALUE_CANNOT_BE_NULL = "CS-3014:Rule matching condition JSON operator value cannot be null";
    public static final String RULE_MATCHING_CONDITION_JSON_OPERATOR_VALUE_INVALID = "CS-3015:Rule matching condition JSON operator value invalid (value should be either eq, eqic, lt, lteq, gt, or gteq)";
    public static final String RULE_ACTION_CONDITION_JSON_CONDITION_VALUE_CANNOT_BE_NULL = "CS-3016:Rule action condition JSON condition value cannot be null";
    public static final String RULE_ACTION_CONDITION_JSON_CONDITION_VALUE_INVALID = "CS-3017:Rule action condition JSON condition value invalid (value should be either AND or OR)";
    public static final String RULE_ACTION_CONDITION_JSON_OPERATOR_VALUE_CANNOT_BE_NULL = "CS-3018:Rule action condition JSON operator value cannot be null";
    public static final String RULE_ACTION_CONDITION_JSON_OPERATOR_VALUE_INVALID = "CS-3019:Rule action condition JSON operator value invalid (value should be either eq, eqic, lt, lteq, gt, or gteq)";



    // Rec
    public static final String REC_ID_INVALID = "CS-4000:Invalid rec id";
    public static final String REC_ADD_SUCCESS = "CS-4001:Rec added successfully";
    public static final String REC_NAME_CANNOT_BE_NULL = "CS-4002:Rec name cannot be null";
    public static final String REC_NAME_CANNOT_BE_EMPTY = "CS-4003:Rec name cannot be empty";
    public static final String REC_UPDATED_SUCCESSFULLY = "CS-4004:Rec updated successfully";
    public static final String REC_BUNDLE_DETAILS_NOT_FOUND = "CS-4005:Rec bundle details not found";
    public static final String REC_DELETED_SUCCESSFULLY = "CS-4006:Rec deleted successfully";
    public static final String REC_ID_ALREADY_USE_IN_REC_SLOTS = "CS-4007:Rec id already use in rec slots";


    // Rec slot
    public static final String REC_SLOT_ID_INVALID = "CS-5000:Invalid rec slot id";
    public static final String REC_SLOT_ADDED_SUCCESSFULLY = "CS-5001:Rec slot added successfully";
    public static final String REC_CANNOT_BE_NULL = "CS-5002:Rec cannot be null";
    public static final String REC_SLOT_UPDATED_SUCCESSFULLY = "CS-5003:Rec slot updated successfully";
    public static final String REC_SLOT_DELETED_SUCCESSFULLY = "CS-5004:Rec slot deleted successfully";
    public static final String SIMILAR_REC_SLOT_ALREADY_EXISTS = "CS-5005:Similar rec slot already exists with given channel, page, placeholder values. " +
            "Please specify different channel, page, placeholder combination.";
    public static final String REC_SLOT_RULES_CANNOT_BE_NULL = "CS-5006:Rec slot rules field cannot be null";

    // Metadata
    public static final String METADATA_NAME_CANNOT_BE_NULL = "CS-6000:Metadata name cannot be null";
    public static final String METADATA_NAME_CANNOT_BE_EMPTY = "CS-6001:Metadata name cannot be empty";
    public static final String METADATA_TYPE_CANNOT_BE_NULL = "CS-6002:Metadata type cannot be null";
    public static final String METADATA_ADDED_SUCCESSFULLY = "CS-6003:Metadata added successfully";
    public static final String CHANNEL_ID_INVALID = "CS-6004:Invalid channel id";
    public static final String CHANNEL_CANNOT_BE_NULL = "CS-6005:Channel cannot be null";
    public static final String METADATA_TYPE_CANNOT_BE_EMPTY = "CS-6006:Metadata type cannot be empty";
    public static final String PAGE_ID_INVALID = "CS-6007:Invalid page id";
    public static final String PAGE_CANNOT_BE_NULL = "CS-6008:Page cannot be null";
    public static final String PLACEHOLDER_ID_INVALID = "CS-6009:Invalid placeholder id";
    public static final String PLACEHOLDER_CANNOT_BE_NULL = "CS-6010:Placeholder cannot be null";
    public static final String SIMILAR_METADATA_ALREADY_EXISTS = "CS-6011:Similar metadata already exists";



    // Rec Engine
    public static final String REC_ENGINE_CONFIG_UPDATE_PROCESS_STARTED = "CS-7000:Rec engine config update process started";
    public static final String REC_ENGINE_BUNDLE_CONFIG_UPDATE_FAILED = "CS-7002:Rec engine bundle config update failed";
    public static final String REC_ENGINE_RULE_CONFIG_UPDATE_FAILED = "CS-7003:Rec engine rule config update failed";
    public static final String REC_ENGINE_REC_CONFIG_UPDATE_FAILED = "CS-7004:Rec engine rec config update failed";
    public static final String REC_ENGINE_REC_SLOT_CONFIG_UPDATE_FAILED = "CS-7005:Rec engine rec slot config update failed";
}
