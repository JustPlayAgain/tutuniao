package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.mapper.GuoMeiTemplateMapper;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
import com.tutuniao.tutuniao.util.ExcelUtils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tutuniao.tutuniao.common.enums.ErrorEnum.EXCEL_ERROR;

@Service
@Slf4j
public class GuoMeiTemplateServiceImpl implements GuoMeiTemplateService {

    @Autowired
    private GuoMeiTemplateMapper guoMeiTemplateMapper;

    @Override
    public int insertGuoMeiTemplate(GuoMeiTemplate activity) {
        return 0;
    }

    @Override
    public int deleteGuoMeiTemplateById(Integer activityId) {
        return 0;
    }

    @Override
    public int updateGuoMeiTemplateById(Integer activityId) {
        return 0;
    }

    @Override
    public List<GuoMeiTemplate> queryGuoMeiTemplateList(GuoMeiTemplate activity) {
        return null;
    }

    @Override
    public Response importGuoMeiData(File file) {
        Workbook wb = null;
        List<GuoMeiTemplate> guoMeiTemplateList = new ArrayList();
        try {
            if (ExcelUtils.isExcel2007(file.getPath())) {
                wb = new XSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(file));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.buildErrorResponse(EXCEL_ERROR.EXCEL_ERROR);
        }
        //获取第一张表
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            for (int j = 0; j < row.getRowNum(); j++) {
                GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();
                guoMeiTemplate.setNumberId((int) row.getCell(j).getNumericCellValue()); // 序号
                guoMeiTemplate.setStudentName(row.getCell(j).getStringCellValue()); // 名字
                guoMeiTemplate.setNationality(row.getCell(j).getStringCellValue()); // 国籍
                guoMeiTemplate.setNation(row.getCell(j).getStringCellValue()); // 民族
                guoMeiTemplate.setGender(row.getCell(j).getStringCellValue()); // 性别
                guoMeiTemplate.setBirthDate(row.getCell(j).getDateCellValue()); // 出生日期
                guoMeiTemplate.setCertificateNumber(row.getCell(j).getStringCellValue()); // 证书编号
                guoMeiTemplate.setProfession(row.getCell(j).getStringCellValue()); // 专业
                guoMeiTemplate.setDeclareLevel(row.getCell(j).getStringCellValue()); // 申报级别
                guoMeiTemplate.setExaminationLevel(row.getCell(j).getStringCellValue()); // 考试级别
                guoMeiTemplate.setOriginalLevel(row.getCell(j).getStringCellValue()); // 原级别
                guoMeiTemplate.setNativePlace(row.getCell(j).getStringCellValue()); // 所在地
                guoMeiTemplate.setExamDate(row.getCell(j).getDateCellValue()); // 考试时间
                guoMeiTemplate.setCreateDate(new Date());
                guoMeiTemplate.setCreateUser("admin");
                guoMeiTemplateList.add(guoMeiTemplate);
            }
        }
        for(GuoMeiTemplate guomei : guoMeiTemplateList) {
            log.info("插入国美证书数据：============ {}", guomei);
            int num = guoMeiTemplateMapper.insertGuoMeiTemplate(guomei);
            log.info("插入国美数据条数：======== {} ", num);
        }
        try {
            wb.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.buildSuccessResponse();
    }
}
