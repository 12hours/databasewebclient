package core.service;

import core.dao.ChildRepository;
import core.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    ChildRepository childRepository;

    @Override
    public Child getChildById(long id) {
        return childRepository.findOne(id);
    }

    @Override
    public void saveChild(Child child) {
        childRepository.save(child);
    }

}
