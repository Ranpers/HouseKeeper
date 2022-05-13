package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.domain.Sort;
import pers.yiran.housekeeper.services.LedgerService;
import pers.yiran.housekeeper.services.SortService;
import pers.yiran.housekeeper.view.AbstractSortMngDialog;

import javax.swing.*;

@SuppressWarnings("all")
public class SortMngController extends AbstractSortMngDialog {
    private SortService sortService = new SortService();
    public SortMngController(JFrame frame) {
        super(frame);
        setTableModel(sortService.querySortAll());
    }

    @Override
    public void addSort() {
        new AddSortController(this).setVisible(true);
        refresh();
    }

    @Override
    public void editSort() {
        int row = sortDataTable.getSelectedRow();
        Sort sort = getSortByTableRow(row);
        if(row<0){
            JOptionPane.showMessageDialog(this,"请选择数据");
            return;
        }
        if(null==sort){
            JOptionPane.showMessageDialog(this,"选择的是空行");
            return;
        }
        new EditSortController(this, sort).setVisible(true);
        refresh();
    }

    @Override
    public void deleteSort() {
        int row = sortDataTable.getSelectedRow();
        Sort sort = getSortByTableRow(row);
        if(0>row){
            JOptionPane.showMessageDialog(this,"请选择数据");
            return;
        }
        if(null==sort){
            JOptionPane.showMessageDialog(this,"选择的是空行");
            return;
        }
        int choose = JOptionPane.showConfirmDialog(this,"是否确定删除？","删除提示",JOptionPane.YES_NO_OPTION);
        if(JOptionPane.YES_OPTION == choose) {
            int flag1 = sortService.deleteSort(sort);
            int flag2 = new LedgerService().deleteLedgerBySort(sort.getSid());
            if (1 != flag1 || flag2 < 0) {
                JOptionPane.showMessageDialog(this, "删除失败", "错误提示", JOptionPane.ERROR_MESSAGE);
            } else {
                refresh();
                JOptionPane.showMessageDialog(this, "删除成功", "操作成功", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public void refresh(){setTableModel(sortService.querySortAll());}
}
