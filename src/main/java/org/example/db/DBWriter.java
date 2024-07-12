package org.example.db.model;

import org.example.db.repositories.LoginsRepo;
import org.example.db.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DBWriter {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    LoginsRepo loginsRepo;

    public void write(Map<Users, List<LoginsHistory>>  loginsHistoryMap) {
        Users savedUser;
        for (Users user: loginsHistoryMap.keySet()) {
            savedUser = usersRepo.save(user);
            loginsRepo.saveAll(loginsHistoryMap.get(user));
        }
    }

}
