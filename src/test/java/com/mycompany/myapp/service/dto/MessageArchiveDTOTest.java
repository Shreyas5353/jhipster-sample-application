package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageArchiveDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageArchiveDTO.class);
        MessageArchiveDTO messageArchiveDTO1 = new MessageArchiveDTO();
        messageArchiveDTO1.setId(1L);
        MessageArchiveDTO messageArchiveDTO2 = new MessageArchiveDTO();
        assertThat(messageArchiveDTO1).isNotEqualTo(messageArchiveDTO2);
        messageArchiveDTO2.setId(messageArchiveDTO1.getId());
        assertThat(messageArchiveDTO1).isEqualTo(messageArchiveDTO2);
        messageArchiveDTO2.setId(2L);
        assertThat(messageArchiveDTO1).isNotEqualTo(messageArchiveDTO2);
        messageArchiveDTO1.setId(null);
        assertThat(messageArchiveDTO1).isNotEqualTo(messageArchiveDTO2);
    }
}
