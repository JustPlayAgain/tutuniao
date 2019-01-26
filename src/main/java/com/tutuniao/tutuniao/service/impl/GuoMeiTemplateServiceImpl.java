package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.mapper.GuoMeiTemplateMapper;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
import com.tutuniao.tutuniao.util.ExcelUtils;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GuoMeiTemplate queryGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate) {
        GuoMeiTemplate template = guoMeiTemplateMapper.queryGuoMeiTemplate(guoMeiTemplate);
        if (Utils.isNull(template)) { // 未查到该数据
            return null;
        }
        return template;
    }

    @Override
    public int insertGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate) {
        log.info("新增国美证书数据: ========== {}", guoMeiTemplate);
        int i = guoMeiTemplateMapper.insertGuoMeiTemplate(guoMeiTemplate);
        log.info("新增国美证书数据受影响行数: ========== {}", i);
        return i;
    }

    @Override
    public int deleteGuoMeiTemplateById(Integer gmId) {
        // 逻辑删除 根据主键修改 valid为 1
        int num = 0;
        GuoMeiTemplate guoMeiTemplate = guoMeiTemplateMapper.queryGuoMeiTemplateById(gmId);
        if (Utils.isNotNull(guoMeiTemplate)) {
            guoMeiTemplate.setValid(1);
            log.info("删除国美证书数据信息: ============= {}", guoMeiTemplate);
            num = guoMeiTemplateMapper.updateGuoMeiTemplateById(guoMeiTemplate);
            log.info("删除国美证书数据信息行数: ============= {}", num);
            return num;
        }
        return num;
    }

    @Override
    public int updateGuoMeiTemplateById(GuoMeiTemplate guoMeiTemplate) {
        log.info("修改国美证书数据信息: ============= {}", guoMeiTemplate);
        int i = guoMeiTemplateMapper.updateGuoMeiTemplateById(guoMeiTemplate);
        log.info("修改国美证书数据信息行数: ============= {}", i);
        return i;
    }

    @Override
    public PageVO<List<GuoMeiTemplate>> queryGuoMeiTemplateList(GuoMeiTemplate guoMeiTemplate) {
        Page<PageInfo> pageInfo = null;
        boolean flag = false;
        if (Utils.isNotNull(guoMeiTemplate.getPageIndex()) && Utils.isNotNull(guoMeiTemplate.getPageSize())) {
            pageInfo = PageHelper.startPage(guoMeiTemplate.getPageIndex(), guoMeiTemplate.getPageSize());
            flag = true;
        }
        log.info("查询【国美证书】开始 参数为:{}", Jackson2Helper.toJsonString(guoMeiTemplate));
        List<GuoMeiTemplate> guoMeiTemplateList = guoMeiTemplateMapper.queryGuoMeiTemplateList(guoMeiTemplate);
        log.info("查询【国美证书】结束 结果为:{}", Jackson2Helper.toJsonString(guoMeiTemplateList));
        return new PageVO<>(flag ? (int) pageInfo.getTotal() : guoMeiTemplateList.size(), guoMeiTemplateList);
    }

    @Override
    public GuoMeiTemplate queryGuoMeiTemplateById(Integer gmId) {
        return guoMeiTemplateMapper.queryGuoMeiTemplateById(gmId);
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
