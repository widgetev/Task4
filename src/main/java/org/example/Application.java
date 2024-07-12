package org.example;

import org.example.checkers.Checker;
import org.example.db.DBWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Application implements ApplicationRunner {
    @Autowired
    DataReader datareader;

    @Autowired
    Parser parser;

    @Autowired
    DBWriter dbWriter;

    @Autowired
    List<Checker> checkerList;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       //читать файлы
       List<String> textLines = datareader.readFilesInDir();

       //распарсить на отдельные поля. С проверкой
       List<FlatLoginHistory> flatLoginHistoryList = parser.parse(textLines);

       //сохранить разобранные данные
        dbWriter.write(flatLoginHistoryList);


    }
}
