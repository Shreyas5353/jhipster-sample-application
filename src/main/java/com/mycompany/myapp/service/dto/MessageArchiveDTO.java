package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.MessageArchive} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageArchiveDTO implements Serializable {

    private Long id;

    private String message;

    private Boolean hasRead;

    private Boolean hasEmergAlert;

    private Boolean hasSignOut;

    private String senderRefTable;

    private Long senderRefId;

    private String receiverRefTable;

    private Long receiverRefId;

    private String status;

    private Long studentId;

    private Boolean hasPrivateActive;

    private Long schoolId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public Boolean getHasEmergAlert() {
        return hasEmergAlert;
    }

    public void setHasEmergAlert(Boolean hasEmergAlert) {
        this.hasEmergAlert = hasEmergAlert;
    }

    public Boolean getHasSignOut() {
        return hasSignOut;
    }

    public void setHasSignOut(Boolean hasSignOut) {
        this.hasSignOut = hasSignOut;
    }

    public String getSenderRefTable() {
        return senderRefTable;
    }

    public void setSenderRefTable(String senderRefTable) {
        this.senderRefTable = senderRefTable;
    }

    public Long getSenderRefId() {
        return senderRefId;
    }

    public void setSenderRefId(Long senderRefId) {
        this.senderRefId = senderRefId;
    }

    public String getReceiverRefTable() {
        return receiverRefTable;
    }

    public void setReceiverRefTable(String receiverRefTable) {
        this.receiverRefTable = receiverRefTable;
    }

    public Long getReceiverRefId() {
        return receiverRefId;
    }

    public void setReceiverRefId(Long receiverRefId) {
        this.receiverRefId = receiverRefId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getHasPrivateActive() {
        return hasPrivateActive;
    }

    public void setHasPrivateActive(Boolean hasPrivateActive) {
        this.hasPrivateActive = hasPrivateActive;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageArchiveDTO)) {
            return false;
        }

        MessageArchiveDTO messageArchiveDTO = (MessageArchiveDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageArchiveDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageArchiveDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", hasRead='" + getHasRead() + "'" +
            ", hasEmergAlert='" + getHasEmergAlert() + "'" +
            ", hasSignOut='" + getHasSignOut() + "'" +
            ", senderRefTable='" + getSenderRefTable() + "'" +
            ", senderRefId=" + getSenderRefId() +
            ", receiverRefTable='" + getReceiverRefTable() + "'" +
            ", receiverRefId=" + getReceiverRefId() +
            ", status='" + getStatus() + "'" +
            ", studentId=" + getStudentId() +
            ", hasPrivateActive='" + getHasPrivateActive() + "'" +
            ", schoolId=" + getSchoolId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
