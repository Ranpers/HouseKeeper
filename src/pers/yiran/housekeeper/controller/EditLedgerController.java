package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.services.LedgerService;
import pers.yiran.housekeeper.services.SortService;
import pers.yiran.housekeeper.view.AbstractOperationLedgerDialog;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@SuppressWarnings("all")
public class EditLedgerController extends AbstractOperationLedgerDialog {
    private SortService sortService = new SortService();
    private LedgerService ledgerService = new LedgerService();
    private final String daytime;
    private boolean flag = false;
    private Ledger ledger;
    public EditLedgerController(JDialog dialog, Ledger ledger) {
        super(dialog);
        this.ledger=ledger;
        daytime = createtimeTxt.getText();
        titleLabel.setText("编辑账务");
        this.setTitle("编辑账务");
        parentBox.setSelectedItem(ledger.getParent());
        List<Object> list = sortService.querySortNameByParent(ledger.getParent());
        sortBox.setModel(new DefaultComboBoxModel(list.toArray()));
        sortBox.setSelectedItem(ledger.getSname());
        accountTxt.setText(ledger.getAccount());
        moneyTxt.setText(ledger.getMoney()+"");
        createtimeTxt.setText(ledger.getCreatetime());
        ldescTxt.setText(ledger.getLdesc());
    }

    @Override
    public void changeParent() {
        //获取收支选择
        String parent = parentBox.getSelectedItem().toString();

        //若 parent == “-请选择-” 则 子分类 == “-请选择-”
        if("-请选择-".equals(parent))
            this.sortBox.setModel(new DefaultComboBoxModel(new String[] {"-请选择-"}));

        //若 parent == “收入” 或 “支出” 则 从数据库中查询对应分类
        if("收入".equals(parent) || "支出".equals(parent)){
            List<Object> list = sortService.querySortNameByParent(parent);
            list.add(0,"-请选择-");
            this.sortBox.setModel(new DefaultComboBoxModel(list.toArray()));
        }
    }

    @Override
    public void confirm() {
        String parent = parentBox.getSelectedItem().toString();
        if("-请选择-".equals(parent)){
            JOptionPane.showMessageDialog(this,"请选择收/支");
            return;
        }
        String son = sortBox.getSelectedItem().toString();
        if("-请选择-".equals(son)){
            JOptionPane.showMessageDialog(this,"请选择子分类");
            return;
        }
        String account = accountTxt.getText();
        if(null==account || account.isEmpty()){
            JOptionPane.showMessageDialog(this,"请填写账户");
            return;
        }
        double money;
        try {
            money = Double.parseDouble(moneyTxt.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请填写正确的金额");
            return;
        }
        if(0>=money){
            JOptionPane.showMessageDialog(this,"所填写的金额应大于0");
            return;
        }
        String createtime = createtimeTxt.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(daytime);
            Date date2 = sdf.parse(createtime);
            if(0 > sdf.parse(daytime).compareTo(date2)){
                JOptionPane.showMessageDialog(this,"日期不应晚于"+daytime);
                return;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String ldesc = ldescTxt.getText();
        ledger.setParent(parent);ledger.setSid(ledgerService.getSidBySname(son));ledger.setAccount(account);
        ledger.setCreatetime(createtime);ledger.setMoney(money);ledger.setLdesc(ldesc);
        if(1==ledgerService.editLedger(ledger)) {
            this.dispose();
            flag = true;
        }else{
            JOptionPane.showMessageDialog(this, "编辑账务失败");
        }
    }
    public boolean flag(){
        return flag;
    }
}
