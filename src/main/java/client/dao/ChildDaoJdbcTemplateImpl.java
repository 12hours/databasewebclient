package client.dao;

import client.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository(value = "jdbcTemplateDao")
public class ChildDaoJdbcTemplateImpl implements ChildDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Child getChildById(long id) {
        return null;
    }

    @Override
    public void saveChild(Child child) {

    }

    @Override
    public List<Child> getAllChildren() {
        return null;
    }
}
