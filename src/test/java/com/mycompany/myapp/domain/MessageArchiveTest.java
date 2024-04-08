package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.MessageArchiveTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageArchiveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageArchive.class);
        MessageArchive messageArchive1 = getMessageArchiveSample1();
        MessageArchive messageArchive2 = new MessageArchive();
        assertThat(messageArchive1).isNotEqualTo(messageArchive2);

        messageArchive2.setId(messageArchive1.getId());
        assertThat(messageArchive1).isEqualTo(messageArchive2);

        messageArchive2 = getMessageArchiveSample2();
        assertThat(messageArchive1).isNotEqualTo(messageArchive2);
    }
}
