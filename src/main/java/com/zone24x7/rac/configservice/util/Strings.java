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
    public static final String BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_NULL = "CS-2008:Bundle custom display text field is missing";
    public static final String BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_EMPTY = "CS-2009:Bundle custom display text cannot be empty";
    public static final String BUNDLE_UPDATED_SUCCESSFULLY = "CS-2010:Bundle updated successfully";
    public static final String BUNDLE_DELETED_SUCCESSFULLY = "CS-2011:Bundle deleted successfully";


    // Metadata
    public static final String CHANNEL_NAME_CANNOT_BE_NULL = "CS-6000:Channel name cannot be null";
    public static final String CHANNEL_NAME_CANNOT_BE_EMPTY = "CS-6001:Channel name cannot be empty";
    public static final String CHANNEL_NAME_ALREADY_EXISTS = "CS-6002:Channel name already exists";
    public static final String CHANNEL_ADDED_SUCCESSFULLY = "CS-6003:Channel added successfully";
    public static final String PAGE_NAME_CANNOT_BE_NULL = "CS-6004:Page name field cannot be null";
    public static final String PAGE_NAME_CANNOT_BE_EMPTY = "CS-6005:Page name field cannot be empty";
    public static final String PAGE_NAME_ALREADY_EXISTS = "CS-6006:Page name already exists";
    public static final String PAGE_ADDED_SUCCESSFULLY = "CS-6007:Page added successfully";
    public static final String PLACEHOLDER_NAME_CANNOT_BE_NULL = "CS-6008:Placeholder name cannot be null";
    public static final String PLACEHOLDER_NAME_CANNOT_BE_EMPTY = "CS-6009:Placeholder name cannot be empty";
    public static final String PLACEHOLDER_NAME_ALREADY_EXISTS = "CS-6010:Placeholder name already exists";
    public static final String PLACEHOLDER_ADDED_SUCCESSFULLY = "CS-6011:Placeholder added successfully";



    // Rec Engine
    public static final String REC_ENGINE_CONFIG_UPDATE_PROCESS_STARTED = "CS-7000:Rec engine config update process started";
    public static final String REC_ENGINE_BUNDLE_CONFIG_UPDATE_FAILED = "CS-7002:Rec engine bundle config update failed";
    public static final String REC_ENGINE_RULE_CONFIG_UPDATE_FAILED = "CS-7003:Rec engine rule config update failed";
    public static final String REC_ENGINE_REC_CONFIG_UPDATE_FAILED = "CS-7004:Rec engine rec config update failed";
    public static final String REC_ENGINE_REC_SLOT_CONFIG_UPDATE_FAILED = "CS-7005:Rec engine rec slot config update failed";


    // Recommendation
    public static final String REC_ID_INVALID = "CS-4000:Invalid recommendation id";
    public static final String REC_ADD_SUCCESS = "CS-4001:Recommendation added successfully";
    public static final String REC_NAME_CANN0T_BE_NULL = "CS-4002:Recommendation name field is missing";
    public static final String REC_NAME_CANN0T_BE_EMPTY = "CS-4003:Recommendation name cannot be empty";
}
