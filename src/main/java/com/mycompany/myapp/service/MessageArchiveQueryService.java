package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.MessageArchive;
import com.mycompany.myapp.repository.MessageArchiveRepository;
import com.mycompany.myapp.service.criteria.MessageArchiveCriteria;
import com.mycompany.myapp.service.dto.MessageArchiveDTO;
import com.mycompany.myapp.service.mapper.MessageArchiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MessageArchive} entities in the database.
 * The main input is a {@link MessageArchiveCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MessageArchiveDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageArchiveQueryService extends QueryService<MessageArchive> {

    private final Logger log = LoggerFactory.getLogger(MessageArchiveQueryService.class);

    private final MessageArchiveRepository messageArchiveRepository;

    private final MessageArchiveMapper messageArchiveMapper;

    public MessageArchiveQueryService(MessageArchiveRepository messageArchiveRepository, MessageArchiveMapper messageArchiveMapper) {
        this.messageArchiveRepository = messageArchiveRepository;
        this.messageArchiveMapper = messageArchiveMapper;
    }

    /**
     * Return a {@link Page} of {@link MessageArchiveDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageArchiveDTO> findByCriteria(MessageArchiveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MessageArchive> specification = createSpecification(criteria);
        return messageArchiveRepository.findAll(specification, page).map(messageArchiveMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageArchiveCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MessageArchive> specification = createSpecification(criteria);
        return messageArchiveRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageArchiveCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MessageArchive> createSpecification(MessageArchiveCriteria criteria) {
        Specification<MessageArchive> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MessageArchive_.id));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), MessageArchive_.message));
            }
            if (criteria.getHasRead() != null) {
                specification = specification.and(buildSpecification(criteria.getHasRead(), MessageArchive_.hasRead));
            }
            if (criteria.getHasEmergAlert() != null) {
                specification = specification.and(buildSpecification(criteria.getHasEmergAlert(), MessageArchive_.hasEmergAlert));
            }
            if (criteria.getHasSignOut() != null) {
                specification = specification.and(buildSpecification(criteria.getHasSignOut(), MessageArchive_.hasSignOut));
            }
            if (criteria.getSenderRefTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenderRefTable(), MessageArchive_.senderRefTable));
            }
            if (criteria.getSenderRefId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSenderRefId(), MessageArchive_.senderRefId));
            }
            if (criteria.getReceiverRefTable() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getReceiverRefTable(), MessageArchive_.receiverRefTable)
                );
            }
            if (criteria.getReceiverRefId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiverRefId(), MessageArchive_.receiverRefId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MessageArchive_.status));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentId(), MessageArchive_.studentId));
            }
            if (criteria.getHasPrivateActive() != null) {
                specification = specification.and(buildSpecification(criteria.getHasPrivateActive(), MessageArchive_.hasPrivateActive));
            }
            if (criteria.getSchoolId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSchoolId(), MessageArchive_.schoolId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MessageArchive_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MessageArchive_.lastModifiedBy));
            }
        }
        return specification;
    }
}
