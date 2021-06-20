package com.example.person.repository;

import com.example.person.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void getPersonByName_shouldReturnPerson() throws Exception {

        Person personsaved = testEntityManager.persistAndFlush(Person.builder()
                .name("stevin").lastname("ladeuth").build());
        Person person = personRepository.findByName("stevin");

        assertThat(person.getName()).isEqualTo(personsaved.getName());
        assertThat(person.getLastname()).isEqualTo(personsaved.getLastname());
    }

}
