package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.MessageArchive} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MessageArchiveResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /message-archives?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageArchiveCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter message;

    private BooleanFilter hasRead;

    private BooleanFilter hasEmergAlert;

    private BooleanFilter hasSignOut;

    private StringFilter senderRefTable;

    private LongFilter senderRefId;

    private StringFilter receiverRefTable;

    private LongFilter receiverRefId;

    private StringFilter status;

    private LongFilter studentId;

    private BooleanFilter hasPrivateActive;

    private LongFilter schoolId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public MessageArchiveCriteria() {}

    public MessageArchiveCriteria(MessageArchiveCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.message = other.optionalMessage().map(StringFilter::copy).orElse(null);
        this.hasRead = other.optionalHasRead().map(BooleanFilter::copy).orElse(null);
        this.hasEmergAlert = other.optionalHasEmergAlert().map(BooleanFilter::copy).orElse(null);
        this.hasSignOut = other.optionalHasSignOut().map(BooleanFilter::copy).orElse(null);
        this.senderRefTable = other.optionalSenderRefTable().map(StringFilter::copy).orElse(null);
        this.senderRefId = other.optionalSenderRefId().map(LongFilter::copy).orElse(null);
        this.receiverRefTable = other.optionalReceiverRefTable().map(StringFilter::copy).orElse(null);
        this.receiverRefId = other.optionalReceiverRefId().map(LongFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.studentId = other.optionalStudentId().map(LongFilter::copy).orElse(null);
        this.hasPrivateActive = other.optionalHasPrivateActive().map(BooleanFilter::copy).orElse(null);
        this.schoolId = other.optionalSchoolId().map(LongFilter::copy).orElse(null);
        this.lastModified = other.optionalLastModified().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MessageArchiveCriteria copy() {
        return new MessageArchiveCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMessage() {
        return message;
    }

    public Optional<StringFilter> optionalMessage() {
        return Optional.ofNullable(message);
    }

    public StringFilter message() {
        if (message == null) {
            setMessage(new StringFilter());
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public BooleanFilter getHasRead() {
        return hasRead;
    }

    public Optional<BooleanFilter> optionalHasRead() {
        return Optional.ofNullable(hasRead);
    }

    public BooleanFilter hasRead() {
        if (hasRead == null) {
            setHasRead(new BooleanFilter());
        }
        return hasRead;
    }

    public void setHasRead(BooleanFilter hasRead) {
        this.hasRead = hasRead;
    }

    public BooleanFilter getHasEmergAlert() {
        return hasEmergAlert;
    }

    public Optional<BooleanFilter> optionalHasEmergAlert() {
        return Optional.ofNullable(hasEmergAlert);
    }

    public BooleanFilter hasEmergAlert() {
        if (hasEmergAlert == null) {
            setHasEmergAlert(new BooleanFilter());
        }
        return hasEmergAlert;
    }

    public void setHasEmergAlert(BooleanFilter hasEmergAlert) {
        this.hasEmergAlert = hasEmergAlert;
    }

    public BooleanFilter getHasSignOut() {
        return hasSignOut;
    }

    public Optional<BooleanFilter> optionalHasSignOut() {
        return Optional.ofNullable(hasSignOut);
    }

    public BooleanFilter hasSignOut() {
        if (hasSignOut == null) {
            setHasSignOut(new BooleanFilter());
        }
        return hasSignOut;
    }

    public void setHasSignOut(BooleanFilter hasSignOut) {
        this.hasSignOut = hasSignOut;
    }

    public StringFilter getSenderRefTable() {
        return senderRefTable;
    }

    public Optional<StringFilter> optionalSenderRefTable() {
        return Optional.ofNullable(senderRefTable);
    }

    public StringFilter senderRefTable() {
        if (senderRefTable == null) {
            setSenderRefTable(new StringFilter());
        }
        return senderRefTable;
    }

    public void setSenderRefTable(StringFilter senderRefTable) {
        this.senderRefTable = senderRefTable;
    }

    public LongFilter getSenderRefId() {
        return senderRefId;
    }

    public Optional<LongFilter> optionalSenderRefId() {
        return Optional.ofNullable(senderRefId);
    }

    public LongFilter senderRefId() {
        if (senderRefId == null) {
            setSenderRefId(new LongFilter());
        }
        return senderRefId;
    }

    public void setSenderRefId(LongFilter senderRefId) {
        this.senderRefId = senderRefId;
    }

    public StringFilter getReceiverRefTable() {
        return receiverRefTable;
    }

    public Optional<StringFilter> optionalReceiverRefTable() {
        return Optional.ofNullable(receiverRefTable);
    }

    public StringFilter receiverRefTable() {
        if (receiverRefTable == null) {
            setReceiverRefTable(new StringFilter());
        }
        return receiverRefTable;
    }

    public void setReceiverRefTable(StringFilter receiverRefTable) {
        this.receiverRefTable = receiverRefTable;
    }

    public LongFilter getReceiverRefId() {
        return receiverRefId;
    }

    public Optional<LongFilter> optionalReceiverRefId() {
        return Optional.ofNullable(receiverRefId);
    }

    public LongFilter receiverRefId() {
        if (receiverRefId == null) {
            setReceiverRefId(new LongFilter());
        }
        return receiverRefId;
    }

    public void setReceiverRefId(LongFilter receiverRefId) {
        this.receiverRefId = receiverRefId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public Optional<StringFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StringFilter status() {
        if (status == null) {
            setStatus(new StringFilter());
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public Optional<LongFilter> optionalStudentId() {
        return Optional.ofNullable(studentId);
    }

    public LongFilter studentId() {
        if (studentId == null) {
            setStudentId(new LongFilter());
        }
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public BooleanFilter getHasPrivateActive() {
        return hasPrivateActive;
    }

    public Optional<BooleanFilter> optionalHasPrivateActive() {
        return Optional.ofNullable(hasPrivateActive);
    }

    public BooleanFilter hasPrivateActive() {
        if (hasPrivateActive == null) {
            setHasPrivateActive(new BooleanFilter());
        }
        return hasPrivateActive;
    }

    public void setHasPrivateActive(BooleanFilter hasPrivateActive) {
        this.hasPrivateActive = hasPrivateActive;
    }

    public LongFilter getSchoolId() {
        return schoolId;
    }

    public Optional<LongFilter> optionalSchoolId() {
        return Optional.ofNullable(schoolId);
    }

    public LongFilter schoolId() {
        if (schoolId == null) {
            setSchoolId(new LongFilter());
        }
        return schoolId;
    }

    public void setSchoolId(LongFilter schoolId) {
        this.schoolId = schoolId;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public Optional<InstantFilter> optionalLastModified() {
        return Optional.ofNullable(lastModified);
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            setLastModified(new InstantFilter());
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Optional<StringFilter> optionalLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            setLastModifiedBy(new StringFilter());
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageArchiveCriteria that = (MessageArchiveCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(hasRead, that.hasRead) &&
            Objects.equals(hasEmergAlert, that.hasEmergAlert) &&
            Objects.equals(hasSignOut, that.hasSignOut) &&
            Objects.equals(senderRefTable, that.senderRefTable) &&
            Objects.equals(senderRefId, that.senderRefId) &&
            Objects.equals(receiverRefTable, that.receiverRefTable) &&
            Objects.equals(receiverRefId, that.receiverRefId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(hasPrivateActive, that.hasPrivateActive) &&
            Objects.equals(schoolId, that.schoolId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            message,
            hasRead,
            hasEmergAlert,
            hasSignOut,
            senderRefTable,
            senderRefId,
            receiverRefTable,
            receiverRefId,
            status,
            studentId,
            hasPrivateActive,
            schoolId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageArchiveCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMessage().map(f -> "message=" + f + ", ").orElse("") +
            optionalHasRead().map(f -> "hasRead=" + f + ", ").orElse("") +
            optionalHasEmergAlert().map(f -> "hasEmergAlert=" + f + ", ").orElse("") +
            optionalHasSignOut().map(f -> "hasSignOut=" + f + ", ").orElse("") +
            optionalSenderRefTable().map(f -> "senderRefTable=" + f + ", ").orElse("") +
            optionalSenderRefId().map(f -> "senderRefId=" + f + ", ").orElse("") +
            optionalReceiverRefTable().map(f -> "receiverRefTable=" + f + ", ").orElse("") +
            optionalReceiverRefId().map(f -> "receiverRefId=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalStudentId().map(f -> "studentId=" + f + ", ").orElse("") +
            optionalHasPrivateActive().map(f -> "hasPrivateActive=" + f + ", ").orElse("") +
            optionalSchoolId().map(f -> "schoolId=" + f + ", ").orElse("") +
            optionalLastModified().map(f -> "lastModified=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
