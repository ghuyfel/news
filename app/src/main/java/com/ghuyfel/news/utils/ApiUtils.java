package com.ghuyfel.news.utils;

import com.ghuyfel.news.R;
import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.constants.Constants;

public class ApiUtils {


    public static ApiErrorMessage getDescriptionForErrorCode(int errorCode) {
        ApiErrorMessage message;
        switch (errorCode) {
            case Constants.ERROR_NOT_FOUND:
                message = new ApiErrorMessage(errorCode, Constants.ERROR_NOT_FOUND_MESSAGE);
                break;
            case Constants.ERROR_SERVER_ERROR:
                message = new ApiErrorMessage(errorCode, Constants.ERROR_SERVER_ERROR_MESSAGE);
                break;
            default:
                message = new ApiErrorMessage(errorCode, Constants.UNKNOWN_ERROR);
                break;
        }
        return message;
    }

    public static int getImageForErrorCode(int errorCode) {
        switch (errorCode) {
            case Constants.ERROR_NOT_FOUND:
                return R.mipmap.ic_not_found;
            case Constants.ERROR_SERVER_ERROR:
                return R.mipmap.ic_server_error;
            default:
                return R.mipmap.ic_unkown_error;
        }
    }
}
