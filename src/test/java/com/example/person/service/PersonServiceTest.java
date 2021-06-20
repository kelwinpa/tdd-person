package com.example.person.service;

import com.example.person.exception.CreationPersonException;
import com.example.person.exception.PersonNotFoundException;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    @Before
    public void setup() {
        personService = new PersonService(personRepository);
    }

    @Test
    public void getPersonByName_shouldReturnPerson() {
        given(personRepository.findByName(anyString())).willReturn(Person.builder()
                .name("kelwin").lastname("payares").build());

        Person person = personService.getPersonByName("kelwin");

        assertThat(person.getName()).isEqualTo("kelwin");
        assertThat(person.getLastname()).isEqualTo("payares");
    }

    @Test
    public void createPerson_shouldCreateANewPerson() throws Exception {

        Person victoria = Person.builder()
                .name("victoria").lastname("ladeuth").build();

        given(personRepository.save(victoria)).willReturn(Person.builder().id(101L)
                .name("victoria").lastname("ladeuth").build());

        Person personSaved = personService.createPerson(victoria);

        assertThat(personSaved).isNotNull();
        assertThat(personSaved.getId()).isNotNull();
        assertThat(personSaved.getName()).isEqualTo(victoria.getName());
        assertThat(personSaved.getLastname()).isEqualTo(victoria.getLastname());

    }

    @Test(expected = PersonNotFoundException.class)
    public void getPersonByNameNotFound_shouldPersonNotFoundException() throws Exception {
        given(personRepository.findByName("stevin")).willReturn(null);

        personService.getPersonByName("stevin");
    }

    @Test(expected = CreationPersonException.class)
    public void createPersonError_ShouldReturnCreationPersonException() throws Exception {
        Person victoria = Person.builder()
                .name("victoria").lastname("ladeuth").build();

        given(personRepository.save(victoria)).willReturn(victoria);

        personService.createPerson(victoria);
    }
}
