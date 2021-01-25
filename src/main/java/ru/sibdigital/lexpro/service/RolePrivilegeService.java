package ru.sibdigital.lexpro.service;

import ru.sibdigital.lexpro.model.ClsPrivilege;
import ru.sibdigital.lexpro.model.ClsRole;
import ru.sibdigital.lexpro.model.ClsUser;

import java.util.List;
import java.util.Set;

public interface RolePrivilegeService {

    List<ClsRole> getUserRoles(ClsUser clsUser);

    List<ClsPrivilege> getUserPrivileges(ClsUser user);

    Set<ClsPrivilege> getRolesPrivileges(List<ClsRole> userRoles);

    List<String> getUserRoleNames(ClsUser clsUser);

    List<String> getUserPrivilegeNames(ClsUser user);

}
