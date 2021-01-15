package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import ru.sibdigital.lexpro.model.DocRkk;

public interface RkkService {

    Page<DocRkk> findAllDocRkk(int page, int size);

}
