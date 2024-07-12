package org.example.db;

import org.example.FlatLoginHistory;
import org.example.db.repositories.LoginsRepo;
import org.example.db.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter {

    UsersRepo usersRepo;

    LoginsRepo loginsRepo;

    public DBWriter(@Autowired UsersRepo usersRepo, @Autowired LoginsRepo loginsRepo) {
        this.usersRepo = usersRepo;
        this.loginsRepo = loginsRepo;
    }

    public void write(List<FlatLoginHistory> flatLoginHistoryList) {

        for (FlatLoginHistory flatLoginHistory:flatLoginHistoryList ){
            Users user = usersRepo.findByUsername(flatLoginHistory.getUsername());
            if (user ==null)
                user = new Users(flatLoginHistory.getUsername(), flatLoginHistory.getFio());
            user = usersRepo.save(user);

            LoginsHistory loginsHistory = new LoginsHistory(user
                    ,flatLoginHistory.getAccess_date()
                    ,flatLoginHistory.getApplication());

            loginsRepo.save(loginsHistory);
        }
    }

}
