package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsRole;
import ru.sibdigital.lexpro.model.RegRolePrivilege;

import java.util.List;


@Repository
public interface RegRolePrivilegeRepo extends JpaRepository<RegRolePrivilege, Long> {

    List<RegRolePrivilege> findByRole(ClsRole role);
}
