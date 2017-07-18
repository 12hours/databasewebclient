package client.service;

import client.dao.ChildDao;
import client.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleChildrenService extends ChildrenService {


    @Autowired
    @Qualifier(value = "childDaoWork")
    ChildDao childDao;

    @Override
    public Child getChildById(long id) {
        return childDao.getChildById(id);
    }

    @Override
    public void saveChild(Child child) {
        childDao.saveChild(child);
    }
}
