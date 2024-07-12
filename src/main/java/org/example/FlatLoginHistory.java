package org.example;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlatLoginHistory{

    private List<String> authorizations;
    private String id;
    private String username;
    private String application;
    private String fio;
    private Timestamp access_date;

    public FlatLoginHistory(List<String> line) {
        this.authorizations = line;
    }


}
