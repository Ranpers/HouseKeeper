package pers.yiran.housekeeper.services;

import pers.yiran.housekeeper.dao.LedgerDao;
import pers.yiran.housekeeper.dao.SortDao;
import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.domain.QueryForm;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerService {
    private final LedgerDao ledgerDao = new LedgerDao();
    private final SortDao sortDao = new SortDao();

    public int deleteLedger(int lid) {
        return ledgerDao.deleteLedger(lid);
    }

    public int deleteLedgerBySort(int sid) {
        return ledgerDao.deleteLedgerBySort(sid);
    }

    public int editLedger(Ledger ledger) {
        return ledgerDao.editLedger(ledger);
    }

    public int getSidBySname(String sname) {
        return sortDao.getSidBySname(sname);
    }

    public Double queryTotalMoneyByParent(String parent) {
        return ledgerDao.getTotalMoney(parent);
    }

    public Map<String, Double> querySumMoneyBySort(String parent) {
        List<Object[]> list = ledgerDao.querySumMoneyBySort(parent);
        HashMap<String, Double> map = new HashMap<>();
        for (Object[] objects : list) {
            map.put(sortDao.getSnameBySid((int) objects[1]), (Double) objects[0]);
        }
        return map;
    }

    /*
     *定义方法 添加账务
     */
    public int addLedger(Ledger ledger) {
        int sid = sortDao.getSidBySname(ledger.getSname());
        ledger.setSid(sid);
        return ledgerDao.addLedger(ledger);
    }

    /*
     * return Map集合
     * 存入 Map集合 return
     */
    public Map<String, Object> queryLedgerByQueryForm(QueryForm queryForm, boolean flag) throws SQLException {
        Map<String, Object> data = ledgerDao.queryLedgerByQueryForm(queryForm, flag);
        List<Ledger> list = (List<Ledger>) data.get("ledger");
        for (Ledger ledger : list) {
            ledger.setSname(sortDao.getSnameBySid(ledger.getSid()));
        }
        return data;
    }
}
