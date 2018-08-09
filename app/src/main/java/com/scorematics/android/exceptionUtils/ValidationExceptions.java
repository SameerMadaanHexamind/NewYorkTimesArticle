package com.scorematics.android.exceptionUtils;

/**
 * Created by sameer.madaan on 7/13/2016.
 */

public class ValidationExceptions extends Exception {
   private String mMessage;
    private ValidationExceptions() {

    }

    public ValidationExceptions(String message) {
    mMessage = message;
    }

    public String getErrorMessage()
    {
        return mMessage;
    }
}
