package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reg_rkk_attachment", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegRkkAttachment {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "REG_RKK_ATTACHMENT_GEN", sequenceName = "reg_rkk_attachment_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_RKK_ATTACHMENT_GEN")
    private Long id;
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Basic
    @Column(name = "number_attachment")
    private String numberAttachment;
    public String getNumberAttachment() {
        return numberAttachment;
    }
    public void setNumberAttachment(String numberAttachment) {
        this.numberAttachment = numberAttachment;
    }

    @Basic
    @Column(name = "signing_date")
    private Date signingDate;
    public Date getSigningDate() {
        return signingDate;
    }
    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    @OneToOne
    @JoinColumn(name = "id_rkk", referencedColumnName = "id")
    private DocRkk docRkk;
    public DocRkk getDocRkk() {return docRkk;}
    public void setDocRkk(DocRkk docRkk) {this.docRkk = docRkk;}

    @OneToOne
    @JoinColumn(name = "id_group", referencedColumnName = "id")
    private ClsGroupAttachment group;
    public ClsGroupAttachment getGroup() {return group;}
    public void setGroup(ClsGroupAttachment group) {this.group = group;}

    @OneToOne
    @JoinColumn(name = "id_type", referencedColumnName = "id")
    private ClsTypeAttachment type;
    public ClsTypeAttachment getType() {return type;}
    public void setType(ClsTypeAttachment type) {this.type = type;}

    @OneToOne
    @JoinColumn(name = "id_participant", referencedColumnName = "id")
    private ClsOrganization participant;
    public ClsOrganization getParticipant() {return participant;}
    public void setParticipant(ClsOrganization participant) {this.participant = participant;}

    @OneToOne
    @JoinColumn(name = "id_rkk_file", referencedColumnName = "id")
    private RegRkkFile file;
    public RegRkkFile getFile() {return file;}
    public void setFile(RegRkkFile file) {this.file = file;}

    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}

