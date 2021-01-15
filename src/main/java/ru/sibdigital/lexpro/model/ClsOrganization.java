package ru.sibdigital.lexpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cls_organization", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClsOrganization {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "CLS_ORG_GEN", sequenceName = "cls_organization_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLS_ORG_GEN")
    private Long id;
    private String name;
    private Integer statusImport;
    private Timestamp timeImport;
    private Timestamp timeCreate;
    private Integer idTypeOrganization;
    private Boolean isActivated;
    private Boolean isDeleted;

    public ClsOrganization(Long organizationId) {
        this.id = organizationId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status_import", nullable = false)
    public Integer getStatusImport() {
        return statusImport;
    }
    public void setStatusImport(Integer statusImport) {
        this.statusImport = statusImport;
    }

    @Basic
    @Column(name = "time_import", nullable = true)
    public Timestamp getTimeImport() {
        return timeImport;
    }
    public void setTimeImport(Timestamp timeImport) {
        this.timeImport = timeImport;
    }

    @Basic
    @Column(name = "id_type_organization")
    public Integer getIdTypeOrganization() {
        return idTypeOrganization;
    }
    public void setIdTypeOrganization(Integer idTypeOrganization) {
        this.idTypeOrganization = idTypeOrganization;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_create")
    public Timestamp getTimeCreate() {
        return timeCreate;
    }
    public void setTimeCreate(Timestamp timeCreate) {
        this.timeCreate = timeCreate;
    }

    @Basic
    @Column(name = "is_activated")
    public Boolean getActivated() {
        return isActivated;
    }
    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClsOrganization that = (ClsOrganization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(statusImport, that.statusImport) &&
                Objects.equals(timeImport, that.timeImport) &&
                Objects.equals(timeCreate, that.timeCreate) &&
                Objects.equals(idTypeOrganization, that.idTypeOrganization) &&
                Objects.equals(isActivated, that.isActivated) &&
                Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, statusImport, timeImport, timeCreate, idTypeOrganization, isActivated, isDeleted);
    }
}
