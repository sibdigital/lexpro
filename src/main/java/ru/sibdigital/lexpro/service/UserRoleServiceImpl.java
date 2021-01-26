package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.model.ClsUser;

import java.util.List;

@Service
public class UserRoleServiceImpl extends SuperServiceImpl implements UserRoleService {

    public List<ClsUser> getUserList() {
        return clsUserRepo.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
    }
}
