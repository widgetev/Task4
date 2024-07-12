package org.example.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "loginsHistory")
public class LoginsHistory  {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    @Column(name = "access_date")
    private Timestamp access_date;

    @Column(name = "application")
    private String application;

    @Override
    public String toString() {
        return  id +
                " Login at " + access_date +
                ", by " + user.fio +
                " <" + user.username + ">" + " (" + user.id + ") "+
                ", from '" + application + '\'' +
                '}';
    }
}
