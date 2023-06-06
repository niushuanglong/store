package com.niu.web.business.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.assembler.SaluteMoneyAssembler;
import com.niu.web.business.dto.SaluteDTO;
import com.niu.web.business.mapper.TSaluteMoneyDao;
import com.niu.web.business.pojo.SaluteMoneyEntity;
import com.niu.web.business.service.TSaluteMoneyService;
import com.niu.web.business.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Transactional
@Service
public class TSaluteMoneyServiceImpl extends ServiceImpl<TSaluteMoneyDao, SaluteMoneyEntity> implements TSaluteMoneyService {
    @Autowired
    private TSaluteMoneyDao saluteMoneyDao;

    @Override
    public JsonResult addOrUpdateSalute(SaluteDTO dto) {
        SaluteMoneyAssembler saluteMoneyAssembler = new SaluteMoneyAssembler();
        String msg;

        if (StringUtils.isNotBlank(dto.getId())){
            SaluteMoneyEntity saluteMoney=saluteMoneyDao.selectById(dto.getId());
            saluteMoney.update(dto.getName(),dto.getTime(),dto.getSalute(),dto.getReason(),dto.getFuneralOrHappy());
            QueryWrapper<SaluteMoneyEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id",dto.getId());
            saluteMoneyDao.update(saluteMoney,wrapper);
            msg="修改成功!";
        }else {
            msg="新增成功!";
            dto.setId(UUID.randomUUID().toString());
            saluteMoneyDao.insert(saluteMoneyAssembler.toSaluteMoney(dto));
        }
        return new JsonResult(msg);
    }

    @Override
    public JsonResult delEvent(String id) {
        if (StringUtils.isBlank(id)){
            throw new RuntimeException("id不能为空!");
        }
        int i = saluteMoneyDao.deleteById(id);
        String msg=i==1?"删除成功!":"删除失败!";
        return new JsonResult(msg);
    }

    @Override
    public JsonResult findRecord(SaluteDTO dto) {
        List<SaluteMoneyEntity> saluteMoneyEntities = saluteMoneyDao.selectList(this.queryCondition(dto));
        SaluteMoneyAssembler saluteMoneyAssembler = new SaluteMoneyAssembler();
        List<SaluteDTO> saluteDTOS=saluteMoneyAssembler.fromSaluteMoneyDTOS(saluteMoneyEntities);
        return new JsonResult(saluteDTOS);
    }


    public QueryWrapper<SaluteMoneyEntity> queryCondition(SaluteDTO dto ){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(dto.getId())){
            queryWrapper.eq("id",dto.getId());
        }
        if (StringUtils.isNotBlank(dto.getName())){
            queryWrapper.like("name",dto.getName());
        }
        if (StringUtils.isNotBlank(dto.getSalute())){
            queryWrapper.eq("salute",dto.getSalute());
        }
        if (StringUtils.isNotBlank(dto.getFuneralOrHappy())){
            queryWrapper.eq("funeral_or_happy",dto.getFuneralOrHappy());
        }
        if (StringUtils.isNotBlank(dto.getReason())){
            queryWrapper.eq("reason",dto.getReason());
        }
        if (StringUtils.isNotBlank(dto.getReason())){
            queryWrapper.eq("reason",dto.getReason());
        }
        return queryWrapper;
    }

}