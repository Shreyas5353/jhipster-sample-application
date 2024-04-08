package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MessageArchive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MessageArchive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, Long>, JpaSpecificationExecutor<MessageArchive> {}
