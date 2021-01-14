package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "reg_user_role", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegUserRole {
        @Id
        @Column(name = "id", nullable = false)
        @SequenceGenerator(name = "REG_USER_ROLE_GEN", sequenceName = "reg_user_role_id_seq", allocationSize = 1, schema = "public")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_USER_ROLE_GEN")
        private Integer id;
        public Integer getId() {return id;}
        public void setId(Integer id) {this.id = id;}


        @OneToOne
        @JoinColumn(name = "id_user", referencedColumnName = "id")
        private ClsUser user;
        public ClsUser getUser() {return user;}
        public void setUser(ClsUser user) {this.user = user;}


        @OneToOne
        @JoinColumn(name = "id_role", referencedColumnName = "id")
        private ClsRole role;
        public ClsRole getRole() {return role;}
        public void setRole(ClsRole role) {this.role = role;}
}
