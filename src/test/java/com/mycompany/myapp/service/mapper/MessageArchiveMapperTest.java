package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.MessageArchiveAsserts.*;
import static com.mycompany.myapp.domain.MessageArchiveTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageArchiveMapperTest {

    private MessageArchiveMapper messageArchiveMapper;

    @BeforeEach
    void setUp() {
        messageArchiveMapper = new MessageArchiveMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMessageArchiveSample1();
        var actual = messageArchiveMapper.toEntity(messageArchiveMapper.toDto(expected));
        assertMessageArchiveAllPropertiesEquals(expected, actual);
    }
}
