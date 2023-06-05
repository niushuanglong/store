package com.niu.web.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.web.business.dto.SaluteDTO;
import com.niu.web.business.pojo.SaluteMoneyEntity;
import com.niu.web.business.utils.JsonResult;

/**
 * 
 *
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-06-03 10:44:50
 */
public interface TSaluteMoneyService extends IService<SaluteMoneyEntity> {


    JsonResult addOrUpdateSalute(SaluteDTO dto);

    JsonResult delEvent(String id);

    JsonResult findRecord(SaluteDTO dto);
}

