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
public class AddLedgerController extends AbstractOperationLedgerDialog {
    SortService sortService = new SortService();
    LedgerService ledgerService = new LedgerService();
    private boolean flag = false;
    private final String daytime = createtimeTxt.getText();
    public AddLedgerController(JDialog dialog) {
        super(dialog);
        titleLabel.setText("添加账务");
        this.setTitle("添加账务");
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
            JOptionPane.showMessageDialog(this,"请选择收支");
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
        Ledger ledger = new Ledger(0,parent,money,0,son,account,createtime,ldesc);
        if(1==ledgerService.addLedger(ledger)) {
            this.setVisible(false);
            flag = true;
        }else{
            JOptionPane.showMessageDialog(this, "添加账务失败");
        }
    }
    public boolean flag(){
        return flag;
    }
    public boolean flag1(Date begin,Date end,String parent,String son){
        String createtime = createtimeTxt.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(createtime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String parent1 = parentBox.getSelectedItem().toString();
        String son1 = sortBox.getSelectedItem().toString();
        this.dispose();
        if(!(0>date.compareTo(begin))&&!(0< date.compareTo(end))&&parent1==parent&&son1==son)
            return true;
        if(!(0>date.compareTo(begin))&&!(0< date.compareTo(end))&&"-请选择-"==parent&&"-请选择-"==son)
            return true;
        return false;
    }
}
