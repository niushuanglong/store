package com.niu.web.business.controller;

import com.niu.web.business.dto.SaluteDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.service.TSaluteMoneyService;
import com.niu.web.business.utils.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niushuanglong
 * @date 2023/6/2 22:14:37
 * @description 礼金记录
 */
@RequestMapping("/api/salute")
@RestController
public class SaluteMoneyController {
    @Autowired
    private TSaluteMoneyService service;

    @ApiOperation(value = "新增或修改礼金",httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateSalute",method = RequestMethod.POST)
    public JsonResult addOrUpdateSalute(@RequestBody SaluteDTO dto, HttpServletResponse response, HttpServletRequest request){
        return service.addOrUpdateSalute(dto);
    }
    @ApiOperation(value = "删除礼金记录",httpMethod = "GET")
    @RequestMapping(value = "/delEvent",method = RequestMethod.GET)
    public JsonResult delEvent(String id, HttpServletResponse response, HttpServletRequest request){
        return service.delEvent(id);
    }
    @ApiOperation(value = "查询礼金记录",httpMethod = "POST")
    @RequestMapping(value = "/findRecord",method = RequestMethod.POST)
    public JsonResult findRecord(@RequestBody SaluteDTO dto, HttpServletResponse response, HttpServletRequest request){
        return service.findRecord(dto);
    }


}
