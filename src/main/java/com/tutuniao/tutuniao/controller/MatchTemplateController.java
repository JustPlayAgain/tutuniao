package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.common.filter.CommonFilter;
import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/match")
public class MatchTemplateController extends CommonFilter {

    @Autowired
    private MatchTemplateService matchTemplateService;

    @RequestMapping("/querymatchtemplatebyid")
    public Response queryMatchTemplateById(Integer mtId) {
        if (Utils.isNull(mtId)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(matchTemplateService.queryMatchTemplateById(mtId));
    }

    @RequestMapping("/insertmatchtemplate")
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

    @RequestMapping("/deletematchtemplatebyid")
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

    @RequestMapping("/updatematchtemplatebyid")
    public Response<String> updateMatchTemplateById(MatchTemplate matchTemplate) {
        if (Utils.isNull(matchTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        int i = matchTemplateService.updateMatchTemplateById(matchTemplate);
        if (i == 0) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.DELETE_DATA_ERROR);
        }
        return ResponseUtil.buildSuccessResponse();
    }

    @RequestMapping("/querymatchtemplatelist")
    public Response queryMatchTemplateList(@RequestBody MatchTemplate matchTemplate) {
        if (Utils.isNull(matchTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(matchTemplateService.queryMatchTemplateList(matchTemplate));
    }

    /**
     * 导入国美证书信息
     * @param file
     * @return
     */
    @PostMapping("/importMatchData")
    public Response importMatchData(@RequestParam("file") MultipartFile file){
        if (Utils.isNull(file)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        String name = file.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseUtil.buildResponse(matchTemplateService.importMatchData(inputStream,name));
    }

}
