package com.lqf.service;

import com.lqf.domain.ElecText;

import java.util.List;

public interface IElecTextService {

    public static final String SERVICE_NAME="com.lqf.service.impl.ElecTextServiceImpl";

    public void save(ElecText elecText);

    public List<ElecText> findCollectionByConditionNoPage(ElecText elecText);


}
