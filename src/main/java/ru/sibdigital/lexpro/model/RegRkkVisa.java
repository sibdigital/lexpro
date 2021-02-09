package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reg_rkk_visa", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegRkkVisa {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "REG_RKK_VISA_GEN", sequenceName = "reg_rkk_visa_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_RKK_VISA_GEN")
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @OneToOne
    @JoinColumn(name = "id_rkk", referencedColumnName = "id")
    private DocRkk docRkk;
    public DocRkk getDocRkk() {return docRkk;}
    public void setDocRkk(DocRkk docRkk) {this.docRkk = docRkk;}

    @OneToOne
    @JoinColumn(name = "id_stage", referencedColumnName = "id")
    private ClsRkkStage stage;
    public ClsRkkStage getStage() {return stage;}
    public void setStage(ClsRkkStage stage) {this.stage = stage;}


    @Basic
    @Column(name = "date")
    private Date date;
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "note", nullable = true, length = -1)
    private String note;
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }


}

