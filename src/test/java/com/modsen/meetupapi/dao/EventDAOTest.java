package com.modsen.meetupapi.dao;

import com.modsen.meetupapi.entity.Event;
import com.modsen.meetupapi.service.EventBaseService;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class EventDAOTest {

    @Test
    public void testGetAllEntitiesGenerateCorrectSql() {
        // arrange
        final String sortBy = "topic";
        final String sortDir = "asc";
        final String filterBy = "organizer";
        final String filterValue = "USA";
        final String expectedSql = "SELECT * FROM events WHERE " + filterBy + " LIKE '%" + filterValue + "%' ORDER BY " + sortBy + " " + sortDir;
        final NativeQuery nativeQueryMock = getNativeQueryResultListMock();
        final Session sessionMock = getSessionCreateSQLQueryMock(nativeQueryMock);
        final EntityManager entityManagerMock = getEntityManagerMock(sessionMock);
        final EventBaseService systemUnderTest = getEventBaseService(entityManagerMock);

        // act
        List<Event> events = systemUnderTest.getAllEntities(sortBy, sortDir, filterBy, filterValue);

        // assert
        verify(entityManagerMock).unwrap(Session.class);
        verify(sessionMock).createSQLQuery(expectedSql);
        verify(nativeQueryMock).getResultList();
        assertThat(events.size()).isEqualTo(0);
    }

    @Test
    public void testGetEntityById() {
        // arrange
        final Long id = 1L;
        final Session sessionMock = getSessionGetMock();
        final EntityManager entityManagerMock = getEntityManagerMock(sessionMock);
        final EventBaseService systemUnderTest = getEventBaseService(entityManagerMock);

        // act
        Optional<Event> event = systemUnderTest.getEntityById(id);

        //assert
        verify(entityManagerMock).unwrap(Session.class);
        assertNotNull(event);
    }

    @Test
    public void testCreateOrUpdateEntity() {
        // arrange
        final Event eventMock = Mockito.mock(Event.class);
        final Session sessionMock = getSessionSaveOrUpdateMock();
        final EntityManager entityManagerMock = getEntityManagerMock(sessionMock);
        final EventBaseService systemUnderTest = getEventBaseService(entityManagerMock);

        // act
        Optional<Event> event = systemUnderTest.createOrUpdateEntity(eventMock);

        //assert
        verify(entityManagerMock).unwrap(Session.class);
        assertNotNull(event);
    }

    @Test
    public void testDeleteEntity() {
        // arrange
        final Long id = 1L;
        final Session sessionMock = getSessionGetMock();
        final EntityManager entityManagerMock = getEntityManagerMock(sessionMock);
        final EventBaseService systemUnderTest = getEventBaseService(entityManagerMock);

        // act
        systemUnderTest.deleteEntity(id);

        //assert
        verify(entityManagerMock).unwrap(Session.class);
        verify(sessionMock).get(Event.class, id);
    }

    private EventBaseService getEventBaseService(EntityManager entityManagerMock) {
        EventBaseDAO eventBaseDAO = new EventBaseDAO(entityManagerMock);
        return new EventBaseService(eventBaseDAO);
    }

    private EntityManager getEntityManagerMock(Session sessionMock) {
        EntityManager entityManagerMock = Mockito.mock(EntityManager.class);
        when(entityManagerMock.unwrap(Session.class)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return sessionMock;
            }
        });

        return entityManagerMock;
    }

    private Session getSessionGetMock() {
        Session sessionMock = Mockito.mock(Session.class);
        when(sessionMock.get(eq(Event.class), anyLong()))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return new Event();
                    }
                });
        return sessionMock;
    }

    private Session getSessionSaveOrUpdateMock() {
        Session sessionMock = Mockito.mock(Session.class);
        doNothing().when(sessionMock).saveOrUpdate(isA(Event.class));
        return sessionMock;
    }

    private Session getSessionCreateSQLQueryMock(NativeQuery nativeQueryMock) {
        Session sessionMock = Mockito.mock(Session.class);
        when(sessionMock.createSQLQuery(any(String.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return nativeQueryMock;
                    }
                });
        return sessionMock;
    }

    private NativeQuery getNativeQueryResultListMock() {
        NativeQuery nativeQueryMock = Mockito.mock(NativeQuery.class);
        when(nativeQueryMock.getResultList())
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) {
                        return new ArrayList<Event>();
                    }
                });

        return nativeQueryMock;
    }
}
