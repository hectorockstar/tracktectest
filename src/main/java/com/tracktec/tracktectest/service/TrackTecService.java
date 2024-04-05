package com.tracktec.tracktectest.service;

import com.tracktec.tracktectest.constants.TrackTecConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TrackTecService {

    /*
        1 - Decodificar Valor de Ibutton
            En el área de servicios GPS se utiliza un sistema de identificación de conductores
            también conocido como Ibutton o llave Dallas. Esta pregunta consiste en aplicar un
            procesamiento de texto sobre un valor dado. El procesamiento consiste en invertir la
            cadena de texto en pares en donde el primer par debe quedar como el ultimo par y el
            segundo par como el penúltimo y así sucesivamente hasta completar la cadena.
            Valor original = E9690F170000
            Valor esperado = 0000170F69E9

            Reglas:
                - No se puede usar ciclos para recorrer la cadena.
     */
    public String decodeIbuttonValue(String ibuttonValue) {
        String[] splittedValue = ibuttonValue.split("(?<=\\G.{2})");
        List<String> indexesValueList = new ArrayList<>(List.of(splittedValue));
        Collections.reverse(indexesValueList);
        return indexesValueList.stream().collect(Collectors.joining(""));
    }

    /*
        2 - Validar cadena de texto según regla
            En el área de servicios GPS se con mucha frecuencia el concepto de “parser” y
            que en muchos casos hace referencia a transformar una cadena y evaluar si cumple o no
            con ciertas características. Basándonos en esta premisa se plantea el siguiente ejercicio.
            Evaluar si una palabra es palíndromo, devolviendo verdadero o falso según sea el caso.

            Reglas:
                - Utilizar programación declarativa y sólo una línea de código en el interior del
            método
     */
    public Boolean isPalindrome(String value) {
        return value.equalsIgnoreCase(reversedStringValue(value));
    }

    private String reversedStringValue(String value){
        String[] splittedValue = value.toLowerCase().split("");
        List<String> indexesValueList = new ArrayList<>(List.of(splittedValue));
        Collections.reverse(indexesValueList);

        return indexesValueList.stream().collect(Collectors.joining(""));
    }


    /*
        3 - Calcular días entre dos fechas
            Continuando con el área de servicios GPS, es muy utilizado y condicionante el
            buen manejo de las fechas y tipos de fecha en Java. Debido a esto se plantea el siguiente
            problema: Calcular cuántos días han pasado desde su fecha de nacimiento.

            Reglas:
            - No se pueden usar operadores matemáticos.
            - Programación declarativa.
            - Una sola línea de código.
     */
    public Long daysBetween(Date birthdate) {
        return DAYS.between(dateToLocalDateFormat(birthdate), LocalDate.now());
    }

    private LocalDate dateToLocalDateFormat(Date birthdate) {
        return birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /*
        4 - Extraer datos de una cadena
            Volviendo al concepto de “Parser”, es necesario extraer una fecha desde una
            cadena de datos GPS correspondiente a la marca Quecklink, esta fecha es la última
            presente en cada cadena y tiene el siguiente formato: 20240403183055

            Cadenas con las que debe resolver el problema:
                +RESP:GTFRI,270703,862165555592548,,,10,1,1,45.8,184,816.7,-70.588968,-32.856467,20240403183042,0730,0001,1584,1FDDE8C,00,149011.2,10611:43:49,,,85,220100,,,,20240403183055,F2EF$

                +RESP:GTFRI,270703,862165555592548,,,10,1,1,45.8,184,816.7,-70.588968,-32.856467,20240403183042,0730,0001,1584,1FDDE8C,00,149011.2,10611:43:49,,,85,220100,,,20240403183056,,F2EF$

                +RESP:GTFRI,270703,862165555592548,,,10,1,1,45.8,184,816.7,-70.588968,-32.856467,20240403183042,0730,0001,1584,1FDDE8C,00,149011.2,10611:43:49,,,85,220100,,20240403183057,,,F2EF$

            El dato requerido es el que se encuentra en color rojo.

            Reglas:
                - Debe ser dinámico puesto que el valor requerido no siempre se encuentra en la
                misma posición.
                - Programación declarativa.
     */
    public String getSentDateStringFromChain(String chain){
        try {
            return this.getDateFromStringChain(chain);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDateFromStringChain(String chain) throws ParseException {
        String regex = ".*,(\\d{14}),";
        Matcher m = Pattern.compile(regex).matcher(chain);
        String strDate = "";
        if (m.find()) {
            m.toMatchResult();
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(m.group(1));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            strDate = dateFormat.format(date);
        } else {
            strDate = "No se encontró fecha";
        }
        return strDate;
    }

    /*
        5 - Codificar una cadena de datos
            Un IMEI es el identificador único del dispositivo GPS, pero hay ocasiones en que
            este Imei está expresado en letras y en Tracktec manejamos el IMEI con un estándar
            numérico. Es necesario transformar una cadena de texto (letras) en una cadena de texto
            numérica basándose en el valor numérico de cada letra en el abecedario, Además el valor
            restante deberá tener un largo de 15 números y de haber números faltantes se deberá
            completar con ceros a la izquierda.

            Ejemplo:
                Cadena con letras = “ABC” (IMEI original)
                Cadena resultante = “000000000000123” (IMEI codificado)

            Reglas:
                - Usar recursividad.
                - Programación declarativa.
     */
    public String encodeIMEI(String originalValue){
        return StringUtils.leftPad(this.encoderAction(originalValue), 15, "0");
    }

    private String encoderAction(String originalValue){
        List<String> indexesValueList = new ArrayList<>(List.of( originalValue.split("")));
        ListIterator<String> iterator = indexesValueList.listIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (TrackTecConstants.CHAR_MAPPING_FOR_IMEI.get(next) != null) {
                iterator.set(TrackTecConstants.CHAR_MAPPING_FOR_IMEI.get(next));
            }
        }

        return indexesValueList.stream().collect(Collectors.joining(""));
    }


}
