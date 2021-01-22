package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reg_role_privilege", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegRolePrivilege {
        @Id
        @Column(name = "id", nullable = false)
        @SequenceGenerator(name = "REG_ROLE_PRIVILEGE_GEN", sequenceName = "reg_role_privilege_id_seq", allocationSize = 1, schema = "public")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_ROLE_PRIVILEGE_GEN")
        private Integer id;
        public Integer getId() {return id;}
        public void setId(Integer id) {this.id = id;}

        @OneToOne
        @JoinColumn(name = "id_role", referencedColumnName = "id")
        private ClsRole role;
        public ClsRole getRole() {return role;}
        public void setRole(ClsRole role) {this.role = role;}

        @OneToOne
        @JoinColumn(name = "id_privilege", referencedColumnName = "id")
        private ClsPrivilege privilege;
        public ClsPrivilege getPrivilege() {return privilege;}
        public void setPrivilege(ClsPrivilege privilege) {this.privilege = privilege;}

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                RegRolePrivilege that = (RegRolePrivilege) o;
                return Objects.equals(id, that.id) &&
                        Objects.equals(role, that.role) &&
                        Objects.equals(privilege, that.privilege);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, role, privilege);
        }
}
