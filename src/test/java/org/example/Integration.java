package org.example;

import static org.junit.Assert.assertTrue;

import org.example.db.repositories.LoginsRepo;
import org.example.db.repositories.UsersRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DataJpaTest
public class Integration {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private LoginsRepo loginsRepo;

    @Autowired
    private List<FlatLoginHistory> flatLoginHistoryList;

    @Autowired
    private DataReader dataReader;

    @Test
    public void readData(){
        Assertions.assertDoesNotThrow(()->{flatLoginHistoryList = dataReader.get();});
    }
}
