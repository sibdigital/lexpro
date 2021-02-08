package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFOldDocument;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Log4j2
@Service
public class FileServiceImpl extends SuperServiceImpl implements FileService{

    @Override
    public String getFileHash(File file){
        String result = "NOT";
        try {
            final byte[] bytes = Files.readAllBytes(file.toPath());
            byte[] hash = MessageDigest.getInstance("MD5").digest(bytes);
            result = DatatypeConverter.printHexBinary(hash);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
        }
        return result;
    }

    @Override
    public String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    @Override
    public Integer getPageCount(File file, String extension) {
        Integer pageCount = null;
        String filePath = file.getPath();
        try {
            if (extension.equals(".docx")) {
                XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));
                pageCount = docx.getProperties().getExtendedProperties().getPages();
                docx.close();
            } else if (extension.equals(".doc")) {
                HWPFDocument doc = new HWPFDocument(new FileInputStream(filePath));
                pageCount = doc.getSummaryInformation().getPageCount();
                doc.close();
            } else if (extension.equals(".pdf")) {
                PDDocument pdf = PDDocument.load(new File(filePath));
                pageCount = pdf.getNumberOfPages();
                pdf.close();
            } else if (extension.equals(".xls")) {
                HSSFWorkbook xls = new HSSFWorkbook(new FileInputStream(filePath));
                Integer sheetNums = xls.getNumberOfSheets();
                if (sheetNums > 0) {
                    pageCount = xls.getSheetAt(0).getRowBreaks().length + 1;
                }
                xls.close();
            } else if (extension.equals(".xlsx")) {
                XSSFWorkbook xlsx = new XSSFWorkbook(filePath);
                Integer sheetNums = xlsx.getNumberOfSheets();
                if (sheetNums > 0) {
                    pageCount = xlsx.getSheetAt(0).getRowBreaks().length + 1;
                }
                xlsx.close();
            } else if (extension.equals(".ppt")) {
                HSLFSlideShow ppt = new HSLFSlideShow(new FileInputStream(filePath));
                pageCount = ppt.getSlides().size();
                ppt.close();
            } else if (extension.equals(".pptx")) {
                XMLSlideShow pptxSlideShow = new XMLSlideShow(new FileInputStream(filePath));
                pageCount = pptxSlideShow.getSlides().size();
                pptxSlideShow.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage()); // Если doc формата 95 и ниже, то POI не может его прочитать
        }

        return pageCount;
    }

}
