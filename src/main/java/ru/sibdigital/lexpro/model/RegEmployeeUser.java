package ru.sibdigital.lexpro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reg_employee_user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegEmployeeUser {
        @Id
        @Column(name = "id", nullable = false)
        @SequenceGenerator(name = "REG_EMPLOYEE_USER_GEN", sequenceName = "reg_employee_user_id_seq", allocationSize = 1, schema = "public")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REG_EMPLOYEE_USER_GEN")
        private Integer id;
        public Integer getId() {return id;}
        public void setId(Integer id) {this.id = id;}


        @OneToOne
        @JoinColumn(name = "id_user", referencedColumnName = "id")
        private ClsUser user;
        public ClsUser getUser() {return user;}
        public void setUser(ClsUser user) {this.user = user;}


        @OneToOne
        @JoinColumn(name = "id_employee", referencedColumnName = "id")
        private ClsEmployee employee;
        public ClsEmployee getEmployee() {return employee;}
        public void setEmployee(ClsEmployee employee) {this.employee = employee;}

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                RegEmployeeUser that = (RegEmployeeUser) o;
                return Objects.equals(id, that.id) &&
                        Objects.equals(user, that.user) &&
                        Objects.equals(employee, that.employee);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, user, employee);
        }
}
