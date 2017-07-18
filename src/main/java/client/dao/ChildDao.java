package client.dao;

import client.domain.Child;

import java.util.List;

public interface ChildDao {

    public Child getChildById(long id);

    public void saveChild(Child child);

    public List<Child> getAllChildren();

}
