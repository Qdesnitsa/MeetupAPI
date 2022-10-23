package com.modsen.meetupapi.service;

import com.modsen.meetupapi.entity.Event;

import java.util.Optional;

public interface EventService extends BaseService<Event, Long> {
    Optional<Event> getEntityByTopic(String topic);
}
