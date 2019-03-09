package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/guomei")
public class GuoMeiTemplateController {

    @Autowired
    private GuoMeiTemplateService guoMeiTemplateService;

    /**
     * 用户接口查询国美证书列表 证书编号+姓名
     * @param guoMeiTemplate
     * @return
     */
    @PostMapping("/queryguomeitemplate")
    public Response queryGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate){
        if (Utils.isNull(guoMeiTemplate)
                && Utils.isEmpty(guoMeiTemplate.getCertificateNumber())
                && Utils.isEmpty(guoMeiTemplate.getStudentName())) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.GUOMEITEMPLATE_ERROR);
        }
        GuoMeiTemplate template = guoMeiTemplateService.queryGuoMeiTemplate(guoMeiTemplate);
        if (Utils.isNull(template)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.GUOMEITEMPLATE_NULL);
        }
        return ResponseUtil.buildResponse(template);
    }

    /**
     * 查询国美证书列表 支持模糊查询
     * @param guoMeiTemplate
     * @return
     */
    @RequestMapping("/queryguomeitemplatelist")
    public Response queryGuoMeiTemplateList(@RequestBody GuoMeiTemplate guoMeiTemplate){
        return ResponseUtil.buildResponse(guoMeiTemplateService.queryGuoMeiTemplateList(guoMeiTemplate));
    }

    /**
     * 新增国美证书信息
     * @param guoMeiTemplate
     * @return
     */
    @PostMapping("/insertguomeitemplate")
    public Response insertGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate){
        if (Utils.isNull(guoMeiTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(guoMeiTemplateService.insertGuoMeiTemplate(guoMeiTemplate));
    }

    /**
     * 修改国美证书信息
     * @param guoMeiTemplate
     * @return
     */
    @PostMapping("/updateguomeitemplatebyid")
    public Response updateGuoMeiTemplateById(GuoMeiTemplate guoMeiTemplate){
        if (Utils.isNull(guoMeiTemplate)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(guoMeiTemplateService.updateGuoMeiTemplateById(guoMeiTemplate));
    }

    /**
     * 逻辑删除国美证书信息
     * @param gmId
     * @return
     */
    @PostMapping("/deleteguomeitemplatebyid")
    public Response deleteGuoMeiTemplateById(Integer gmId){
        if (Utils.isNull(gmId)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        int num = guoMeiTemplateService.deleteGuoMeiTemplateById(gmId);
        if (num == 0) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.DELETE_DATA_ERROR);
        }
        return ResponseUtil.buildSuccessResponse();
    }

    /**
     * 根据id查询国美证书信息
     * @param gmId
     * @return
     */
    @PostMapping("/queryguomeitemplatebyid")
    public Response queryGuoMeiTemplateById(Integer gmId){
        if (Utils.isNull(gmId)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.PARAM_TYPE);
        }
        return ResponseUtil.buildResponse(guoMeiTemplateService.queryGuoMeiTemplateById(gmId));
    }

    /**
     * 导入国美证书信息
     * @param file
     * @return
     */
    @PostMapping("/importguomeidata")
    public Response importGuoMeiData(@RequestParam("file") MultipartFile file){
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

        return ResponseUtil.buildResponse(guoMeiTemplateService.importGuoMeiData(inputStream,name));
    }
}
