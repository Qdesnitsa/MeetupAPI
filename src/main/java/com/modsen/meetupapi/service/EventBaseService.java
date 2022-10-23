package com.modsen.meetupapi.service;

import com.modsen.meetupapi.dao.BaseDAO;
import com.modsen.meetupapi.dao.EventDAO;
import com.modsen.meetupapi.dto.EventDto;
import com.modsen.meetupapi.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    private EventDto mapToDTO(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setEventId(event.getEventId());
        eventDto.setTopic(event.getTopic());
        eventDto.setDescription(event.getDescription());
        eventDto.setOrganizer(event.getOrganizer());
        eventDto.setDateTime(String.valueOf(event.getDateTime()));
        eventDto.setPlace(String.valueOf(event.getPlace()));
        return eventDto;
    }

    private Event mapToEntity(EventDto eventDto){
        Event event = new Event();
        event.setTopic(eventDto.getTopic());
        event.setDescription(eventDto.getDescription());
        event.setOrganizer(eventDto.getOrganizer());
        event.setDateTime(Timestamp.valueOf(eventDto.getDateTime()));
        event.setPlace(eventDto.getPlace());
        return event;
    }

    @Override
    public Optional<Event> getEntityByTopic(String topic) {
        return baseDAO.getEntityByTopic(topic);
    }
}
