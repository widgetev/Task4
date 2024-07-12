package org.example.db.repositories;

import org.example.db.LoginsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginsRepo extends JpaRepository<LoginsHistory,Long> {
}
