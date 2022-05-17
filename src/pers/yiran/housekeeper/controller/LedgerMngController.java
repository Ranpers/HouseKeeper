package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.domain.QueryForm;
import pers.yiran.housekeeper.services.LedgerService;
import pers.yiran.housekeeper.services.SortService;
import pers.yiran.housekeeper.view.AbstractLedgerMngDialog;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LedgerMngController extends AbstractLedgerMngDialog {
    private final SortService sortService = new SortService();
    private final LedgerService ledgerService = new LedgerService();
    private String maxDate;
    private String minDate;
    private String parent;
    private String son;
    private int page = 1;
    private int maxPage;

    public LedgerMngController(JFrame frame) {
        super(frame);
        queryLedger();
        currentPageLabel.setText("当前：1/" + maxPage + "页");
    }

    private String formatDouble(Object val) {
        return String.format("%.2f", (double) val);
    }

    @Override
    public void addLedger() {
        AddLedgerController addLedgerController = new AddLedgerController(this);
        addLedgerController.setVisible(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!addLedgerController.flag2()) return;
        try {
            if (addLedgerController.flag1(sdf.parse(minDate), sdf.parse(maxDate), parent, son)) {
                queryLedger(minDate, maxDate, parent, son, maxPage);
                this.page = maxPage;
                currentPageLabel.setText("当前：" + this.page + "/" + maxPage + "页");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (addLedgerController.flag())
            JOptionPane.showMessageDialog(this, "添加账务成功");
    }

    @Override
    public void editLedger() {
        int row = ledgerDataTable.getSelectedRow();
        if (0 > row) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的数据");
            return;
        }
        Ledger ledger = getLedgerByTableRow(row);
        if (null == ledger) {
            JOptionPane.showMessageDialog(this, "您选择的是空行");
            return;
        }
        EditLedgerController editLedgerController = new EditLedgerController(this, ledger);
        editLedgerController.setVisible(true);
        if (editLedgerController.flag()) {
            queryLedger(minDate, maxDate, parent, son, page);
            JOptionPane.showMessageDialog(this, "编辑账务成功");
        }
    }

    @Override
    public void deleteLedger() {
        int row = ledgerDataTable.getSelectedRow();
        if (0 > row) {
            JOptionPane.showMessageDialog(this, "没有选择数据");
            return;
        }
        Ledger ledger = getLedgerByTableRow(row);
        if (null == ledger) {
            JOptionPane.showMessageDialog(this, "您选择的是空行");
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "确定要删除吗？");
        if (JOptionPane.OK_OPTION == result) {
            if (1 == ledgerService.deleteLedger(ledger.getLid())) {
                queryLedger(minDate, maxDate, parent, son, page);
                if (maxPage < page)
                    page--;
                currentPageLabel.setText("当前：" + this.page + "/" + maxPage + "页");
                JOptionPane.showMessageDialog(this, "删除成功");
            }
        }
    }

    @Override
    public void queryLedger() {
        page = 1;
        String begin = beginDateTxt.getText();
        String end = endDateTxt.getText();
        String parent = Objects.requireNonNull(parentBox.getSelectedItem()).toString();
        String son = Objects.requireNonNull(sortBox.getSelectedItem()).toString();
        queryLedger(begin, end, parent, son, page);
        currentPageLabel.setText("当前：" + this.page + "/" + maxPage + "页");
    }

    private void queryLedger(String begin, String end, String parent, String son, int page) {
        this.minDate = begin;
        this.maxDate = end;
        this.parent = parent;
        this.son = son;
        QueryForm queryForm = new QueryForm(begin, end, parent, son, page);
        Map<String, Object> data;
        try {
            data = ledgerService.queryLedgerByQueryForm(queryForm);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Ledger> list = (List<Ledger>) data.get("ledger");
        maxPage = (int) Math.ceil((int) data.get("size") / 10.0);
        if (0 == maxPage)
            maxPage = 1;
        this.setTableModel(list);
        this.inMoneyTotalLabel.setText("总收入：" + formatDouble(data.get("in")) + "元");
        this.payMoneyTotalLabel.setText("总支出：" + formatDouble(data.get("out")) + "元");
    }

    /*
     *菜单联动
     */
    @Override
    public void parentChange() {

        //获取收支选择
        String parent = parentBox.getSelectedItem().toString();

        //若 parent == “-请选择-” 则 子分类 == “-请选择-”
        if ("-请选择-".equals(parent))
            sortBox.setModel(new DefaultComboBoxModel(new String[]{"-请选择-"}));

        //若 parent == “收入/支出” 则 从数据库中查询所有子分类
        if ("收入/支出".equals(parent)) {
            //调用services层方法 获取 List集合 并填充
            List<Object> list = sortService.querySortNameAll();
            list.add(0, "-请选择-");
            sortBox.setModel(new DefaultComboBoxModel(list.toArray()));
        }
        //若 parent == “收入” 或 “支出” 则 从数据库中查询对应分类
        if ("收入".equals(parent) || "支出".equals(parent)) {
            List<Object> list = sortService.querySortNameByParent(parent);
            list.add(0, "-请选择-");
            sortBox.setModel(new DefaultComboBoxModel<>(list.toArray()));
        }
    }

    @Override
    public void pie() {
        new ShapeController(this).setVisible(true);
    }

    @Override
    public void pageTurning(String op) {
        if ("pre".equals(op) && 1 < page) {
            page--;
        } else if ("next".equals(op) && maxPage > page) {
            page++;
        }
        currentPageLabel.setText("当前：" + this.page + "/" + maxPage + "页");
        queryLedger(minDate, maxDate, parent, son, page);
    }
}
