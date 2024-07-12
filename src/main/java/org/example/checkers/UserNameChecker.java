package org.example.checkers;

import org.example.FlatLoginHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserNameChecker implements Checker{
    @Override
    public FlatLoginHistory apply(FlatLoginHistory line) {
        List<String> fields  =line.getAuthorizations();
        if(fields==null)
            throw new IllegalArgumentException("FlatLoginHistory is empty");
        line.setUsername(fields.get(2));
        return line;
    }
}
