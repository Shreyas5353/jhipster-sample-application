package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MessageArchive.
 */
@Entity
@Table(name = "message_archive")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageArchive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "has_read")
    private Boolean hasRead;

    @Column(name = "has_emerg_alert")
    private Boolean hasEmergAlert;

    @Column(name = "has_sign_out")
    private Boolean hasSignOut;

    @Column(name = "sender_ref_table")
    private String senderRefTable;

    @Column(name = "sender_ref_id")
    private Long senderRefId;

    @Column(name = "receiver_ref_table")
    private String receiverRefTable;

    @Column(name = "receiver_ref_id")
    private Long receiverRefId;

    @Column(name = "status")
    private String status;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "has_private_active")
    private Boolean hasPrivateActive;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MessageArchive id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public MessageArchive message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasRead() {
        return this.hasRead;
    }

    public MessageArchive hasRead(Boolean hasRead) {
        this.setHasRead(hasRead);
        return this;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public Boolean getHasEmergAlert() {
        return this.hasEmergAlert;
    }

    public MessageArchive hasEmergAlert(Boolean hasEmergAlert) {
        this.setHasEmergAlert(hasEmergAlert);
        return this;
    }

    public void setHasEmergAlert(Boolean hasEmergAlert) {
        this.hasEmergAlert = hasEmergAlert;
    }

    public Boolean getHasSignOut() {
        return this.hasSignOut;
    }

    public MessageArchive hasSignOut(Boolean hasSignOut) {
        this.setHasSignOut(hasSignOut);
        return this;
    }

    public void setHasSignOut(Boolean hasSignOut) {
        this.hasSignOut = hasSignOut;
    }

    public String getSenderRefTable() {
        return this.senderRefTable;
    }

    public MessageArchive senderRefTable(String senderRefTable) {
        this.setSenderRefTable(senderRefTable);
        return this;
    }

    public void setSenderRefTable(String senderRefTable) {
        this.senderRefTable = senderRefTable;
    }

    public Long getSenderRefId() {
        return this.senderRefId;
    }

    public MessageArchive senderRefId(Long senderRefId) {
        this.setSenderRefId(senderRefId);
        return this;
    }

    public void setSenderRefId(Long senderRefId) {
        this.senderRefId = senderRefId;
    }

    public String getReceiverRefTable() {
        return this.receiverRefTable;
    }

    public MessageArchive receiverRefTable(String receiverRefTable) {
        this.setReceiverRefTable(receiverRefTable);
        return this;
    }

    public void setReceiverRefTable(String receiverRefTable) {
        this.receiverRefTable = receiverRefTable;
    }

    public Long getReceiverRefId() {
        return this.receiverRefId;
    }

    public MessageArchive receiverRefId(Long receiverRefId) {
        this.setReceiverRefId(receiverRefId);
        return this;
    }

    public void setReceiverRefId(Long receiverRefId) {
        this.receiverRefId = receiverRefId;
    }

    public String getStatus() {
        return this.status;
    }

    public MessageArchive status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public MessageArchive studentId(Long studentId) {
        this.setStudentId(studentId);
        return this;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getHasPrivateActive() {
        return this.hasPrivateActive;
    }

    public MessageArchive hasPrivateActive(Boolean hasPrivateActive) {
        this.setHasPrivateActive(hasPrivateActive);
        return this;
    }

    public void setHasPrivateActive(Boolean hasPrivateActive) {
        this.hasPrivateActive = hasPrivateActive;
    }

    public Long getSchoolId() {
        return this.schoolId;
    }

    public MessageArchive schoolId(Long schoolId) {
        this.setSchoolId(schoolId);
        return this;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MessageArchive lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MessageArchive lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageArchive)) {
            return false;
        }
        return getId() != null && getId().equals(((MessageArchive) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageArchive{" +
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
