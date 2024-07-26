package ru.mike.liquibase.service;

import ru.mike.liquibase.domain.Passenger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class LiquibaseService {

    public List<Passenger> setFilters(List<Passenger> listFilter1, boolean survived, boolean adult, boolean males, boolean noRelatives) {
        List<Passenger> finalListFilter = listFilter1;
        Supplier<Stream<Passenger>> streamFilter1 = finalListFilter::stream;
        if (survived) {
            listFilter1 = streamFilter1.get()
                    .filter(Passenger::isSurvived).toList();
        }
        List<Passenger> listFilter2 = listFilter1;
        Supplier<Stream<Passenger>> streamFilter2 = listFilter2::stream;
        if (adult) {
            listFilter2 = streamFilter2.get()
                    .filter(p -> p.getAge() >= 16).toList();
        }
        List<Passenger> listFilter3 = listFilter2;
        Supplier<Stream<Passenger>> streamFilter3 = listFilter3::stream;
        if (males) {
            listFilter3 = streamFilter3.get()
                    .filter(p -> p.getSex().equals("male")).toList();
        }
        List<Passenger> listFilter4 = listFilter3;
        Supplier<Stream<Passenger>> streamFilter4 = listFilter4::stream;
        if (noRelatives) {
            listFilter4 = streamFilter4.get()
                    .filter(p -> p.getParentsChildrenAboard() < 1 && p.getSiblingsSpousesAboard() < 1).toList();
        }
        return listFilter4;
    }

    public void getSort(String pageSort, String direction, List<Passenger> listFilter1) {
        if (!pageSort.equals("id") && direction.equals("ASC")) {
            switch (pageSort) {
                case "name" -> listFilter1.sort(Comparator.comparing(Passenger::getName));
                case "age" -> listFilter1.sort(Comparator.comparing(Passenger::getAge));
                case "fare" -> listFilter1.sort(Comparator.comparing(Passenger::getFare));
            }
        } else {
            switch (pageSort) {
                case "name" -> listFilter1.sort(Comparator.comparing(Passenger::getName).reversed());
                case "age" -> listFilter1.sort(Comparator.comparing(Passenger::getAge).reversed());
                case "fare" -> listFilter1.sort(Comparator.comparing(Passenger::getFare).reversed());
            }
        }
    }

    public static void liquibaseMigration() throws IOException {
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
        String firstString = strings[0].toLowerCase().replace("/", "_").replace(" ", "_");
        String contentUpdated = firstString + "\r\n" + strings[1];

        Files.write(Path.of(path + fileName), contentUpdated.getBytes());
        PrintWriter writer = new PrintWriter(String.valueOf(Path.of(path + fileName)), StandardCharsets.UTF_8);
        writer.println(contentUpdated);
        writer.close();
    }

}
