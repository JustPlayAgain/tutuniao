package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.schedule.ScheduledService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping(value="index",method= RequestMethod.GET)
    public IndexObject index(){
        if( null != ScheduledService.indexObject){
            return ScheduledService.indexObject;
        }else{
            IndexObject indexObject = ScheduledService.buildIndex();
            if(null != indexObject){
                ScheduledService.indexObject = indexObject;
                return ScheduledService.indexObject;
            }
        }
        return null;
    }
}
