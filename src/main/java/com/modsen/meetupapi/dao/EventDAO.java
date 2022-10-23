package com.modsen.meetupapi.dao;

import com.modsen.meetupapi.entity.Event;

import java.util.Optional;

public interface EventDAO extends BaseDAO<Event, Long> {
    Optional<Event> getEntityByTopic(String topic);
}
