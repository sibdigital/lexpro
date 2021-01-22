package ru.sibdigital.lexpro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.repository.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class SuperServiceImpl implements SuperService{

    @Autowired
    protected DocRkkRepo docRkkRepo;

    @Autowired
    protected ClsRkkStatusRepo clsRkkStatusRepo;

    @Autowired
    protected ClsNpaTypeRepo clsNpaTypeRepo;

    @Autowired
    protected ClsOrganizationRepo clsOrganizationRepo;

    @Autowired
    protected ClsEmployeeRepo clsEmployeeRepo;

    @Override
    public Date parseDateFromForm(String stringDate) {
        Date date = null;
        try {
            if (stringDate != null && !stringDate.equals("")) {
                date = new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(stringDate).getTime());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return date;
    }
}
