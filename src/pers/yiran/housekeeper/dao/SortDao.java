package pers.yiran.housekeeper.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pers.yiran.housekeeper.domain.Sort;
import pers.yiran.housekeeper.tools.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("all")
public class SortDao {
    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    public int getSidBySname(String sname) {
        String sql = "SELECT sid FROM keeper_sort WHERE sname = ?";
        try {
            return (int) queryRunner.query(sql, new ScalarHandler(), sname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSnameBySid(int sid) {
        String sql = "SELECT sname FROM keeper_sort WHERE sid = ?";
        try {
            return (String) queryRunner.query(sql, new ScalarHandler(), sid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> querySortNameByParent(String parent) {
        String sql = "SELECT sname FROM keeper_sort WHERE parent = ?";
        try {
            return (List<Object>) queryRunner.query(sql, new ColumnListHandler(), parent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> querySortNameAll() {
        String sql = "SELECT sname FROM keeper_sort";
        try {
            return (List<Object>) queryRunner.query(sql, new ColumnListHandler());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addSort(Sort sort) {
        String sql = "INSERT INTO keeper_sort(sname,parent,sdesc) values(?,?,?)";
        Object[] params = {sort.getSname(), sort.getParent(), sort.getSdesc()};
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editSort(Sort sort) {
        String sql = "UPDATE keeper_sort SET sname=?, parent=?, sdesc=? WHERE sid=?";
        Object[] params = {sort.getSname(), sort.getParent(), sort.getSdesc(), sort.getSid()};
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteSort(Sort sort) {
        String sql = "DELETE FROM keeper_sort WHERE sid=?";
        Object[] params = {sort.getSid()};
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sort> querySortAll() {
        String sql = "SELECT * FROM keeper_sort";
        try {
            return queryRunner.query(sql, new BeanListHandler<Sort>(Sort.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
