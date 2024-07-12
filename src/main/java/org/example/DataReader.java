package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Qualifier(value = "dataReader")
public class DataReader{
    @Value("${spring.application.pathInput}")
    private String path;

    public DataReader() {
    }

    List<FlatLoginHistory> get() {
        return new ArrayList<>();
    }

    List<String> readFilesInDir() throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<String> lines = new ArrayList<>();
        for (File file : listOfFiles) {
            lines.addAll(readAllLines(file));
        }
        return lines;
    }

    List<String> readAllLines(File file) {
        Scanner scanner;
        List<String> lines = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }


}
