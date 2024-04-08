package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.MessageArchive;
import com.mycompany.myapp.service.dto.MessageArchiveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageArchive} and its DTO {@link MessageArchiveDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageArchiveMapper extends EntityMapper<MessageArchiveDTO, MessageArchive> {}
