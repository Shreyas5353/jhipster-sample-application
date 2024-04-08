package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MessageArchive;
import com.mycompany.myapp.repository.MessageArchiveRepository;
import com.mycompany.myapp.service.dto.MessageArchiveDTO;
import com.mycompany.myapp.service.mapper.MessageArchiveMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.MessageArchive}.
 */
@Service
@Transactional
public class MessageArchiveService {

    private final Logger log = LoggerFactory.getLogger(MessageArchiveService.class);

    private final MessageArchiveRepository messageArchiveRepository;

    private final MessageArchiveMapper messageArchiveMapper;

    public MessageArchiveService(MessageArchiveRepository messageArchiveRepository, MessageArchiveMapper messageArchiveMapper) {
        this.messageArchiveRepository = messageArchiveRepository;
        this.messageArchiveMapper = messageArchiveMapper;
    }

    /**
     * Save a messageArchive.
     *
     * @param messageArchiveDTO the entity to save.
     * @return the persisted entity.
     */
    public MessageArchiveDTO save(MessageArchiveDTO messageArchiveDTO) {
        log.debug("Request to save MessageArchive : {}", messageArchiveDTO);
        MessageArchive messageArchive = messageArchiveMapper.toEntity(messageArchiveDTO);
        messageArchive = messageArchiveRepository.save(messageArchive);
        return messageArchiveMapper.toDto(messageArchive);
    }

    /**
     * Update a messageArchive.
     *
     * @param messageArchiveDTO the entity to save.
     * @return the persisted entity.
     */
    public MessageArchiveDTO update(MessageArchiveDTO messageArchiveDTO) {
        log.debug("Request to update MessageArchive : {}", messageArchiveDTO);
        MessageArchive messageArchive = messageArchiveMapper.toEntity(messageArchiveDTO);
        messageArchive = messageArchiveRepository.save(messageArchive);
        return messageArchiveMapper.toDto(messageArchive);
    }

    /**
     * Partially update a messageArchive.
     *
     * @param messageArchiveDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MessageArchiveDTO> partialUpdate(MessageArchiveDTO messageArchiveDTO) {
        log.debug("Request to partially update MessageArchive : {}", messageArchiveDTO);

        return messageArchiveRepository
            .findById(messageArchiveDTO.getId())
            .map(existingMessageArchive -> {
                messageArchiveMapper.partialUpdate(existingMessageArchive, messageArchiveDTO);

                return existingMessageArchive;
            })
            .map(messageArchiveRepository::save)
            .map(messageArchiveMapper::toDto);
    }

    /**
     * Get one messageArchive by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessageArchiveDTO> findOne(Long id) {
        log.debug("Request to get MessageArchive : {}", id);
        return messageArchiveRepository.findById(id).map(messageArchiveMapper::toDto);
    }

    /**
     * Delete the messageArchive by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageArchive : {}", id);
        messageArchiveRepository.deleteById(id);
    }
}
