package com.tracktec.tracktectest.constants;

import java.util.HashMap;
import java.util.Map;

public class TrackTecConstants {

    public static final Map<String, String> CHAR_MAPPING_FOR_IMEI;

    static {
        Map<String, String> charMappingForIMEI = new HashMap<>();
        charMappingForIMEI.put("A", "1");
        charMappingForIMEI.put("B", "2");
        charMappingForIMEI.put("C", "3");
        charMappingForIMEI.put("D", "4");
        charMappingForIMEI.put("E", "5");
        charMappingForIMEI.put("F", "6");
        charMappingForIMEI.put("G", "7");
        charMappingForIMEI.put("J", "10");
        charMappingForIMEI.put("K", "11");
        charMappingForIMEI.put("L", "12");
        charMappingForIMEI.put("M", "13");
        charMappingForIMEI.put("N", "14");
        charMappingForIMEI.put("O", "15");
        charMappingForIMEI.put("P", "16");
        CHAR_MAPPING_FOR_IMEI = charMappingForIMEI;
    }


}
