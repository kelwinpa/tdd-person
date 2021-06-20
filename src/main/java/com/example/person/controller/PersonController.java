package com.example.person.controller;

import com.example.person.exception.CreationPersonException;
import com.example.person.exception.PersonNotFoundException;
import com.example.person.model.Person;
import com.example.person.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{name}")
    private Person getPersonByName(@PathVariable String name) {
        return personService.getPersonByName(name);
    }

    @PostMapping("/person")
    private Person createPerson(@RequestBody Person person){
        return  personService.createPerson(person);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void personNotFoundHandler(PersonNotFoundException e) {
        log.error("Entering and leaving PersonController : PersonNotFoundHandler");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void creationPersonHandler(CreationPersonException e) {
        log.error("Entering and leaving PersonController : CreationPersonHandler");
    }

}
