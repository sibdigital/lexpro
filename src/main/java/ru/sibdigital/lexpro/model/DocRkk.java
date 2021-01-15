package ru.sibdigital.lexpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Date            deadline;
    private Integer         sessionNumber;
    private Date            sessionDate;
    private Boolean         includedInAgenta;

    @OneToOne
    @JoinColumn(name = "id_npa_type", referencedColumnName = "id")
    private ClsNpaType      npaType;

    @OneToOne
    @JoinColumn(name = "id_responsible_organization", referencedColumnName = "id")
    private ClsOrganization responsibleOrganization;

    @OneToOne
    @JoinColumn(name = "id_responsible_employee", referencedColumnName = "id")
    private ClsEmployee     employee;

    @OneToOne
    @JoinColumn(name = "id_status", referencedColumnName = "id")
    private ClsRkkStatus    status;

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
    @Column(name = "session_number")
    public Integer getSessionNumber() {
        return sessionNumber;
    }
    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    @Basic
    @Column(name = "session_date")
    public Date getSessionDate() {
        return sessionDate;
    }
    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    @Basic
    @Column(name = "included_in_agenta")
    public Boolean getIncludedInAgenta() {
        return includedInAgenta;
    }
    public void setIncludedInAgenta(Boolean includedInAgenta) {
        this.includedInAgenta = includedInAgenta;
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

    public ClsEmployee getEmployee() {
        return employee;
    }
    public void setEmployee(ClsEmployee employee) {
        this.employee = employee;
    }

    public ClsRkkStatus getStatus() {
        return status;
    }
    public void setStatus(ClsRkkStatus status) {
        this.status = status;
    }
}
