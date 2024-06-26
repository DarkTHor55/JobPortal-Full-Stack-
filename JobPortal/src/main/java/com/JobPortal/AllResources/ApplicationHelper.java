package com.JobPortal.AllResources;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

public class ApplicationHelper {
    public static String generateApplicationId() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString().toUpperCase();
    }

}
