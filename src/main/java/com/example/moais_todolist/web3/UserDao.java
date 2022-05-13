package com.example.moais_todolist.web3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private RowMapper<UserVo> rowMapper = BeanPropertyRowMapper.newInstance(UserVo.class);


    public UserDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Boolean login(String id, String password) {

        return true;
    }

    public int checkUserId(Map<String, String> data) {
        String sql = "select count(*) as countId " +
                "from tdlUser " +
                "where id = :id ";

        return template.queryForObject(sql, data, Integer.class);
    }

    public int join(Map<String, String> data) {


        var sql = "insert into tdlUser(id,password,createDate) \n" +
                "values (:id,:password,now())";

        template.update(sql, data);
        return 0;
    }

    public UserVo get(String id) {
        UserVo userVo = new UserVo();
        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("id", id);

            String sql = "select no,id,password,creteDate,deleteDate \n" +
                    "from tdlUser \n" +
                    "where id = :id \n";

            ResultSet rs = (ResultSet) template.queryForRowSet(sql, data);
            userVo.setNo(rs.getInt(1));
            userVo.setId(rs.getString(2));
            userVo.setPassword(rs.getString(3));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userVo;
    }

}
