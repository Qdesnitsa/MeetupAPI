package com.modsen.meetupapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "postgres.meetup.events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "event_seq",
            allocationSize = 1, initialValue = 1)
    private Long eventId;
    @NotBlank(message = "*Should have topic")
    @Size(min = 2, max = 255, message = "Should have between 2 and 255 symbols")
    private String topic;
    private String description;
    @NotBlank(message = "*Should have event organizer")
    @Size(min = 2, max = 255, message = "Should have between 2 and 255 symbols")
    private String organizer;
    @NotNull(message = "*Should have date and time")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Timestamp dateTime;
    @NotBlank(message = "*Should have place where event is held")
    @Size(min = 2, max = 255, message = "Should have between 2 and 255 symbols")
    private String place;

    public Event() {
    }

    public Event(String topic, String description, String organizer, Timestamp dateTime, String place) {
        this.topic = topic;
        this.description = description;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.place = place;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId) && Objects.equals(topic, event.topic)
                && Objects.equals(description, event.description) && Objects.equals(organizer, event.organizer)
                && Objects.equals(dateTime, event.dateTime) && Objects.equals(place, event.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, topic, description, organizer, dateTime, place);
    }

}
