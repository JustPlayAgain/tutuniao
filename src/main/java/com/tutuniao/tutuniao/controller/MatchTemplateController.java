package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchTemplateController {

    @Autowired
    private MatchTemplateService matchTemplateService;

    @PostMapping("/querymatchtemplatebyid")
    public Response queryMatchTemplateById(Integer mtId) {
        if (Utils.isNull(mtId)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(matchTemplateService.queryMatchTemplateById(mtId));
    }

    @PostMapping("/insertmatchtemplate")
    public Response insertMatchTemplate(MatchTemplate matchTemplate) {
        if (Utils.isNull(matchTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        // todo 缺少身份证号的校验
        if (Utils.isEmpty(matchTemplate.getStudentName())) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        int num = matchTemplateService.insertMatchTemplate(matchTemplate);
        if (num == 0) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.INSERT_DATA_ERROR);
        }
        return ResponseUtil.buildSuccessResponse();
    }

    @PostMapping("/deletematchtemplatebyid")
    public Response deleteMatchTemplateById(Integer mtId) {
        if (Utils.isNull(mtId)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        int num = matchTemplateService.deleteMatchTemplateById(mtId);
        if (num == 0) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.DELETE_DATA_ERROR);
        }
        return ResponseUtil.buildSuccessResponse();
    }

    @PostMapping("/updatematchtemplatebyid")
    public Response updateMatchTemplateById(MatchTemplate matchTemplate) {
        if (Utils.isNull(matchTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        int i = matchTemplateService.updateMatchTemplateById(matchTemplate);
        if (i == 0) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.DELETE_DATA_ERROR);
        }
        return ResponseUtil.buildSuccessResponse();
    }

    @PostMapping("/querymatchtemplatelist")
    public Response queryMatchTemplateList(MatchTemplate matchTemplate) {
        if (Utils.isNull(matchTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(matchTemplateService.queryMatchTemplateList(matchTemplate));
    }


}
