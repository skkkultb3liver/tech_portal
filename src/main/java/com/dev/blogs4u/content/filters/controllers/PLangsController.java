package com.dev.blogs4u.content.filters.controllers;

import com.dev.blogs4u.content.filters.dto.CreatePLangRequest;
import com.dev.blogs4u.content.filters.entities.PLang;
import com.dev.blogs4u.content.filters.services.IPLangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plangs")
@RequiredArgsConstructor
public class PLangsController {

    private final IPLangService pLangService;

    @PostMapping("/create")
    public ResponseEntity<List<PLang>> createPLangsHandler(@RequestBody List<CreatePLangRequest> requests) {
        List<PLang> pLangs = pLangService.saveMultiplePLangs(requests);

        return new ResponseEntity<>(pLangs, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PLang>> getAllPLangsHandler() {
        return new ResponseEntity<>(pLangService.findAllPLangs(), HttpStatus.FOUND);
    }

}
