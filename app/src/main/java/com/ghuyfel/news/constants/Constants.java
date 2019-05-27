package com.ghuyfel.news.constants;

public class Constants {

    public static final String PATTERN_APP_DATE = "yyyy-MM-dd";

    public static final String KEY_NEWS = "news";
    public static final String KEY_BUNDLE =  "bundle";
    
    public static final String MIMETYPE_HTML = "text/html; charset=UTF-8";
    public static final String BLANK_URL = "about:blank";

    //Api

    public static String BASE_URL = "http://ipadfeed.supersport.com/";
    public static String FORMAT_JSON = "json";

    //Errors

    public static final String UNKNOWN_ERROR = "Something went wrong.\nPlease try again later." ;
    public static final String ERROR_NOT_FOUND_MESSAGE =  "Something went wrong.\nPage requested not found." ;
    public static final String ERROR_SERVER_ERROR_MESSAGE =  "Something went wrong.\nThe server is not available\nat the moment.\nPlease try again later." ;

    // Server error codes
    public static final int ERROR_SERVER_ERROR = 500;
    public static final int ERROR_NOT_FOUND = 404;
    public static final int SUCCEEDED = 200;

    // Others
    public static final String EMPTY_STRING = "" ;
}
