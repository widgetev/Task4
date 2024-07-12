package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Value("classpath:filename.txt")
    Resource resource;
}
