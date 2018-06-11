package com.example.govin.cameraex;

/**
 * Created by govin on 11-06-2018.
 */

public interface AppConstants {

    String WEB_IMAGE = "http";

    interface RequestCodes {
        int TAKE_PHOTO = 100;
        int PICK_FROM_GALLERY = 101;
        int SEND_IMAGE_PATH = 102;
        int FORGOT_PASSWORD = 104;
        int CHANGE_EMAIL = 110;
        int TERMS_AND_CONDITIONS = 111;
        int ADDRESS_LOCATION = 112;
        int USER_PROFILE_SCAN = 113;
        int PRODUCT_WARRANTY_SCAN = 114;
        int PRODUCT_ASSIGN_SCAN = 115;
        int SERIAL_NO_SCAN = 116;
        int BATCH_NO_SCAN = 117;
    }

    interface IntentConstants {
        String SCANNED_TITLE = "scannedTitle";
        String SCANNED_CODE = "scanedCode";
        String USER_PHONE_NUMBER = "userPhoneNumber";
        String IMAGE_PATH = "imagePath";
        String ADDRESS_COMMA = "addressDetails";
        String LOCATION_COMMA = "locationDetails";
        String FROM_FORGOT_PASSWORD_SCREEN = "fromForgotPasswordScreen";
    }
}
