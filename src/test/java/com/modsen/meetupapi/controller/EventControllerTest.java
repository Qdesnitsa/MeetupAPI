package com.modsen.meetupapi.controller;

import com.modsen.meetupapi.entity.Event;
import com.modsen.meetupapi.service.EventBaseService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Tag("controller")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EventBaseService eventBaseService;

    @Test
    public void tesStatusIsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testContentJson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/events"))
                .accept(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic", Matchers.is("Cyber")));
    }

    @Test
    public void testEventsEndPointReturnsListOfAllEvents() {
        int countAllEvents = eventBaseService.getAllEntities("topic", "asc", "event_id", "").size();
        Event[] events = this.restTemplate.getForObject("http://localhost:8080/api/events", Event[].class);
        assertThat(events).hasSize(countAllEvents);
    }


    @ParameterizedTest
    @CsvSource(value = {"17", "18", "19"})
    public void testEventsIDEndPointReturnsCorrectEvent(long targetId) {
        Event eventDB = eventBaseService.getEntityById(targetId).get();
        Event eventByPath = this.restTemplate.getForObject("http://localhost:8080/api/events/" + targetId, Event.class);
        assertThat(eventDB).isEqualTo(eventByPath);
    }

//    @Test
//    public void testEveryEventHasNotEmptyTitle() {
//        Event[] events = this.restTemplate.getForObject("http://localhost:8080/api/events", Event[].class);
//        assertTrue(Arrays.stream(events).allMatch(s -> !s.getTopic().isBlank()));
//    }
}
