package com.example;

import com.example.model.Division;
import com.example.model.Person;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Главный класс для чтения данных о сотрудниках из CSV-файла.
 * Использует библиотеку OpenCSV для парсинга файла с разделителем ';'.
 */
public class CsvReaderMain 
{
	
	/**
     * Точка входа в программу.
     * Загружает данные из CSV-файла и выводит информацию о сотрудниках в консоль.
     *
     * @param args аргументы командной строки 
     */
    public static void main(String[] args) 
    {
        String csvFileName = "foreign_names.csv";
        List<Person> people = readPeopleFromCsv(csvFileName);
        
        System.out.println("Загружено людей: " + people.size());
        for (Person p : people) 
        {
            System.out.println(p);
        }
    }
    /**
     * Читает данные о сотрудниках из CSV-файла и возвращает список объектов {@link Person}.
     *
     *Файл должен находиться в папке {@code src/main/resources}.
     * Ожидаемый формат CSV (разделитель ';'):
     * id;name;gender;birthDate;division;salary
     *
     * @param csvFileName имя CSV-файла (например: "foreign_names.csv")
     * @return список сотрудников {@link List} из {@link Person}
     */
    public static List<Person> readPeopleFromCsv(String csvFileName) 
    {
        List<Person> people = new ArrayList<>();
        
        Map<String, Division> divisions = new HashMap<>();
        int divisionIdCounter = 1;            

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (InputStream in = CsvReaderMain.class.getClassLoader().getResourceAsStream(csvFileName);
             CSVReader reader = in == null ? null : 
                 new CSVReaderBuilder(new InputStreamReader(in))
                     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                     .build()) {

            if (reader == null) 
            {
                throw new RuntimeException("Файл не найден: " + csvFileName);
            }

            String[] nextLine;
            boolean isFirstLine = true;

            while ((nextLine = reader.readNext()) != null) 
            {
                
                if (isFirstLine) 
                {  
                    isFirstLine = false;
                    continue;
                }

                if (nextLine.length < 6) 
                {
                    System.out.println("Пропущена некорректная строка: " + String.join(";", nextLine));
                    continue;
                }

                try 
                {
                    int personId = Integer.parseInt(nextLine[0].trim());
                    String name = nextLine[1].trim();
                    String gender = nextLine[2].trim();
                    String birthDateStr = nextLine[3].trim();
                    String divisionName = nextLine[4].trim();
                    double salary = Double.parseDouble(nextLine[5].trim().replace(",", "."));

                    LocalDate birthDate = LocalDate.parse(birthDateStr, dateFormatter);

                    Division division;
                    if (divisions.containsKey(divisionName)) 
                    {
                        division = divisions.get(divisionName);
                    } else 
                    {
                        division = new Division(divisionIdCounter++, divisionName);
                        divisions.put(divisionName, division);
                    }

                    Person person = new Person(personId, name, gender, division, salary, birthDate);
                    people.add(person);

                } catch (Exception e) 
                {
                    System.out.println("Ошибка при обработке строки: " + String.join(";", nextLine));
                }
            }

        } catch (Exception e) 
        {
            System.err.println("Ошибка при чтении CSV файла:");
            e.printStackTrace();
        }

        return people;
    }
}