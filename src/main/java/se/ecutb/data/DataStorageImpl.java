package se.ecutb.data;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import se.ecutb.model.Person;
import se.ecutb.util.PersonGenerator;

import javax.swing.*;
import java.time.Period;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Skapa implementationer till alla metoder. Jag har redan skrivit en metodimplementation för hjälp
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private List<Person> personList;

    private DataStorageImpl(){
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance(){
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        for (Person p: personList){
            if (filter.test(p)){
                return p;
            }
        }
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString){
        Person p = findOne(filter);
        if (p == null){
            return "Not found!";
        } else {
            return personToString.apply(p);
        }
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString){
        List<Person> pList = findMany(filter);
        List<String> stringList = new ArrayList<>();
        pList.forEach(person -> stringList.add(personToString.apply(person)));
        return stringList;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer){
        findMany(filter).forEach(consumer);
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator){
        List<Person> pList = new ArrayList<>(personList);
        pList.sort(comparator);
        return pList;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator){
        List<Person> pList = findMany(filter);
        pList.sort(comparator);
        return pList;
    }
}
