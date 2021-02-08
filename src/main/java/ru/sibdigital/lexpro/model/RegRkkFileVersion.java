package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "reg_rkk_file_version", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegRkkFileVersion {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "REG_RKK_FILE_VERSION_GEN", sequenceName = "reg_rkk_file_version_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_RKK_FILE_VERSION_GEN")
    private Long id;
    private Boolean isDeleted;
    private Timestamp timeCreate;
    private String attachmentPath;
    private String fileName;
    private String originalFileName;
    private String fileExtension;
    private String hash;
    private Long fileSize;
    private Integer pageCount;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @OneToOne
    @JoinColumn(name = "id_version", referencedColumnName = "id")
    private RegRkkFile rkkFile;
    public RegRkkFile getRkkFile() {return rkkFile;}
    public void setRkkFile(RegRkkFile rkkFile) {this.rkkFile = rkkFile;}

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
    @JoinColumn(name = "id_operator", referencedColumnName = "id")
    private ClsEmployee operator;
    public ClsEmployee getOperator() {return operator;}
    public void setOperator(ClsEmployee operator) {this.operator = operator;}

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

    @Basic
    @Column(name = "page_count")
    public Integer getPageCount() {
        return pageCount;
    }
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "is_deleted", nullable = true)
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_create", nullable = false)
    public Timestamp getTimeCreate() {
        return timeCreate;
    }
    public void setTimeCreate(Timestamp timeCreate) {
        this.timeCreate = timeCreate;
    }

    @Basic
    @Column(name = "attachment_path", nullable = true, length = -1)
    public String getAttachmentPath() {
        return attachmentPath;
    }
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    @Basic
    @Column(name = "file_name", nullable = true, length = -1)
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "original_file_name", nullable = true, length = -1)
    public String getOriginalFileName() {
        return originalFileName;
    }
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Basic
    @Column(name = "file_extension", nullable = true, length = 16)
    public String getFileExtension() {
        return fileExtension;
    }
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Basic
    @Column(name = "hash", nullable = true, length = -1)
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "file_size", nullable = true)
    public Long getFileSize() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public RegRkkFileVersion(RegRkkFile rkkFile) {
        this.rkkFile = rkkFile; // id_version
        this.docRkk = rkkFile.getDocRkk(); //id_rkk
        this.group = rkkFile.getGroup();    //id_group
        this.type = rkkFile.getType();  //id_type
        this.participant = rkkFile.getParticipant(); //id_participant
        this.numberAttachment = rkkFile.getNumberAttachment(); //number_attachment
        this.signingDate = rkkFile.getSigningDate(); //signing_date
        this.pageCount = rkkFile.getPageCount();    //page_count
        this.isDeleted = rkkFile.getDeleted();  //is_deleted
        this.timeCreate = rkkFile.getTimeCreate();  //time_create
        this.attachmentPath = rkkFile.getAttachmentPath(); //attachment_path
        this.fileName = rkkFile.getFileName();  //file_name
        this.originalFileName = rkkFile.getOriginalFileName(); //original_file_name
        this.fileExtension = rkkFile.getFileExtension();    //file_extension
        this.hash = rkkFile.getHash();  //hash
        this.fileSize = rkkFile.getFileSize(); // file_size
        this.operator = rkkFile.getOperator();  // id_operator
    }

}

