package com.tracktec.tracktectest.controller;

import com.tracktec.tracktectest.service.TrackTecService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Devuelve un valor codificado")
    @GetMapping("decodeIbutton")
    @ResponseBody
    public String getDecodedIbutton (@RequestParam("ibuttonValue") String ibuttonValue) {
        return trackTecService.decodeIbuttonValue(ibuttonValue);
    }

    @Operation(summary = "Indica si la palabra es palindroma")
    @GetMapping("isPallindrome")
    @ResponseBody
    public Boolean getIsPallindrome(@RequestParam("value") String value) {
        return trackTecService.isPalindrome(value);
    }

    @Operation(summary = "Devuelve los dias que han transcurrido desde la fecha ingresada")
    @GetMapping("birthdate")
    public Long birthdate(@RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate) {
        return trackTecService.daysBetween(birthdate);
    }

    @Operation(summary = "Devuelve una fecha desde una cadena String")
    @GetMapping("dateStringFromChain")
    public String getSentDateStringFromChain(@RequestParam("chain") String chain) {
        return trackTecService.getSentDateStringFromChain(chain);
    }

    @Operation(summary = "Devuelve un IMEI codificado")
    @GetMapping("encodeIMEI")
    public String encodeIMEI(@RequestParam("imei") String imei) {
        return trackTecService.encodeIMEI(imei);
    }
}
