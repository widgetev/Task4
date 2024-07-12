package org.example.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Logins extends JpaRepository<Logins,Long> {
}
