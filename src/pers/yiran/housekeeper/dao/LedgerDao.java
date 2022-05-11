package pers.yiran.housekeeper.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.domain.QueryForm;
import pers.yiran.housekeeper.tools.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LedgerDao {
    private final QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

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
        String sql = "INSERT INTO keeper_ledger (parent,money,sid,account,createtime,ldesc)"+
                "values(?,?,?,?,?,?)";
        Object[] params = {ledger.getParent(),ledger.getMoney(),ledger.getSid(),ledger.getAccount(),
        ledger.getCreatetime(),ledger.getLdesc()};
        try {
            return queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ledger> queryLedgerByQueryForm(QueryForm queryForm) {
        List<String> params = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM keeper_ledger WHERE createtime BETWEEN ? AND ?");
        params.add(queryForm.getBegin());
        params.add(queryForm.getEnd());
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
        try {
            return queryRunner.query(stringBuilder.toString(), new BeanListHandler<>(Ledger.class), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}