package br.com.erudio.services;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    public Person create(Person person){
        return person;
    }

    public Person update(Person person){
        return person;
    }

    public void delete(String id){

    }

    public Person findById(String id){
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Aleff");
        person.setLastName("Porto");
        person.setAddress("Belo Horizonte - MG");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll(){
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            personList.add(person);
        }
        return personList;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person Name" + i);
        person.setLastName("Person Last Name" + i);
        person.setAddress("City" + i);
        person.setGender("Gender" + i);
        return person;
    }


}
