package ru.sibdigital.lexpro.service;

import java.io.File;

public interface FileService {

    String getFileHash(File file);
    String getFileExtension(String name);
    Integer getPageCount(File file, String extension);
}
