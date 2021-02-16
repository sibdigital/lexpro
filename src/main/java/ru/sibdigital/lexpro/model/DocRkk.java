package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "doc_rkk", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DocRkk implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "DOC_RKK_SEQ_GEN", sequenceName = "doc_rkk_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOC_RKK_SEQ_GEN")
    private Long            id;
    private String          rkkNumber;
    private String          npaName;
    private Date            registrationDate;
    private Date            introductionDate;
    private String          legislativeBasis;
    private Date            deadline;
    private Date            includedInAgenda;
    private Boolean         readyForSession;
    private String          agendaNumber;
    private Date            headSignature;
    private Date            publicationDate;
    private Boolean         isArchived;
    private Boolean         isDeleted;

    @OneToOne
    @JoinColumn(name = "id_npa_type", referencedColumnName = "id")
    private ClsNpaType      npaType;

    @OneToOne
    @JoinColumn(name = "id_responsible_organization", referencedColumnName = "id")
    private ClsOrganization responsibleOrganization;

    @OneToOne
    @JoinColumn(name = "id_responsible_employee", referencedColumnName = "id")
    private ClsEmployee     responsibleEmployee;

    @OneToOne
    @JoinColumn(name = "id_status", referencedColumnName = "id")
    private ClsRkkStatus    status;

    @OneToOne
    @JoinColumn(name = "id_law_subject", referencedColumnName = "id")
    private ClsOrganization lawSubject;

    @OneToOne
    @JoinColumn(name = "id_speaker", referencedColumnName = "id")
    private ClsEmployee speaker;

    @OneToOne
    @JoinColumn(name = "id_session", referencedColumnName = "id")
    private ClsSession session;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rkk_number")
    public String getRkkNumber() {
        return rkkNumber;
    }
    public void setRkkNumber(String rkkNumber) {
        this.rkkNumber = rkkNumber;
    }

    @Basic
    @Column(name = "npa_name")
    public String getNpaName() {
        return npaName;
    }
    public void setNpaName(String npaName) {
        this.npaName = npaName;
    }

    @Basic
    @Column(name = "registration_date")
    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Basic
    @Column(name = "introduction_date")
    public Date getIntroductionDate() {
        return introductionDate;
    }
    public void setIntroductionDate(Date introductionDate) {
        this.introductionDate = introductionDate;
    }

    @Basic
    @Column(name = "deadline")
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "legislative_basis")
    public String getLegislativeBasis() {
        return legislativeBasis;
    }
    public void setLegislativeBasis(String legislativeBasis) {
        this.legislativeBasis = legislativeBasis;
    }

    @Basic
    @Column(name = "included_in_agenda")
    public Date getIncludedInAgenda() {
        return includedInAgenda;
    }
    public void setIncludedInAgenda(Date includedInAgenda) {
        this.includedInAgenda = includedInAgenda;
    }

    @Basic
    @Column(name = "ready_for_session")
    public Boolean getReadyForSession() {
        return readyForSession;
    }
    public void setReadyForSession(Boolean readyForSession) {
        this.readyForSession = readyForSession;
    }

    @Basic
    @Column(name = "agenda_number")
    public String getAgendaNumber() {
        return agendaNumber;
    }
    public void setAgendaNumber(String agendaNumber) {
        this.agendaNumber = agendaNumber;
    }

    @Basic
    @Column(name = "head_signature")
    public Date getHeadSignature() {
        return headSignature;
    }
    public void setHeadSignature(Date headSignature) {
        this.headSignature = headSignature;
    }

    @Basic
    @Column(name = "publication_date")
    public Date getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ClsNpaType getNpaType() {
        return npaType;
    }
    public void setNpaType(ClsNpaType npaType) {
        this.npaType = npaType;
    }

    public ClsOrganization getResponsibleOrganization() {
        return responsibleOrganization;
    }
    public void setResponsibleOrganization(ClsOrganization responsibleOrganization) {
        this.responsibleOrganization = responsibleOrganization;
    }

    public ClsEmployee getResponsibleEmployee() {
        return responsibleEmployee;
    }
    public void setResponsibleEmployee(ClsEmployee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public ClsRkkStatus getStatus() {
        return status;
    }
    public void setStatus(ClsRkkStatus status) {
        this.status = status;
    }

    public ClsOrganization getLawSubject() {
        return lawSubject;
    }
    public void setLawSubject(ClsOrganization lawSubject) {
        this.lawSubject = lawSubject;
    }

    public ClsEmployee getSpeaker() {
        return speaker;
    }
    public void setSpeaker(ClsEmployee speaker) {
        this.speaker = speaker;
    }

    public ClsSession getSession() {
        return session;
    }
    public void setSession(ClsSession session) {
        this.session = session;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getIsDeletedDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Basic
    @Column(name = "is_archived")
    public Boolean getIsArchived() {
        return isArchived;
    }
    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocRkk docRkk = (DocRkk) o;
        return Objects.equals(id, docRkk.id) &&
                Objects.equals(rkkNumber, docRkk.rkkNumber) &&
                Objects.equals(npaName, docRkk.npaName) &&
                Objects.equals(registrationDate, docRkk.registrationDate) &&
                Objects.equals(introductionDate, docRkk.introductionDate) &&
                Objects.equals(legislativeBasis, docRkk.legislativeBasis) &&
                Objects.equals(deadline, docRkk.deadline) &&
                Objects.equals(includedInAgenda, docRkk.includedInAgenda) &&
                Objects.equals(readyForSession, docRkk.readyForSession) &&
                Objects.equals(npaType, docRkk.npaType) &&
                Objects.equals(responsibleOrganization, docRkk.responsibleOrganization) &&
                Objects.equals(responsibleEmployee, docRkk.responsibleEmployee) &&
                Objects.equals(status, docRkk.status) &&
                Objects.equals(lawSubject, docRkk.lawSubject) &&
                Objects.equals(speaker, docRkk.speaker) &&
                Objects.equals(session, docRkk.session) &&
                Objects.equals(isDeleted, docRkk.isDeleted) &&
                Objects.equals(isArchived, docRkk.isArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rkkNumber, npaName, registrationDate, introductionDate, legislativeBasis, deadline, includedInAgenda, readyForSession, npaType, responsibleOrganization, responsibleEmployee, status, lawSubject, speaker, session, isDeleted);
    }
}
