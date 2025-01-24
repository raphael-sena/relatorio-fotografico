package com.newenergy.inspecao_rei.controllers;

import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.services.InspecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inspecao")
public class InspecaoController {

    @Autowired
    private InspecaoService inspecaoService;

    @PostMapping
    public ResponseEntity<Void> criarInspecao(@RequestBody InspecaoCreateDTO obj) {

        inspecaoService.criarInspecao(obj);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
