package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sibdigital.lexpro.model.DocRkk;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
public class RkkController extends SuperController {

    @GetMapping("/doc_rkks")
    public @ResponseBody Map<String, Object> getDocRkks(@RequestParam(value = "start", required = false) Integer start,
                                                        @RequestParam(value = "count", required = false) Integer count) {
        int page = start == null ? 0 : start / 25;
        int size = count == null ? 25 : count;

        Map<String, Object> result = new HashMap<>();
        Page<DocRkk> docRkks = rkkService.findAllDocRkk(page, size);

        result.put("data", docRkks.getContent());
        result.put("pos", (long) page * size);
        result.put("total_count", docRkks.getTotalElements());
        return result;
    }

    @GetMapping("/doc_rkk/{id_doc_rkk}")
    public @ResponseBody DocRkk getDocRkk(@PathVariable("id_doc_rkk") Long idDocRkk) {
        return docRkkRepo.findById(idDocRkk).orElse(null);
    }

}
