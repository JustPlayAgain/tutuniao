package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MatchTemplateServiceImpl implements MatchTemplateService {
    @Override
    public int insertMatchTemplate(MatchTemplate activity) {
        return 0;
    }

    @Override
    public int deleteMatchTemplateById(Integer activityId) {
        return 0;
    }

    @Override
    public int updateMatchTemplateById(Integer activityId) {
        return 0;
    }

    @Override
    public List<MatchTemplate> queryMatchTemplateList(MatchTemplate activity) {
        return null;
    }
}
