package com.example.moais_todolist.web3;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserDao {
    JdbcTemplate template = null;

    public int login(String id, String password) {
    }

    public int checkUserId(HashMap data) {
        var sql = "select count(*) as countId " +
                "from `user` " +
                "where id = ? ";

         SqlRowSet result = template.queryForRowSet(sql,data.get("id"));

        return result;



        Intrinsics.checkNotNullParameter(data, "data");

        String sql1 = "select count(*) as countId from `user` where accountId = ? ";

        try {
            JdbcTemplate var10000 = new JdbcTemplate();

            if (var10000 != null) {
                Object[] var10002 = new Object[1];
                String var10005 = (String)data.get("accountId");
                if (var10005 == null) {
                    var10005 = "";
                }

                var10002[0] = var10005;
                SqlRowSet var7 = var10000.queryForRowSet(sql, var10002);
                if (var7 != null) {
                    SqlRowSet var3 = var7;
                    int var5 = false;
                    var3.first();
                    return var3.getInt("countId");
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }





    }

    public int join(HashMap data) {
        var sql = "insert into tdlUser(id,password,createDate,deleteDate) \n" +
                "values (${data.get('id'})";

        template.queryForObject(sql, Integer.class);
        return template.queryForObject(sql, Integer.class);
    }

}
