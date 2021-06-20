package com.example.person.integration;


import com.example.person.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PersonIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPerson_returnPersonDetails() throws Exception {
        //arrange

        //act
        ResponseEntity<Person> responseEntity = restTemplate.getForEntity("/person/kelwin", Person.class);

        //assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo("kelwin");
        assertThat(responseEntity.getBody().getLastname()).isEqualTo("payares");
    }


    @Test
    public void createPerson_shouldCreateANewPerson() throws Exception {
        //arrange
        Person victoria = Person.builder().name("victoria").lastname("ladeuth").build();

        //act
        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", victoria, Person.class);

        //assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isNotEqualTo(null);
    }
}
