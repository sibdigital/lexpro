package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.model.DocRkk;

@Service
public class RkkServiceImpl extends SuperServiceImpl implements RkkService{

    public Page<DocRkk> findAllDocRkk(int page, int size) {
        return docRkkRepo.findAll(PageRequest.of(page, size, Sort.by("registrationDate")));
    }
}
