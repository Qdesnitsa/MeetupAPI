package com.modsen.meetupapi.dao;

import com.modsen.meetupapi.entity.Event;
import com.modsen.meetupapi.service.EventBaseService;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;


@Tag("dao")
@Execution(ExecutionMode.CONCURRENT)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class EventDAOTest {

    private EntityManager entityManagerMock = Mockito.mock(EntityManager.class);

    private EventBaseDAO eventBaseDAO = new EventBaseDAO(entityManagerMock);

    private EventBaseService eventBaseService = new EventBaseService(eventBaseDAO);
    private static String testTopic = "Test";
    private static String testTopicUpdate = "Testing";

    @Test
    @Order(1)
    public void testEventIsCreated() {
        int countFirst = eventBaseService.getAllEntities("topic", "asc", "topic","").size();
        Event event = new Event(testTopic,"TestSescription","TestOrganizer", Timestamp.valueOf("2022-01-01 00:00:00"),"TestPlace");
        eventBaseService.createOrUpdateEntity(event);
        assertThat(eventBaseService.getAllEntities("topic", "asc", "topic", "").size()).isEqualTo(++countFirst);
    }

    @Test
    @Order(2)
    public void testEventIsUpdated() {
        Event eventBeforeUpdate = eventBaseService.getEntityByTopic(testTopic).get();
        String oldTopic = eventBeforeUpdate.getTopic();
        eventBeforeUpdate.setTopic(testTopicUpdate);
        Event eventAfterUpdate = eventBaseService.createOrUpdateEntity(eventBeforeUpdate).get();
        assertThat(eventBeforeUpdate.getTopic()).isNotEqualTo(oldTopic);
        assertThat(eventBeforeUpdate.getEventId()).isEqualTo(eventAfterUpdate.getEventId());
    }

    @Test
    @Order(3)
    public void testEventIsDeleted() {
        Event event = eventBaseService.getEntityByTopic(testTopicUpdate).get();
        eventBaseService.deleteEntity(event.getEventId());
        assertThrows(NullPointerException.class, () -> {
            eventBaseService.getEntityById(event.getEventId());
        });
    }
}
