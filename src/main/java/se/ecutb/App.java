package se.ecutb;

import se.ecutb.data.DataStorage;
import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class App
{

    private static DataStorage dataStorage;


    static {
        dataStorage = DataStorage.INSTANCE;

    }

    public static void main( String[] args )
    {
        // 1
        Predicate<Person> erik = person -> person.getFirstName().equalsIgnoreCase("Erik");

        // 2
        Predicate<Person> females = person -> person.getGender().equals(Gender.FEMALE);

        // 3
        Predicate<Person> findByDate = person -> person.getBirthDate().isAfter(LocalDate.parse("1999-12-31"));

        // 4
        Predicate<Person> findById = person -> person.getId() == 123;

        // 5: “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
        Predicate<Person> findByAnotherId = person -> person.getId() == 456;
        Function<Person, String> mapToString = person ->
                (person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate());

        // 6. Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
        Predicate<Person> males = person -> person.getGender().equals(Gender.MALE);
        Predicate<Person> startsWithE = person -> person.getFirstName().startsWith("E");
        Function<Person, String> mapToString2 = person ->
                (person.getFirstName() + " " + person.getLastName());

        // 7. Find all people who are below age of 10 and convert them to a String like this:
        // “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
        Function<Person, String> mapToString3 = person ->
                                person.getFirstName() + " " +
                                person.getLastName() + " " +
                                Period.between(person.getBirthDate(), LocalDate.now()).getYears() + "\n";

        Predicate<Person> under10 = person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10;

        // 8. Using findAndDo() print out all people with firstName “Ulf”
        Consumer<Person> print = person -> System.out.println(person);
        Predicate<Person> ulf = person -> person.getFirstName().equalsIgnoreCase("ulf");

        // 9. Using findAndDo() print out everyone who have their lastName contain their firstName.
        Predicate<Person> firstNameInLastName = person -> person.getLastName().contains(person.getFirstName());

        // 10. Using findAndDo() print out the firstName and lastName of everyone whose firstName is a
        // palindrome.
        Predicate<Person> firstNamePalindrome = person ->
                new StringBuilder(person.getFirstName()).reverse().toString().equalsIgnoreCase(person.getFirstName());

        // 11. Using findAndSort() find everyone whose firstName starts with A sorted by birthdate
        Predicate<Person> startsWithA = person -> person.getFirstName().startsWith("A");
        Comparator<Person> sortByBirthday = Comparator.comparing(Person::getBirthDate);

        // 12. Using findAndSort() find everyone born before 1950 sorted reversed by latest to earliest
        Predicate<Person> bornBefore1950 = person -> person.getBirthDate().isBefore(LocalDate.parse("1950-01-01"));
        Comparator<Person> reversedBirthday = Comparator.comparing(Person::getBirthDate).reversed();

        // 13. Using findAndSort() find everyone sorted in following order: lastName > firstName >
        //birthdate.
        Predicate<Person> all = Objects::nonNull;
        Comparator<Person> sortX3 = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);

        //dataStorage.findMany(erik).forEach(System.out::println);
        //dataStorage.findMany(females).forEach(System.out::println);
        //dataStorage.findMany(findByDate).forEach(System.out::println);
        //System.out.println(dataStorage.findOne(findById));
        //System.out.println(dataStorage.findOneAndMapToString(findByAnotherId, mapToString));
        //System.out.println(dataStorage.findManyAndMapEachToString(startsWithE.and(males), mapToString2));
        //System.out.println(dataStorage.findManyAndMapEachToString(under10, mapToString3));
        //dataStorage.findAndDo(ulf, print);
        //dataStorage.findAndDo(firstNameInLastName, print);
        //dataStorage.findAndDo(firstNamePalindrome, print);
        //System.out.println(dataStorage.findAndSort(startsWithA, sortByBirthday));
        //System.out.println(dataStorage.findAndSort(bornBefore1950, reversedBirthday));
        System.out.println(dataStorage.findAndSort(all, sortX3));

    }
}
