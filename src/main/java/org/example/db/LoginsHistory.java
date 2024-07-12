package org.example.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

//@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
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

    public LoginsHistory(Users user, Timestamp access_date, String application) {
        this.user = user;
        this.access_date = access_date;
        this.application = application;
    }

    @Column(name = "access_date")
    private Timestamp access_date;

    @Column(name = "application")
    private String application;

    @Override
    public String toString() {
        return  id +
                " Login at " + access_date +
                ", by " + user.getFio() +
                " <" + user.getUsername() + ">" + " (" + user.getId() + ") "+
                ", from '" + application + '\'' +
                '}';
    }
}
