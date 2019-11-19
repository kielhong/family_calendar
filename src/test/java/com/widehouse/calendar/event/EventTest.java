package com.widehouse.calendar.event;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

class EventTest {
    @Test
    void buildTest() {
        // given
        Instant startAt = ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC).toInstant();
        Instant endAt = ZonedDateTime.of(2019, 11, 1, 14, 0, 0, 0, ZoneOffset.UTC).toInstant();
        // when
        Event event = Event.builder()
                        .name("event name")
                        .description("event description")
                        .startAt(startAt)
                        .endAt(endAt)
                        .build();
        // then
        then(event)
                .hasFieldOrPropertyWithValue("name", "event name")
                .hasFieldOrPropertyWithValue("description", "event description")
                .hasFieldOrPropertyWithValue("startAt", startAt)
                .hasFieldOrPropertyWithValue("endAt", endAt);
    }

    @Test
    void startAtIsBehindEndAt_ThrowEventDateException() {
        // when
        Event.EventBuilder eventBuilder = Event.builder()
                .name("event name")
                .description("event description")
                .startAt(ZonedDateTime.of(2019, 11, 1, 15, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC).toInstant());

        thenThrownBy(() -> eventBuilder.build())
                .isInstanceOf(IllegalArgumentException.class);
    }
}