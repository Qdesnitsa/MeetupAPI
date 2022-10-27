package com.modsen.meetupapi.dao;

import com.modsen.meetupapi.entity.Event;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class EventBaseDAO implements EventDAO {
    private EntityManager entityManager;

    public EventBaseDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Event> getAllEntities(String sortBy, String sortDir, String filterBy, String filterValue) {
        Session session = entityManager.unwrap(Session.class);
        List<Event> allEvents = session
                .createSQLQuery("SELECT * FROM postgres.meetup.events WHERE " + filterBy + " LIKE '%" + filterValue + "%' ORDER BY " + sortBy + " " + sortDir)
                .getResultList();
        return allEvents;
    }

    @Override
    public Optional<Event> getEntityById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return Optional.of(session.get(Event.class, id));
    }

    @Override
    public Optional<Event> createOrUpdateEntity(Event entity) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(entity);
        return Optional.of(entity);
    }

    @Override
    public void deleteEntity(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Event event = session.get(Event.class, id);
        if (event == null) {
            throw new NullPointerException();
        }
        session.delete(event);
    }

    @Override
    public Optional<Event> getEntityByTopic(String topic) {
        Session session = entityManager.unwrap(Session.class);
        Event event = session.createQuery("FROM Event WHERE  topic = :topic", Event.class)
                .setParameter("topic", topic)
                .getSingleResult();
        return Optional.of(event);
    }
}
