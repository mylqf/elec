package com.lqf.dao.impl;

import com.lqf.dao.IElecTextDao;
import com.lqf.domain.ElecText;
import org.springframework.stereotype.Repository;

@Repository(IElecTextDao.SERVICE_NAME)
public class ElecTextImpl extends CommonDaoImpl<ElecText> implements IElecTextDao {


}
