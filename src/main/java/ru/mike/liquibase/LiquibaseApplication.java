package ru.mike.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;


@SpringBootApplication
public class LiquibaseApplication {

    public static void main(String[] args) throws IOException {

        String urlAddress = "https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/stuff/titanic.csv";
        String path = "C:/Users/Mike/IdeaProjects/liquibase/src/main/resources/db/changelog/data/";
        String fileName = "titanic-data.csv";

        URL url = new URL(urlAddress);
        Path outputPath = Path.of(path + fileName);
        InputStream in = url.openStream();
        {
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
        in.close();

        String content = Files.readString(outputPath);
        String[] strings = content.split("\r\n", 2);
        String firstString = strings[0].toLowerCase();
        String contentUpdated = firstString + "\r\n" + strings[1];
//        Files.write(Path.of(path + fileName),contentUpdated.getBytes());
//        PrintWriter writer = new PrintWriter(String.valueOf(Path.of(path + fileName)), StandardCharsets.UTF_8);
//        writer.println(contentUpdated);
//        writer.close();

        FileWriter fw = new FileWriter(String.valueOf(Path.of(path + fileName)));
        BufferedWriter bw = new BufferedWriter(fw);
        // Write in file
        bw.write(content);
        // Close connection
        bw.close();

        SpringApplication.run(LiquibaseApplication.class, args);
    }

}
