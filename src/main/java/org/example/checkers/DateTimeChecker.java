package org.example.checkers;

import org.example.FlatLoginHistory;
import org.example.loger.LogTransformation;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DateTimeChecker implements Checker{

    private Timestamp tsFromDT(String datetime) throws ParseException {
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return new Timestamp(datetimeFormatter1.parse(datetime).getTime());
    };

    @Override
    @LogTransformation(logfile = "C:\\MyDocuments\\IdeaProjects\\JavaPractic\\Task_4\\log\\dateTime.txt")
    public FlatLoginHistory apply(FlatLoginHistory line) {
        List<String> fields  =line.getAuthorizations();
        Timestamp ts;
        if(fields==null)
            throw new IllegalArgumentException("FlatLoginHistory is empty");
        String dateStr =fields.get(0);
        String timeStr =fields.get(1);
        if(dateStr.isEmpty() || timeStr.isEmpty()) {
            System.out.println("Error when parse");//TODO write to error file
            return null;
        }
        try {
            ts = tsFromDT(dateStr + " " + timeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(!ts.equals(null) && !ts.before(new Date())) {
            System.out.println("Error when parse");//TODO write to error file
            return null;
        }
        line.setAccess_date(ts);
        return line;
    }
}
