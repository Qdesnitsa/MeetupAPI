package com.modsen.meetupapi.service;

import com.modsen.meetupapi.dao.EventDAO;
import com.modsen.meetupapi.entity.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventBaseService implements EventService {
    private EventDAO baseDAO;

    public EventBaseService(@Qualifier("eventBaseDAO") EventDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEntities(String sortBy, String sortDir, String filterBy, String filterValue) {
        return baseDAO.getAllEntities(sortBy, sortDir, filterBy, filterValue);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Event> getEntityById(Long id) {
        return baseDAO.getEntityById(id);
    }

    @Override
    public Optional<Event> createOrUpdateEntity(Event entity) {
        return baseDAO.createOrUpdateEntity(entity);
    }

    @Override
    public void deleteEntity(Long id) {
        baseDAO.deleteEntity(id);
    }

    @Override
    public Optional<Event> getEntityByTopic(String topic) {
        return baseDAO.getEntityByTopic(topic);
    }
}
