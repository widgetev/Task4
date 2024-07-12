package org.example;

import static org.junit.Assert.assertTrue;

import org.example.db.DBWriter;
import org.example.db.Users;
import org.example.db.repositories.LoginsRepo;
import org.example.db.repositories.UsersRepo;
import org.example.DataReader;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class Integration {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");
       /*     .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");*/
    @Autowired
    private UsersRepo usersRepo;
    private LoginsRepo loginsRepo;

    private List<FlatLoginHistory> flatLoginHistoryList;

    @Autowired
    private DataReader datareader;

    @Autowired
    private Parser parser;
    @Autowired
    DBWriter dbWriter;
    List<String> lines;

    @Test
    public void readDataTest(){
        Assertions.assertDoesNotThrow(()->{lines = datareader.readFilesInDir();});
        Assertions.assertEquals(lines.get(0),"\"2024-05-15\";\"08:15:25\";\"user1\";\"Иванов иван иванович\";\"mobile\"");
    }

    @Test
    public void parserTest() throws IOException, ParseException, IllegalAccessException, NoSuchFieldException {
        //читать файлы
        List<String> textLines = datareader.readFilesInDir();

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        FlatLoginHistory flatLoginHistory = new FlatLoginHistory(
                new ArrayList<String>(List.of(new String[]{"2024-05-15", "08:16:25", "admin", "Сергеев сергей Сергеевич", "desktop"}))
                ,null
                ,"admin"
                ,"desktop"
                ,"Сергеев сергей Сергеевич"
                ,new Timestamp(format.parse("2024-05-15 08:16:25").getTime())
        );
        //распарсить на отдельные поля. С проверкой
        Assertions.assertDoesNotThrow(() -> {flatLoginHistoryList = parser.parse(textLines);});
        Field[] fields = flatLoginHistory.getClass().getDeclaredFields();
        for(Field field :fields){
            field.setAccessible(true);
            Object value = field.get(flatLoginHistory);
            FlatLoginHistory histor2= flatLoginHistoryList.get(1);
            Field field2 =histor2.getClass().getDeclaredField(field.getName());
            field2.setAccessible(true);
            Object value2 = field2.get(histor2);
            Assertions.assertEquals(value,value2);

        }
        //Assertions.assertEquals(flatLoginHistory,flatLoginHistoryList.get(1));

    }
    @Test
    public void dbWriterTest() throws IOException, ParseException {
        //читать файлы
        List<String> textLines = datareader.readFilesInDir();

        Users admin= new Users("admin", "Сергеев сергей Сергеевич");
        admin.setId(2);
        //распарсить на отдельные поля. С проверкой
        flatLoginHistoryList = parser.parse(textLines);
        //сохранить разобранные данные
        dbWriter.write(flatLoginHistoryList);

        Users dbuser = usersRepo.findByUsername("admin");
        Assertions.assertEquals(admin,dbuser);

    }
}
