package ru.sibdigital.lexpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.repository.DocRkkRepo;

@Service
public class SuperServiceImpl implements SuperService{

    @Autowired
    protected DocRkkRepo docRkkRepo;
}
