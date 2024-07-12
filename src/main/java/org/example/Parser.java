package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.checkers.Checker;
import org.example.db.LoginsHistory;
import org.example.db.Users;
import org.example.loger.LogTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Parser{

    @Autowired
    private List<Checker> checkers;
    @Value("${task4.dataconverter.fieldDelimiterRegExp}")
    String delimiter;
    private List<FlatLoginHistory> flatHistoryList = new ArrayList<>();
    List<FlatLoginHistory> parse(List<String> lines){
        String[] splitLine;

        for (String line: lines) {
            if(line.isBlank()) continue;
            line=line.substring(1,line.length()-1);
            splitLine = line.split(delimiter);
            FlatLoginHistory flatHistory = new FlatLoginHistory(Arrays.asList(splitLine));
            for (Checker cheker: checkers) {
                flatHistory = cheker.apply(flatHistory);
            }
            if(flatHistory!=null)
                flatHistoryList.add(flatHistory);
            else
                System.out.println("Empty line : " + line);
        }
        return flatHistoryList;
    }
}
