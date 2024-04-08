package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MessageArchiveAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MessageArchive;
import com.mycompany.myapp.repository.MessageArchiveRepository;
import com.mycompany.myapp.service.dto.MessageArchiveDTO;
import com.mycompany.myapp.service.mapper.MessageArchiveMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MessageArchiveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageArchiveResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_READ = false;
    private static final Boolean UPDATED_HAS_READ = true;

    private static final Boolean DEFAULT_HAS_EMERG_ALERT = false;
    private static final Boolean UPDATED_HAS_EMERG_ALERT = true;

    private static final Boolean DEFAULT_HAS_SIGN_OUT = false;
    private static final Boolean UPDATED_HAS_SIGN_OUT = true;

    private static final String DEFAULT_SENDER_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_SENDER_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SENDER_REF_ID = 1L;
    private static final Long UPDATED_SENDER_REF_ID = 2L;
    private static final Long SMALLER_SENDER_REF_ID = 1L - 1L;

    private static final String DEFAULT_RECEIVER_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_RECEIVER_REF_ID = 1L;
    private static final Long UPDATED_RECEIVER_REF_ID = 2L;
    private static final Long SMALLER_RECEIVER_REF_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;
    private static final Long SMALLER_STUDENT_ID = 1L - 1L;

    private static final Boolean DEFAULT_HAS_PRIVATE_ACTIVE = false;
    private static final Boolean UPDATED_HAS_PRIVATE_ACTIVE = true;

    private static final Long DEFAULT_SCHOOL_ID = 1L;
    private static final Long UPDATED_SCHOOL_ID = 2L;
    private static final Long SMALLER_SCHOOL_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/message-archives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MessageArchiveRepository messageArchiveRepository;

    @Autowired
    private MessageArchiveMapper messageArchiveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageArchiveMockMvc;

    private MessageArchive messageArchive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageArchive createEntity(EntityManager em) {
        MessageArchive messageArchive = new MessageArchive()
            .message(DEFAULT_MESSAGE)
            .hasRead(DEFAULT_HAS_READ)
            .hasEmergAlert(DEFAULT_HAS_EMERG_ALERT)
            .hasSignOut(DEFAULT_HAS_SIGN_OUT)
            .senderRefTable(DEFAULT_SENDER_REF_TABLE)
            .senderRefId(DEFAULT_SENDER_REF_ID)
            .receiverRefTable(DEFAULT_RECEIVER_REF_TABLE)
            .receiverRefId(DEFAULT_RECEIVER_REF_ID)
            .status(DEFAULT_STATUS)
            .studentId(DEFAULT_STUDENT_ID)
            .hasPrivateActive(DEFAULT_HAS_PRIVATE_ACTIVE)
            .schoolId(DEFAULT_SCHOOL_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return messageArchive;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageArchive createUpdatedEntity(EntityManager em) {
        MessageArchive messageArchive = new MessageArchive()
            .message(UPDATED_MESSAGE)
            .hasRead(UPDATED_HAS_READ)
            .hasEmergAlert(UPDATED_HAS_EMERG_ALERT)
            .hasSignOut(UPDATED_HAS_SIGN_OUT)
            .senderRefTable(UPDATED_SENDER_REF_TABLE)
            .senderRefId(UPDATED_SENDER_REF_ID)
            .receiverRefTable(UPDATED_RECEIVER_REF_TABLE)
            .receiverRefId(UPDATED_RECEIVER_REF_ID)
            .status(UPDATED_STATUS)
            .studentId(UPDATED_STUDENT_ID)
            .hasPrivateActive(UPDATED_HAS_PRIVATE_ACTIVE)
            .schoolId(UPDATED_SCHOOL_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return messageArchive;
    }

    @BeforeEach
    public void initTest() {
        messageArchive = createEntity(em);
    }

    @Test
    @Transactional
    void createMessageArchive() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);
        var returnedMessageArchiveDTO = om.readValue(
            restMessageArchiveMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(messageArchiveDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MessageArchiveDTO.class
        );

        // Validate the MessageArchive in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMessageArchive = messageArchiveMapper.toEntity(returnedMessageArchiveDTO);
        assertMessageArchiveUpdatableFieldsEquals(returnedMessageArchive, getPersistedMessageArchive(returnedMessageArchive));
    }

    @Test
    @Transactional
    void createMessageArchiveWithExistingId() throws Exception {
        // Create the MessageArchive with an existing ID
        messageArchive.setId(1L);
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageArchiveMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMessageArchives() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageArchive.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].hasRead").value(hasItem(DEFAULT_HAS_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEmergAlert").value(hasItem(DEFAULT_HAS_EMERG_ALERT.booleanValue())))
            .andExpect(jsonPath("$.[*].hasSignOut").value(hasItem(DEFAULT_HAS_SIGN_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].senderRefTable").value(hasItem(DEFAULT_SENDER_REF_TABLE)))
            .andExpect(jsonPath("$.[*].senderRefId").value(hasItem(DEFAULT_SENDER_REF_ID.intValue())))
            .andExpect(jsonPath("$.[*].receiverRefTable").value(hasItem(DEFAULT_RECEIVER_REF_TABLE)))
            .andExpect(jsonPath("$.[*].receiverRefId").value(hasItem(DEFAULT_RECEIVER_REF_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasPrivateActive").value(hasItem(DEFAULT_HAS_PRIVATE_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].schoolId").value(hasItem(DEFAULT_SCHOOL_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getMessageArchive() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get the messageArchive
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL_ID, messageArchive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageArchive.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.hasRead").value(DEFAULT_HAS_READ.booleanValue()))
            .andExpect(jsonPath("$.hasEmergAlert").value(DEFAULT_HAS_EMERG_ALERT.booleanValue()))
            .andExpect(jsonPath("$.hasSignOut").value(DEFAULT_HAS_SIGN_OUT.booleanValue()))
            .andExpect(jsonPath("$.senderRefTable").value(DEFAULT_SENDER_REF_TABLE))
            .andExpect(jsonPath("$.senderRefId").value(DEFAULT_SENDER_REF_ID.intValue()))
            .andExpect(jsonPath("$.receiverRefTable").value(DEFAULT_RECEIVER_REF_TABLE))
            .andExpect(jsonPath("$.receiverRefId").value(DEFAULT_RECEIVER_REF_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()))
            .andExpect(jsonPath("$.hasPrivateActive").value(DEFAULT_HAS_PRIVATE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.schoolId").value(DEFAULT_SCHOOL_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getMessageArchivesByIdFiltering() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        Long id = messageArchive.getId();

        defaultMessageArchiveFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMessageArchiveFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMessageArchiveFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where message equals to
        defaultMessageArchiveFiltering("message.equals=" + DEFAULT_MESSAGE, "message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where message in
        defaultMessageArchiveFiltering("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE, "message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where message is not null
        defaultMessageArchiveFiltering("message.specified=true", "message.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByMessageContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where message contains
        defaultMessageArchiveFiltering("message.contains=" + DEFAULT_MESSAGE, "message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where message does not contain
        defaultMessageArchiveFiltering("message.doesNotContain=" + UPDATED_MESSAGE, "message.doesNotContain=" + DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasReadIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasRead equals to
        defaultMessageArchiveFiltering("hasRead.equals=" + DEFAULT_HAS_READ, "hasRead.equals=" + UPDATED_HAS_READ);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasReadIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasRead in
        defaultMessageArchiveFiltering("hasRead.in=" + DEFAULT_HAS_READ + "," + UPDATED_HAS_READ, "hasRead.in=" + UPDATED_HAS_READ);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasRead is not null
        defaultMessageArchiveFiltering("hasRead.specified=true", "hasRead.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasEmergAlertIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasEmergAlert equals to
        defaultMessageArchiveFiltering(
            "hasEmergAlert.equals=" + DEFAULT_HAS_EMERG_ALERT,
            "hasEmergAlert.equals=" + UPDATED_HAS_EMERG_ALERT
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasEmergAlertIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasEmergAlert in
        defaultMessageArchiveFiltering(
            "hasEmergAlert.in=" + DEFAULT_HAS_EMERG_ALERT + "," + UPDATED_HAS_EMERG_ALERT,
            "hasEmergAlert.in=" + UPDATED_HAS_EMERG_ALERT
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasEmergAlertIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasEmergAlert is not null
        defaultMessageArchiveFiltering("hasEmergAlert.specified=true", "hasEmergAlert.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasSignOutIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasSignOut equals to
        defaultMessageArchiveFiltering("hasSignOut.equals=" + DEFAULT_HAS_SIGN_OUT, "hasSignOut.equals=" + UPDATED_HAS_SIGN_OUT);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasSignOutIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasSignOut in
        defaultMessageArchiveFiltering(
            "hasSignOut.in=" + DEFAULT_HAS_SIGN_OUT + "," + UPDATED_HAS_SIGN_OUT,
            "hasSignOut.in=" + UPDATED_HAS_SIGN_OUT
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasSignOutIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasSignOut is not null
        defaultMessageArchiveFiltering("hasSignOut.specified=true", "hasSignOut.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefTable equals to
        defaultMessageArchiveFiltering(
            "senderRefTable.equals=" + DEFAULT_SENDER_REF_TABLE,
            "senderRefTable.equals=" + UPDATED_SENDER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefTable in
        defaultMessageArchiveFiltering(
            "senderRefTable.in=" + DEFAULT_SENDER_REF_TABLE + "," + UPDATED_SENDER_REF_TABLE,
            "senderRefTable.in=" + UPDATED_SENDER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefTable is not null
        defaultMessageArchiveFiltering("senderRefTable.specified=true", "senderRefTable.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefTableContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefTable contains
        defaultMessageArchiveFiltering(
            "senderRefTable.contains=" + DEFAULT_SENDER_REF_TABLE,
            "senderRefTable.contains=" + UPDATED_SENDER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefTable does not contain
        defaultMessageArchiveFiltering(
            "senderRefTable.doesNotContain=" + UPDATED_SENDER_REF_TABLE,
            "senderRefTable.doesNotContain=" + DEFAULT_SENDER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId equals to
        defaultMessageArchiveFiltering("senderRefId.equals=" + DEFAULT_SENDER_REF_ID, "senderRefId.equals=" + UPDATED_SENDER_REF_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId in
        defaultMessageArchiveFiltering(
            "senderRefId.in=" + DEFAULT_SENDER_REF_ID + "," + UPDATED_SENDER_REF_ID,
            "senderRefId.in=" + UPDATED_SENDER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId is not null
        defaultMessageArchiveFiltering("senderRefId.specified=true", "senderRefId.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId is greater than or equal to
        defaultMessageArchiveFiltering(
            "senderRefId.greaterThanOrEqual=" + DEFAULT_SENDER_REF_ID,
            "senderRefId.greaterThanOrEqual=" + UPDATED_SENDER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId is less than or equal to
        defaultMessageArchiveFiltering(
            "senderRefId.lessThanOrEqual=" + DEFAULT_SENDER_REF_ID,
            "senderRefId.lessThanOrEqual=" + SMALLER_SENDER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId is less than
        defaultMessageArchiveFiltering("senderRefId.lessThan=" + UPDATED_SENDER_REF_ID, "senderRefId.lessThan=" + DEFAULT_SENDER_REF_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySenderRefIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where senderRefId is greater than
        defaultMessageArchiveFiltering(
            "senderRefId.greaterThan=" + SMALLER_SENDER_REF_ID,
            "senderRefId.greaterThan=" + DEFAULT_SENDER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefTable equals to
        defaultMessageArchiveFiltering(
            "receiverRefTable.equals=" + DEFAULT_RECEIVER_REF_TABLE,
            "receiverRefTable.equals=" + UPDATED_RECEIVER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefTable in
        defaultMessageArchiveFiltering(
            "receiverRefTable.in=" + DEFAULT_RECEIVER_REF_TABLE + "," + UPDATED_RECEIVER_REF_TABLE,
            "receiverRefTable.in=" + UPDATED_RECEIVER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefTable is not null
        defaultMessageArchiveFiltering("receiverRefTable.specified=true", "receiverRefTable.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefTableContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefTable contains
        defaultMessageArchiveFiltering(
            "receiverRefTable.contains=" + DEFAULT_RECEIVER_REF_TABLE,
            "receiverRefTable.contains=" + UPDATED_RECEIVER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefTable does not contain
        defaultMessageArchiveFiltering(
            "receiverRefTable.doesNotContain=" + UPDATED_RECEIVER_REF_TABLE,
            "receiverRefTable.doesNotContain=" + DEFAULT_RECEIVER_REF_TABLE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId equals to
        defaultMessageArchiveFiltering(
            "receiverRefId.equals=" + DEFAULT_RECEIVER_REF_ID,
            "receiverRefId.equals=" + UPDATED_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId in
        defaultMessageArchiveFiltering(
            "receiverRefId.in=" + DEFAULT_RECEIVER_REF_ID + "," + UPDATED_RECEIVER_REF_ID,
            "receiverRefId.in=" + UPDATED_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId is not null
        defaultMessageArchiveFiltering("receiverRefId.specified=true", "receiverRefId.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId is greater than or equal to
        defaultMessageArchiveFiltering(
            "receiverRefId.greaterThanOrEqual=" + DEFAULT_RECEIVER_REF_ID,
            "receiverRefId.greaterThanOrEqual=" + UPDATED_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId is less than or equal to
        defaultMessageArchiveFiltering(
            "receiverRefId.lessThanOrEqual=" + DEFAULT_RECEIVER_REF_ID,
            "receiverRefId.lessThanOrEqual=" + SMALLER_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId is less than
        defaultMessageArchiveFiltering(
            "receiverRefId.lessThan=" + UPDATED_RECEIVER_REF_ID,
            "receiverRefId.lessThan=" + DEFAULT_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByReceiverRefIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where receiverRefId is greater than
        defaultMessageArchiveFiltering(
            "receiverRefId.greaterThan=" + SMALLER_RECEIVER_REF_ID,
            "receiverRefId.greaterThan=" + DEFAULT_RECEIVER_REF_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where status equals to
        defaultMessageArchiveFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where status in
        defaultMessageArchiveFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where status is not null
        defaultMessageArchiveFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStatusContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where status contains
        defaultMessageArchiveFiltering("status.contains=" + DEFAULT_STATUS, "status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where status does not contain
        defaultMessageArchiveFiltering("status.doesNotContain=" + UPDATED_STATUS, "status.doesNotContain=" + DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId equals to
        defaultMessageArchiveFiltering("studentId.equals=" + DEFAULT_STUDENT_ID, "studentId.equals=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId in
        defaultMessageArchiveFiltering(
            "studentId.in=" + DEFAULT_STUDENT_ID + "," + UPDATED_STUDENT_ID,
            "studentId.in=" + UPDATED_STUDENT_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId is not null
        defaultMessageArchiveFiltering("studentId.specified=true", "studentId.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId is greater than or equal to
        defaultMessageArchiveFiltering(
            "studentId.greaterThanOrEqual=" + DEFAULT_STUDENT_ID,
            "studentId.greaterThanOrEqual=" + UPDATED_STUDENT_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId is less than or equal to
        defaultMessageArchiveFiltering(
            "studentId.lessThanOrEqual=" + DEFAULT_STUDENT_ID,
            "studentId.lessThanOrEqual=" + SMALLER_STUDENT_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId is less than
        defaultMessageArchiveFiltering("studentId.lessThan=" + UPDATED_STUDENT_ID, "studentId.lessThan=" + DEFAULT_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByStudentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where studentId is greater than
        defaultMessageArchiveFiltering("studentId.greaterThan=" + SMALLER_STUDENT_ID, "studentId.greaterThan=" + DEFAULT_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasPrivateActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasPrivateActive equals to
        defaultMessageArchiveFiltering(
            "hasPrivateActive.equals=" + DEFAULT_HAS_PRIVATE_ACTIVE,
            "hasPrivateActive.equals=" + UPDATED_HAS_PRIVATE_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasPrivateActiveIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasPrivateActive in
        defaultMessageArchiveFiltering(
            "hasPrivateActive.in=" + DEFAULT_HAS_PRIVATE_ACTIVE + "," + UPDATED_HAS_PRIVATE_ACTIVE,
            "hasPrivateActive.in=" + UPDATED_HAS_PRIVATE_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByHasPrivateActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where hasPrivateActive is not null
        defaultMessageArchiveFiltering("hasPrivateActive.specified=true", "hasPrivateActive.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId equals to
        defaultMessageArchiveFiltering("schoolId.equals=" + DEFAULT_SCHOOL_ID, "schoolId.equals=" + UPDATED_SCHOOL_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId in
        defaultMessageArchiveFiltering("schoolId.in=" + DEFAULT_SCHOOL_ID + "," + UPDATED_SCHOOL_ID, "schoolId.in=" + UPDATED_SCHOOL_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId is not null
        defaultMessageArchiveFiltering("schoolId.specified=true", "schoolId.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId is greater than or equal to
        defaultMessageArchiveFiltering(
            "schoolId.greaterThanOrEqual=" + DEFAULT_SCHOOL_ID,
            "schoolId.greaterThanOrEqual=" + UPDATED_SCHOOL_ID
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId is less than or equal to
        defaultMessageArchiveFiltering("schoolId.lessThanOrEqual=" + DEFAULT_SCHOOL_ID, "schoolId.lessThanOrEqual=" + SMALLER_SCHOOL_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId is less than
        defaultMessageArchiveFiltering("schoolId.lessThan=" + UPDATED_SCHOOL_ID, "schoolId.lessThan=" + DEFAULT_SCHOOL_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesBySchoolIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where schoolId is greater than
        defaultMessageArchiveFiltering("schoolId.greaterThan=" + SMALLER_SCHOOL_ID, "schoolId.greaterThan=" + DEFAULT_SCHOOL_ID);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModified equals to
        defaultMessageArchiveFiltering("lastModified.equals=" + DEFAULT_LAST_MODIFIED, "lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModified in
        defaultMessageArchiveFiltering(
            "lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED,
            "lastModified.in=" + UPDATED_LAST_MODIFIED
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModified is not null
        defaultMessageArchiveFiltering("lastModified.specified=true", "lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModifiedBy equals to
        defaultMessageArchiveFiltering(
            "lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModifiedBy in
        defaultMessageArchiveFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModifiedBy is not null
        defaultMessageArchiveFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModifiedBy contains
        defaultMessageArchiveFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMessageArchivesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        // Get all the messageArchiveList where lastModifiedBy does not contain
        defaultMessageArchiveFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    private void defaultMessageArchiveFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMessageArchiveShouldBeFound(shouldBeFound);
        defaultMessageArchiveShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageArchiveShouldBeFound(String filter) throws Exception {
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageArchive.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].hasRead").value(hasItem(DEFAULT_HAS_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEmergAlert").value(hasItem(DEFAULT_HAS_EMERG_ALERT.booleanValue())))
            .andExpect(jsonPath("$.[*].hasSignOut").value(hasItem(DEFAULT_HAS_SIGN_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].senderRefTable").value(hasItem(DEFAULT_SENDER_REF_TABLE)))
            .andExpect(jsonPath("$.[*].senderRefId").value(hasItem(DEFAULT_SENDER_REF_ID.intValue())))
            .andExpect(jsonPath("$.[*].receiverRefTable").value(hasItem(DEFAULT_RECEIVER_REF_TABLE)))
            .andExpect(jsonPath("$.[*].receiverRefId").value(hasItem(DEFAULT_RECEIVER_REF_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasPrivateActive").value(hasItem(DEFAULT_HAS_PRIVATE_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].schoolId").value(hasItem(DEFAULT_SCHOOL_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageArchiveShouldNotBeFound(String filter) throws Exception {
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageArchiveMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMessageArchive() throws Exception {
        // Get the messageArchive
        restMessageArchiveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMessageArchive() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageArchive
        MessageArchive updatedMessageArchive = messageArchiveRepository.findById(messageArchive.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMessageArchive are not directly saved in db
        em.detach(updatedMessageArchive);
        updatedMessageArchive
            .message(UPDATED_MESSAGE)
            .hasRead(UPDATED_HAS_READ)
            .hasEmergAlert(UPDATED_HAS_EMERG_ALERT)
            .hasSignOut(UPDATED_HAS_SIGN_OUT)
            .senderRefTable(UPDATED_SENDER_REF_TABLE)
            .senderRefId(UPDATED_SENDER_REF_ID)
            .receiverRefTable(UPDATED_RECEIVER_REF_TABLE)
            .receiverRefId(UPDATED_RECEIVER_REF_ID)
            .status(UPDATED_STATUS)
            .studentId(UPDATED_STUDENT_ID)
            .hasPrivateActive(UPDATED_HAS_PRIVATE_ACTIVE)
            .schoolId(UPDATED_SCHOOL_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(updatedMessageArchive);

        restMessageArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageArchiveDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isOk());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMessageArchiveToMatchAllProperties(updatedMessageArchive);
    }

    @Test
    @Transactional
    void putNonExistingMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageArchiveDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageArchiveWithPatch() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageArchive using partial update
        MessageArchive partialUpdatedMessageArchive = new MessageArchive();
        partialUpdatedMessageArchive.setId(messageArchive.getId());

        partialUpdatedMessageArchive
            .hasRead(UPDATED_HAS_READ)
            .hasEmergAlert(UPDATED_HAS_EMERG_ALERT)
            .senderRefTable(UPDATED_SENDER_REF_TABLE)
            .senderRefId(UPDATED_SENDER_REF_ID)
            .receiverRefTable(UPDATED_RECEIVER_REF_TABLE)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED);

        restMessageArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageArchive.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMessageArchive))
            )
            .andExpect(status().isOk());

        // Validate the MessageArchive in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMessageArchiveUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMessageArchive, messageArchive),
            getPersistedMessageArchive(messageArchive)
        );
    }

    @Test
    @Transactional
    void fullUpdateMessageArchiveWithPatch() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the messageArchive using partial update
        MessageArchive partialUpdatedMessageArchive = new MessageArchive();
        partialUpdatedMessageArchive.setId(messageArchive.getId());

        partialUpdatedMessageArchive
            .message(UPDATED_MESSAGE)
            .hasRead(UPDATED_HAS_READ)
            .hasEmergAlert(UPDATED_HAS_EMERG_ALERT)
            .hasSignOut(UPDATED_HAS_SIGN_OUT)
            .senderRefTable(UPDATED_SENDER_REF_TABLE)
            .senderRefId(UPDATED_SENDER_REF_ID)
            .receiverRefTable(UPDATED_RECEIVER_REF_TABLE)
            .receiverRefId(UPDATED_RECEIVER_REF_ID)
            .status(UPDATED_STATUS)
            .studentId(UPDATED_STUDENT_ID)
            .hasPrivateActive(UPDATED_HAS_PRIVATE_ACTIVE)
            .schoolId(UPDATED_SCHOOL_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restMessageArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageArchive.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMessageArchive))
            )
            .andExpect(status().isOk());

        // Validate the MessageArchive in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMessageArchiveUpdatableFieldsEquals(partialUpdatedMessageArchive, getPersistedMessageArchive(partialUpdatedMessageArchive));
    }

    @Test
    @Transactional
    void patchNonExistingMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageArchiveDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageArchive() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        messageArchive.setId(longCount.incrementAndGet());

        // Create the MessageArchive
        MessageArchiveDTO messageArchiveDTO = messageArchiveMapper.toDto(messageArchive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageArchiveMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(messageArchiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageArchive in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageArchive() throws Exception {
        // Initialize the database
        messageArchiveRepository.saveAndFlush(messageArchive);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the messageArchive
        restMessageArchiveMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageArchive.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return messageArchiveRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MessageArchive getPersistedMessageArchive(MessageArchive messageArchive) {
        return messageArchiveRepository.findById(messageArchive.getId()).orElseThrow();
    }

    protected void assertPersistedMessageArchiveToMatchAllProperties(MessageArchive expectedMessageArchive) {
        assertMessageArchiveAllPropertiesEquals(expectedMessageArchive, getPersistedMessageArchive(expectedMessageArchive));
    }

    protected void assertPersistedMessageArchiveToMatchUpdatableProperties(MessageArchive expectedMessageArchive) {
        assertMessageArchiveAllUpdatablePropertiesEquals(expectedMessageArchive, getPersistedMessageArchive(expectedMessageArchive));
    }
}
