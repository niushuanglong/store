package com.niu.web.business.assembler;

import com.niu.web.business.dto.SaluteDTO;
import com.niu.web.business.pojo.SaluteMoneyEntity;
import com.sun.org.apache.regexp.internal.RE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author niushuanglong
 * @date 2023/6/3 15:43:57
 * @description
 */
public class SaluteMoneyAssembler {


    public SaluteMoneyEntity toSaluteMoney(SaluteDTO dto) {
        return new SaluteMoneyEntity(dto.getName(),dto.getTime(),dto.getSalute(),dto.getReason(),dto.getFuneralOrHappy());
    }

    public List<SaluteDTO> fromSaluteMoneyDTOS(List<SaluteMoneyEntity> saluteMoneyEntities) {
        return saluteMoneyEntities.stream().filter(Objects::nonNull).map(saluteMoney ->fromSaluteMoneyDTO(saluteMoney)).collect(Collectors.toList());
    }

    public SaluteDTO fromSaluteMoneyDTO(SaluteMoneyEntity saluteMoney){
        return new SaluteDTO(saluteMoney.getId(), saluteMoney.getName(),saluteMoney.getTime(),saluteMoney.getSalute(),
                saluteMoney.getReason(),saluteMoney.getFuneralOrHappy());
    }

}
