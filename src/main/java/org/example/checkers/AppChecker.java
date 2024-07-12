package org.example.checkers;

import org.example.FlatLoginHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AppChecker implements Checker{

    public AppChecker(@Value("${task4.dataconverter.validAppValues}") List<String> validValues) {
        this.validValues = validValues;
    }

    private final List<String> validValues;

    @Override
    public FlatLoginHistory apply(FlatLoginHistory line) {
        List<String> fields  =line.getAuthorizations();
        if(fields==null) {
            throw new IllegalArgumentException("FlatLoginHistory is empty OR not fields are filled in");
        }
        if(fields.size()>5) {
            System.out.println("Where App column ?");//TODO write to error file
            return null;
        }

        String appStr = fields.get(4);
        if (appStr.isBlank()){
            System.out.println("Error when parse");//TODO write to error file
            return null;
        }
        if(validValues.contains(appStr)){
            line.setApplication(appStr);
        }
        else {
            line.setApplication("other");
        }
        return line;
    }
}
