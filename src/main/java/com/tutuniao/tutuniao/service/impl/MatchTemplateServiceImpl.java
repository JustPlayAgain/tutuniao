package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.mapper.MatchTemplateMapper;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.util.ExcelUtils;
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
import java.util.List;

import static com.tutuniao.tutuniao.common.enums.ErrorEnum.EXCEL_ERROR;

@Service
@Slf4j
public class MatchTemplateServiceImpl implements MatchTemplateService {

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
        log.info("修改图图鸟活动证书: ============= {}", matchTemplate);
        int num = matchTemplateMapper.updateMatchTemplateById(matchTemplate);
        log.info("修改图图鸟活动证书结果: ============= {}", num);
        return num;
    }

    @Override
    public PageVO<List<MatchTemplate>> queryMatchTemplateList(MatchTemplate matchTemplate) {
        Page<PageInfo> pageInfo = null;
        boolean flag = false;
        if (Utils.isNotNull(matchTemplate.getPageIndex()) && Utils.isNotNull(matchTemplate.getPageSize())) {
            pageInfo = PageHelper.startPage(matchTemplate.getPageIndex(), matchTemplate.getPageSize());
            flag = true;
        }
        List<MatchTemplate> matchTemplateList = matchTemplateMapper.queryMatchTemplateList(matchTemplate);
        return new PageVO<>(flag ? (int) pageInfo.getTotal() : matchTemplateList.size(), matchTemplateList);
    }


    @Override
    public Response importMatchData(File file) {
        Workbook wb = null;
        List<MatchTemplate> matchTemplateList = new ArrayList();
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

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            for (int j = 0; j < row.getRowNum(); j++) {
                // 取出excel 当前行中的数据 设置数据
                MatchTemplate matchTemplate = new MatchTemplate();
                matchTemplate.setNumberId((int) row.getCell(j).getNumericCellValue()); // 序号
                matchTemplate.setStudentName(row.getCell(j).getStringCellValue()); // 名字
                matchTemplate.setBirthDate(row.getCell(j).getDateCellValue()); // 出生日期
                matchTemplate.setGender(row.getCell(j).getStringCellValue()); // 性别
                matchTemplate.setProfession(row.getCell(j).getStringCellValue()); // 专业
                matchTemplate.setGroupLevel(row.getCell(j).getStringCellValue()); // 组别
                matchTemplate.setWorksName(row.getCell(j).getStringCellValue()); // 作品名称
                matchTemplate.setTutor(row.getCell(j).getStringCellValue()); // 辅导老师
                matchTemplate.setResults(row.getCell(j).getStringCellValue()); // 获奖结果
                matchTemplateList.add(matchTemplate);
            }

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
}
