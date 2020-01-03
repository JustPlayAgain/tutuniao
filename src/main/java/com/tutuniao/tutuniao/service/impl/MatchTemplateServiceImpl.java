package com.tutuniao.tutuniao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.mapper.MatchTemplateMapper;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.util.ExcelUtils;
import com.tutuniao.tutuniao.util.IdCardUtil;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tutuniao.tutuniao.common.enums.ErrorEnum.EXCEL_ERROR;

@Service
@Slf4j
public class MatchTemplateServiceImpl implements MatchTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(MatchTemplateServiceImpl.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MatchTemplateMapper matchTemplateMapper;



    @Override
    public MatchTemplate queryMatchTemplateById(Integer mtId) {
        log.info("查询图图鸟活动证书: ============= {}", mtId);
        MatchTemplate matchTemplate = matchTemplateMapper.queryMatchTemplateById(mtId);
        log.info("查询图图鸟活动证书: ============= {}", matchTemplate);
        return matchTemplate;
    }

    @Override
    public int insertMatchTemplate(MatchTemplate matchTemplate) {
        if(!StringUtil.isBlank(matchTemplate.getIdCard())){
            setBirthday(matchTemplate,matchTemplate.getIdCard());
        }
        log.info("插入图图鸟活动证书: ============= {}", matchTemplate);
        int num = matchTemplateMapper.insertMatchTemplate(matchTemplate);
        log.info("插入图图鸟活动证书结果: ============= {}", num);
        return num;
    }

    @Override
    public int deleteMatchTemplateById(Integer mtId) {
        log.info("删除图图鸟活动证书: ============= {}", mtId);
        int num = matchTemplateMapper.deleteMatchTemplateById(mtId);
        log.info("删除图图鸟活动证书结果: ============= {}", num);
        return num;
    }

    @Override
    public int updateMatchTemplateById(MatchTemplate matchTemplate) {
        if(!StringUtil.isBlank(matchTemplate.getIdCard())){
            setBirthday(matchTemplate,matchTemplate.getIdCard());
        }
        log.info("修改图图鸟活动证书: ============= {}", matchTemplate);
        int num = matchTemplateMapper.updateMatchTemplateById(matchTemplate);
        log.info("修改图图鸟活动证书结果: =============== {}", num);
        return num;
    }

    @Override
    public PageVO<List<MatchTemplate>> queryMatchTemplateList(MatchTemplate matchTemplate) {
        Page<PageInfo> pageInfo = matchTemplate.getPageInfos(matchTemplate);

        List<MatchTemplate> matchTemplateList = matchTemplateMapper.queryMatchTemplateList(matchTemplate);
        return new PageVO<>((int) pageInfo.getTotal(), matchTemplateList, pageInfo.getPageSize());
    }

    @Override
    public List<MatchTemplate> matchTemplateList(MatchTemplate matchTemplate) {
        return matchTemplateMapper.matchTemplateList(matchTemplate);
    }


    @Override
    public Response importMatchData(InputStream file, String name){

        Workbook wb = null;
        List<MatchTemplate> matchTemplateList = new ArrayList();
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

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
                // 取出excel 当前行中的数据 设置数据

            if(row == null )
                break;
            int j = 0;
            MatchTemplate matchTemplate = new MatchTemplate();
            Cell cell = row.getCell(j);
            logger.info("row = {}", JSON.toJSONString(cell));
            if(cell == null ){
                continue;
            }
            row.getCell(j++).setCellType(CellType.NUMERIC);
            matchTemplate.setNumberId((int) cell.getNumericCellValue()); // 序号
            Cell studentName = row.getCell(j++);
            if(studentName != null ){
                String tmpName = studentName.getStringCellValue();
                tmpName = tmpName.replaceAll("\r","");
                tmpName = tmpName.replaceAll("\n","");
                tmpName = tmpName.replaceAll(" ","");
                Matcher matcher = Pattern.compile(Constant.regex).matcher(tmpName);
                if(matcher.find()){
                    String group = matcher.group(0);
                    matchTemplate.setStudentName(group); // 名字
                }
            }


            row.getCell(j).setCellType(CellType.STRING);
            String idCard = row.getCell(j++).getStringCellValue();
            if(idCard != null ){
                idCard = idCard.replaceAll("\r","");
                idCard = idCard.replaceAll("\n","");
                idCard = idCard.replaceAll(" ","");
            }
            matchTemplate.setIdCard(idCard); // 身份证
            setBirthday(matchTemplate,idCard);

            matchTemplate.setWorksName(row.getCell(j++).getStringCellValue()); // 测评名称
            Cell certificateNumber = row.getCell(j++);
            if(certificateNumber != null )
            matchTemplate.setCertificateNumber(certificateNumber.getStringCellValue()); // 证书编号

            matchTemplate.setProfession(row.getCell(j++).getStringCellValue()); // 专业

            matchTemplate.setExaminationLevel(row.getCell(j++).getStringCellValue()); // 级别
            Cell nativePlace = row.getCell(j++);
            if(nativePlace != null)
            matchTemplate.setNativePlace(nativePlace.getStringCellValue()); // 所在地
            matchTemplate.setExamDate(row.getCell(j++).getDateCellValue()); // 考试时间

            matchTemplateList.add(matchTemplate);

        }
        for (MatchTemplate match : matchTemplateList) {
            log.info("插入比赛证书信息：=============== {}", match);
            int num = matchTemplateMapper.insertMatchTemplate(match);
            log.info("插入比赛证书信息条数：=============== {}", num);
        }
        try {
            wb.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.buildSuccessResponse();
    }

    private void setBirthday(MatchTemplate matchTemplate, String idCard) {
        try {
            Map<String, String> birAgeSex = IdCardUtil.getBirAgeSex(idCard);
            String birthday = birAgeSex.get("birthday");
            matchTemplate.setBirthDate(simpleDateFormat.parse(birthday));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
