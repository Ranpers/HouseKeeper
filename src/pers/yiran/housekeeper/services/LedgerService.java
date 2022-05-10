package pers.yiran.housekeeper.services;

import pers.yiran.housekeeper.dao.LedgerDao;
import pers.yiran.housekeeper.dao.SortDao;
import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.domain.QueryForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LedgerService {
    private LedgerDao ledgerDao = new LedgerDao();
    private SortDao sortDao = new SortDao();
    /*
     *定义方法 添加账务
     */
   public int editLedger(Ledger ledger){
       return ledgerDao.editLedger(ledger);
    }
    public int getSidBySname(String sname){
       return sortDao.getSidBySname(sname);
    }
    public int addLedger(Ledger ledger){
        int sid = sortDao.getSidBySname(ledger.getSname());
        ledger.setSid(sid);
        return ledgerDao.addLedger(ledger);
    }
    /*
     * return Map集合
     * 遍历符合条件的 List集合 计算出 收入 支出
     * 存入 Map集合 return
     */
    public Map<String,Object> queryLedgerByQueryForm(QueryForm queryForm){
        List<Ledger> list = ledgerDao.queryLedgerByQueryForm(queryForm);
        double in = 0;
        double out = 0;
        for(Ledger ledger : list){
            ledger.setSname(sortDao.getSnameBySid(ledger.getSid()));
            if("收入".equals(ledger.getParent()))
                in+=ledger.getMoney();
            else
                out+=ledger.getMoney();
        }
        Map<String,Object> data = new HashMap<>();
        data.put("ledger",list);
        data.put("in",in);
        data.put("out",out);
        return data;
    }
}
