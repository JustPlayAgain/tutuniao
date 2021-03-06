package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.mapper.GuoMeiTemplateMapper;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
import com.tutuniao.tutuniao.util.ExcelUtils;
import com.tutuniao.tutuniao.util.IdCardUtil;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.tutuniao.tutuniao.common.enums.ErrorEnum.EXCEL_ERROR;

@Service
@Slf4j
public class GuoMeiTemplateServiceImpl implements GuoMeiTemplateService {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private GuoMeiTemplateMapper guoMeiTemplateMapper;

    @Override
    public List<GuoMeiTemplate> queryGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate) {
        List<GuoMeiTemplate> template = guoMeiTemplateMapper.queryGuoMeiTemplate(guoMeiTemplate);
        if (Utils.isNull(template)) { // 未查到该数据
            return null;
        }
        return template;
    }

    @Override
    public int insertGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate) {
        if(!StringUtil.isBlank(guoMeiTemplate.getIdCard())){
            setBirthday(guoMeiTemplate,guoMeiTemplate.getIdCard());
        }
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
        if(!StringUtil.isBlank(guoMeiTemplate.getIdCard())){
            setBirthday(guoMeiTemplate,guoMeiTemplate.getIdCard());
        }
        log.info("修改国美证书数据信息: ============= {}", guoMeiTemplate);
        int i = guoMeiTemplateMapper.updateGuoMeiTemplateById(guoMeiTemplate);
        log.info("修改国美证书数据信息行数: ============= {}", i);
        return i;
    }

    @Override
    public PageVO<List<GuoMeiTemplate>> queryGuoMeiTemplateList( GuoMeiTemplate guoMeiTemplate) {
        if(guoMeiTemplate.getActId() == null ) guoMeiTemplate.setActId(-2);

        Page<PageInfo> pageInfo = guoMeiTemplate.getPageInfos(guoMeiTemplate);

        log.info("查询【国美证书】开始 参数为:{}", Jackson2Helper.toJsonString(guoMeiTemplate));

        List<GuoMeiTemplate> guoMeiTemplateList = guoMeiTemplateMapper.queryGuoMeiTemplateList(guoMeiTemplate);
        log.info("查询【国美证书】结束 结果为:{}", Jackson2Helper.toJsonString(guoMeiTemplateList));
        return new PageVO<>( (int) pageInfo.getTotal(), guoMeiTemplateList, pageInfo.getPageSize());
    }

    @Override
    public GuoMeiTemplate queryGuoMeiTemplateById(Integer gmId) {
        return guoMeiTemplateMapper.queryGuoMeiTemplateById(gmId);
    }

    @Override
    public Response importGuoMeiData(InputStream file, String name, int actId) {
        Workbook wb = null;
        List<GuoMeiTemplate> guoMeiTemplateList = new ArrayList();
        try {
            if (ExcelUtils.isExcel2007(name)) {
                wb = new XSSFWorkbook(file);
            } else {
                wb = new HSSFWorkbook(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.buildErrorResponse(EXCEL_ERROR.EXCEL_ERROR);
        }
        //获取第一张表
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            if(row == null )
                break;
            int j = 0;
            GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();

            Cell cellNumberId = row.getCell(j);
            if(cellNumberId == null)
                continue;
            row.getCell(j++).setCellType(CellType.NUMERIC);
            guoMeiTemplate.setNumberId((int) cellNumberId.getNumericCellValue()); // 序号

            guoMeiTemplate.setStudentName(row.getCell(j++).getStringCellValue()); // 名字

            row.getCell(j).setCellType(CellType.STRING);
            String idCard = row.getCell(j++).getStringCellValue();
            guoMeiTemplate.setIdCard(idCard); // 身份证
            setBirthday(guoMeiTemplate, idCard);

            guoMeiTemplate.setWorksName(row.getCell(j++).getStringCellValue()); // 大赛名称
            guoMeiTemplate.setProfession(row.getCell(j++).getStringCellValue()); // 专业

            guoMeiTemplate.setResults(row.getCell(j++).getStringCellValue()); // 获奖结果
            guoMeiTemplate.setActId(actId);

            guoMeiTemplateList.add(guoMeiTemplate);
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
            return ResponseUtil.buildErrorResponse();
        }
        return ResponseUtil.buildSuccessResponse();
    }

    private void setBirthday(GuoMeiTemplate guoMeiTemplate, String idCard) {
        try {
            Map<String, String> birAgeSex = IdCardUtil.getBirAgeSex(idCard);
            String birthday = birAgeSex.get("birthday");
            guoMeiTemplate.setBirthDate(simpleDateFormat.parse(birthday));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}