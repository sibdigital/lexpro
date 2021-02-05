package ru.sibdigital.lexpro.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.model.*;

public interface RkkFileService {

    RegRkkFile constructRkkFile(MultipartFile part, DocRkk docRkk, ClsEmployee operator);
}
