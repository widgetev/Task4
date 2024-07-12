package org.example.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@ToString
public class FlatLoginHistory{

    private List<String> authorizations;
    private String id;
    private String username;
    private String application;
    private Timestamp access_date;

}
