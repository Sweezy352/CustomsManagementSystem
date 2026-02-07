package com.example.sweezcustoms.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class BusinessIdentityGenerator {

    public String generateTin(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("2");
        stringBuilder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy")));
        stringBuilder.append(String.format("%5d", new Random().nextInt(100000)));
        return stringBuilder.toString();
    }

    public String generateOkpo(){
        return String.format("%8d", new Random().nextInt(100000000));
    }

    public String generateCustomsCode(String tin){
        return "KG-" + tin + "-SWZ";
    }
}
