package com.example.person.service;

import com.example.person.exception.CreationPersonException;
import com.example.person.exception.PersonNotFoundException;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonByName(String name) {
        Person person = personRepository.findByName(name);
        if (person == null) {
            throw new PersonNotFoundException();
        }
        return person;
    }

    public Person createPerson(Person person) {

        Person personSaved = personRepository.save(person);

        if (personSaved.getId() == null) {
            throw new CreationPersonException();
        }

        return personSaved;
    }
}
