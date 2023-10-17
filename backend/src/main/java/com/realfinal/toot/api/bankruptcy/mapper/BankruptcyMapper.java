package com.realfinal.toot.api.bankruptcy.mapper;

import com.realfinal.toot.api.bankruptcy.response.AllBankruptcyRes;
import com.realfinal.toot.api.bankruptcy.response.DetailBankruptcyRes;
import com.realfinal.toot.db.entity.Bankruptcy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankruptcyMapper {

    BankruptcyMapper INSTANCE = Mappers.getMapper(BankruptcyMapper.class);

    AllBankruptcyRes toAllBankruptcyRes(Bankruptcy bankruptcy);

    DetailBankruptcyRes toDetailBankruptcyRes(Bankruptcy bankruptcy);
}
