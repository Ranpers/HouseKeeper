package pers.yiran.housekeeper.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.domain.QueryForm;
import pers.yiran.housekeeper.tools.DateUtils;
import pers.yiran.housekeeper.tools.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerDao {
    private final QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    public Double getTotalMoney(String parent) {
        String sql = "SELECT SUM(money) FROM keeper_ledger WHERE parent= ? AND createtime LIKE ?";
        Object[] params = {parent, DateUtils.getYear() + "%"};
        try {
            return queryRunner.query(sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteLedgerBySort(int sid) {
        try {
            return queryRunner.update("DELETE FROM keeper_ledger WHERE sid = ?", sid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> querySumMoneyBySort(String parent) {
        String sql = "SELECT SUM(money),sid FROM keeper_ledger WHERE parent= ? AND createtime LIKE ? GROUP BY sid";
        Object[] params = {parent, DateUtils.getYear() + "%"};
        try {
            return queryRunner.query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteLedger(int lid) {
        try {
            return queryRunner.update("DELETE FROM keeper_ledger WHERE lid = ?", lid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editLedger(Ledger ledger) {
        String sql = "UPDATE keeper_ledger SET parent = ?, money = ?, sid = ?," +
                " account = ?, createtime = ?, ldesc = ?" +
                "WHERE lid = ?";
        Object[] params = {ledger.getParent(), ledger.getMoney(), ledger.getSid(), ledger.getAccount(), ledger.getCreatetime(), ledger.getLdesc(), ledger.getLid()};
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addLedger(Ledger ledger){
        String sql = "INSERT INTO keeper_ledger (parent,money,sid,account,createtime,ldesc)" +
                "values(?,?,?,?,?,?)";
        Object[] params = {ledger.getParent(), ledger.getMoney(), ledger.getSid(), ledger.getAccount(),
                ledger.getCreatetime(), ledger.getLdesc()};
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Double> getMoneyByParent(QueryForm queryForm) {
        return null;
    }

    public Map<String, Object> queryLedgerByQueryForm(QueryForm queryForm) throws SQLException {
        List<Object> params = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT SUM(money) FROM keeper_ledger WHERE createtime BETWEEN ? AND ? AND parent = '支出'");
        params.add(queryForm.getBegin() + "");
        params.add(queryForm.getEnd() + "");
        if ("收入".equals(queryForm.getParent()) || "支出".equals(queryForm.getParent())) {
            stringBuilder.append(" AND parent = ?");
            params.add(queryForm.getParent());
        }
        String sname = queryForm.getSon();
        if (!"-请选择-".equals(sname)) {
            int sid = new SortDao().getSidBySname(sname);
            stringBuilder.append(" AND sid = ?");
            params.add(sid + "");
        }
        Double out = queryRunner.query(stringBuilder.toString(), new ScalarHandler<>(), params.toArray());
        if (null == out) out = 0.0;
        stringBuilder.replace(84, 86, "收入");
        Double in = queryRunner.query(stringBuilder.toString(), new ScalarHandler<>(), params.toArray());
        if (null == in) in = 0.0;
        stringBuilder.replace(69, 87, "");
        stringBuilder.replace(7, 17, "COUNT(lid)");
        int dataSize;
        dataSize = Math.toIntExact(queryRunner.query(stringBuilder.toString(), new ScalarHandler<>(), params.toArray()));
        Map<String, Object> data = new HashMap<>();
        data.put("size", dataSize);
        data.put("out", out);
        data.put("in", in);
        stringBuilder.append(" ORDER BY createtime LIMIT ?,?");
        int page = queryForm.getPage();
        int total = queryForm.getPage() * 10;
        params.add((page - 1) * 10);
        if (total > dataSize) {
            params.add(10 + dataSize);
        } else {
            params.add(10);
        }
        stringBuilder.replace(7, 17, "*");
        data.put("ledger", queryRunner.query(stringBuilder.toString(), new BeanListHandler<>(Ledger.class), params.toArray()));
        return data;
    }
}