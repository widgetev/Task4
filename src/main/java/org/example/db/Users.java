package org.example.db;

import jakarta.persistence.*;
import lombok.*;

@Setter @Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
//@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Users(String username, String fio) {
        this.username = username;
        this.fio = fio;
    }

    @Column(name = "username")
    private String username;
    @Column(name = "fio")
    private String fio;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                '}';
    }
}
