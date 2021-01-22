package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cls_privilege", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClsPrivilege implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "CLS_PRIVILEGE_SEQ_GEN", sequenceName = "cls_privilege_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLS_PRIVILEGE_SEQ_GEN")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClsPrivilege clsRole = (ClsPrivilege) o;
        return Objects.equals(id, clsRole.id) &&
                Objects.equals(name, clsRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
