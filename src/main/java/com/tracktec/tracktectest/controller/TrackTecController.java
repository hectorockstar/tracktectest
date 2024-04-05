package com.tracktec.tracktectest.controller;

import com.tracktec.tracktectest.service.TrackTecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TrackTecController {

    private final TrackTecService trackTecService;

    @Autowired
    public TrackTecController(TrackTecService trackTecService) {
        this.trackTecService = trackTecService;
    }

    @GetMapping("decodeIbutton")
    @ResponseBody
    public String getDecodedIbutton (@RequestParam("ibuttonValue") String ibuttonValue) {
        return trackTecService.decodeIbuttonValue(ibuttonValue);
    }

    @GetMapping("isPallindrome")
    @ResponseBody
    public Boolean getIsPallindrome(@RequestParam("value") String value) {
        return trackTecService.isPalindrome(value);
    }

    @GetMapping("birthdate")
    public Long birthdate(@RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate) {
        return trackTecService.daysBetween(birthdate);
    }

    @GetMapping("dateStringFromChain")
    public String getSentDateStringFromChain(@RequestParam("chain") String chain) {
        return trackTecService.getSentDateStringFromChain(chain);
    }

    @GetMapping("encodeIMEI")
    public String encodeIMEI(@RequestParam("imei") String imei) {
        return trackTecService.encodeIMEI(imei);
    }
}
