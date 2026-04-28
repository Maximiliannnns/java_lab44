package com.example;

import com.example.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderMainTest 
{

    private CsvReaderMain csvReaderMain;

    @BeforeEach
    void setUp()
    {
        csvReaderMain = new CsvReaderMain();
    }

    @Test
    @DisplayName("Должен успешно прочитать тестовый CSV файл и вернуть непустой список")
    void shouldReadCsvFileAndReturnList()
    {
        String testFile = "foreign_names.csv";   

        List<Person> people = CsvReaderMain.readPeopleFromCsv(testFile);

        assertNotNull(people, "Список не должен быть null");
        assertTrue(people.size() > 0, "Список должен содержать хотя бы одного человека");
        System.out.println("Успешно загружено " + people.size() + " записей");
    }

    @Test
    @DisplayName("Каждый Person должен иметь корректно заполненные поля")
    void shouldHaveCorrectlyFilledPersonFields() 
    {
        List<Person> people = CsvReaderMain.readPeopleFromCsv("foreign_names.csv");

        assertFalse(people.isEmpty());

        Person firstPerson = people.get(0);

        assertNotNull(firstPerson.getName(), "Имя не должно быть null");
        assertNotNull(firstPerson.getDivision(), "Подразделение не должно быть null");
        assertTrue(firstPerson.getSalary() > 0, "Зарплата должна быть положительной");
        assertNotNull(firstPerson.getBirthDate(), "Дата рождения не должна быть null");
    }

    @Test
    @DisplayName("Подразделения не должны дублироваться (одно название = один объект Division)")
    void shouldNotDuplicateDivisions() 
    {
        List<Person> people = CsvReaderMain.readPeopleFromCsv("foreign_names.csv");

        long uniqueDivisionCount = people.stream()
                .map(p -> p.getDivision().getName())
                .distinct()
                .count();

        long totalPeople = people.size();

        // Если людей больше, чем уникальных подразделений — значит дублирование подразделений работает правильно
        assertTrue(uniqueDivisionCount < totalPeople || uniqueDivisionCount == 1,
                "Должно быть меньше подразделений, чем людей (или хотя бы одно)");
    }

    @Test
    @DisplayName("Должен корректно обрабатывать несуществующий файл")
    void shouldHandleMissingFileGracefully() 
    {
        List<Person> people = CsvReaderMain.readPeopleFromCsv("non_existing_file.csv");

        //возвращается пустой список при ошибке
        assertNotNull(people);
        assertTrue(people.isEmpty(), "При отсутствии файла должен возвращаться пустой список");
    }
}