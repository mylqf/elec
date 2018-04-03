package com.lqf.service.impl;

import com.lqf.dao.IElecTextDao;
import com.lqf.domain.ElecText;
import com.lqf.service.IElecTextService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(IElecTextService.SERVICE_NAME)
@Transactional(readOnly = true)
public class ElecTextServiceIMpl implements IElecTextService {

    @Resource(name = IElecTextDao.SERVICE_NAME)
    private IElecTextDao elecTextDao;

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = false)
    @Override
    public void save(ElecText elecText) {
        elecTextDao.save(elecText);
    }


    @Override
    public List<ElecText> findCollectionByConditionNoPage(ElecText elecText) {

        //组织查询条件
        String condition="";
        //存放可变参数?
        List<Object> paramList=new ArrayList<>(Object);
        if (StringUtils.isNotBlank(elecText.getTextName())){
            condition+=" and o.textName like ?";
            paramList.add("%"+elecText.getTextName()+"%");
        }
        if (StringUtils.isNotBlank(elecText.getTextRemark())){
            condition+=" and o.textRemark like ?";
            paramList.add("%"+elecText.getTextRemark()+"%");
        }
        //将集合中存放的可变参数转换成数组
        Object[] params=paramList.toArray();
        //使用集合存放排序的条件
        Map<String,String> orderby=new LinkedHashMap<>();
        orderby.put("o.textDate","asc");
        orderby.put("o.textRemark","desc");
        List<ElecText> list=elecTextDao.findCollectionByConditionNoPage(condition,params,orderby);
        return  list;
    }
}
