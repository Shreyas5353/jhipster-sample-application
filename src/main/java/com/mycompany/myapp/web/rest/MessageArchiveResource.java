package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MessageArchiveRepository;
import com.mycompany.myapp.service.MessageArchiveQueryService;
import com.mycompany.myapp.service.MessageArchiveService;
import com.mycompany.myapp.service.criteria.MessageArchiveCriteria;
import com.mycompany.myapp.service.dto.MessageArchiveDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MessageArchive}.
 */
@RestController
@RequestMapping("/api/message-archives")
public class MessageArchiveResource {

    private final Logger log = LoggerFactory.getLogger(MessageArchiveResource.class);

    private static final String ENTITY_NAME = "messageArchive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageArchiveService messageArchiveService;

    private final MessageArchiveRepository messageArchiveRepository;

    private final MessageArchiveQueryService messageArchiveQueryService;

    public MessageArchiveResource(
        MessageArchiveService messageArchiveService,
        MessageArchiveRepository messageArchiveRepository,
        MessageArchiveQueryService messageArchiveQueryService
    ) {
        this.messageArchiveService = messageArchiveService;
        this.messageArchiveRepository = messageArchiveRepository;
        this.messageArchiveQueryService = messageArchiveQueryService;
    }

    /**
     * {@code POST  /message-archives} : Create a new messageArchive.
     *
     * @param messageArchiveDTO the messageArchiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageArchiveDTO, or with status {@code 400 (Bad Request)} if the messageArchive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MessageArchiveDTO> createMessageArchive(@RequestBody MessageArchiveDTO messageArchiveDTO)
        throws URISyntaxException {
        log.debug("REST request to save MessageArchive : {}", messageArchiveDTO);
        if (messageArchiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageArchive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        messageArchiveDTO = messageArchiveService.save(messageArchiveDTO);
        return ResponseEntity.created(new URI("/api/message-archives/" + messageArchiveDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, messageArchiveDTO.getId().toString()))
            .body(messageArchiveDTO);
    }

    /**
     * {@code PUT  /message-archives/:id} : Updates an existing messageArchive.
     *
     * @param id the id of the messageArchiveDTO to save.
     * @param messageArchiveDTO the messageArchiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageArchiveDTO,
     * or with status {@code 400 (Bad Request)} if the messageArchiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageArchiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MessageArchiveDTO> updateMessageArchive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageArchiveDTO messageArchiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MessageArchive : {}, {}", id, messageArchiveDTO);
        if (messageArchiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageArchiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageArchiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        messageArchiveDTO = messageArchiveService.update(messageArchiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageArchiveDTO.getId().toString()))
            .body(messageArchiveDTO);
    }

    /**
     * {@code PATCH  /message-archives/:id} : Partial updates given fields of an existing messageArchive, field will ignore if it is null
     *
     * @param id the id of the messageArchiveDTO to save.
     * @param messageArchiveDTO the messageArchiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageArchiveDTO,
     * or with status {@code 400 (Bad Request)} if the messageArchiveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the messageArchiveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageArchiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MessageArchiveDTO> partialUpdateMessageArchive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageArchiveDTO messageArchiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessageArchive partially : {}, {}", id, messageArchiveDTO);
        if (messageArchiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageArchiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageArchiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageArchiveDTO> result = messageArchiveService.partialUpdate(messageArchiveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageArchiveDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /message-archives} : get all the messageArchives.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageArchives in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MessageArchiveDTO>> getAllMessageArchives(
        MessageArchiveCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MessageArchives by criteria: {}", criteria);

        Page<MessageArchiveDTO> page = messageArchiveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-archives/count} : count all the messageArchives.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMessageArchives(MessageArchiveCriteria criteria) {
        log.debug("REST request to count MessageArchives by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageArchiveQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /message-archives/:id} : get the "id" messageArchive.
     *
     * @param id the id of the messageArchiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageArchiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageArchiveDTO> getMessageArchive(@PathVariable("id") Long id) {
        log.debug("REST request to get MessageArchive : {}", id);
        Optional<MessageArchiveDTO> messageArchiveDTO = messageArchiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageArchiveDTO);
    }

    /**
     * {@code DELETE  /message-archives/:id} : delete the "id" messageArchive.
     *
     * @param id the id of the messageArchiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageArchive(@PathVariable("id") Long id) {
        log.debug("REST request to delete MessageArchive : {}", id);
        messageArchiveService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
