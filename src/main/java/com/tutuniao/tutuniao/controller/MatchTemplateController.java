package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.service.MatchTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchTemplateController {

    @Autowired
    private MatchTemplateService matchTemplateService;


}
