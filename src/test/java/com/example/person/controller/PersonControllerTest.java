package com.example.person.controller;

import com.example.person.exception.CreationPersonException;
import com.example.person.exception.PersonNotFoundException;
import com.example.person.model.Person;
import com.example.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void getPersonByName_shouldReturnPerson() throws Exception {

        given(personService.getPersonByName(anyString())).willReturn(Person.builder()
                .name("kelwin").lastname("payares").build());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/person/kelwin")).andExpect(status().isOk())
                .andExpect(jsonPath("name").value("kelwin"))
                .andExpect(jsonPath("lastname").value("payares"));

    }

    @Test
    public void getPersonByNameNotFound_shouldPersonNotFoundException() throws Exception {
        given(personService.getPersonByName(anyString())).willThrow(new PersonNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/person/stevin")).andExpect(status().isNotFound());
    }

    @Test
    public void createPerson_shouldCreateANewPerson() throws Exception {

        String person = "{\"name\":\"victoria\",\"lastname\":\"ladeuth\"}";

        given(personService.createPerson(Person.builder()
                .name("victoria").lastname("ladeuth").build())).willReturn(Person.builder()
                .id(1L).name("victoria").lastname("ladeuth").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(person))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("victoria"))
                .andExpect(jsonPath("lastname").value("ladeuth"));

    }

    @Test
    public void createPersonError_ShouldReturnCreationPersonException() throws Exception {

        String person = "{\"name\":\"victoria\",\"lastname\":\"ladeuth\"}";

        given(personService.createPerson(Person.builder()
                .name("victoria").lastname("ladeuth").build())).willThrow(new CreationPersonException());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(person))
                .andExpect(status().isInternalServerError());
    }
}
