package org.example.checkers;

import org.example.FlatLoginHistory;
import org.example.loger.LogTransformation;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FioChecker implements Checker {

   @Override
   @LogTransformation
    public FlatLoginHistory apply(FlatLoginHistory line) {
       List<String> fields  =line.getAuthorizations();
       if(fields==null)
           throw new IllegalArgumentException("FlatLoginHistory is empty");
       String tmp = fields.get(3).replaceAll("\"","");
       String[] fio = tmp.split(" ");
       if(fio.length <3) {
           System.out.println("Error when parse: Это не ФИО или оно не полное : " + tmp);//TODO write to error file
           return null;
       }
       line.setFio(String.join(" ",fio));
       return line;
    }
}
