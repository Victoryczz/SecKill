package seu.vczz.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.vczz.seckill.dao.TestDao;
import seu.vczz.seckill.domain.Test;
import seu.vczz.seckill.service.ITestService;

/**
 * CREATE by vczz on 2018/5/11
 */
@Service("iTestService")
public class TestServiceImpl implements ITestService{

    @Autowired
    private TestDao testDao;


    public Test getById(Integer id) {
        Test test = testDao.selectTestById(id);
        return test;
    }
}
