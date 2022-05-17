package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.domain.Sort;
import pers.yiran.housekeeper.services.SortService;
import pers.yiran.housekeeper.view.AbstractOperationSortDialog;

import javax.swing.*;

public class AddSortController extends AbstractOperationSortDialog {

    public AddSortController(JDialog dialog) {
        super(dialog);
        titleLabel.setText("添加分类");
        super.setTitle("添加分类");
    }

    @SuppressWarnings("all")
    @Override
    public void confirm() {
        String parent = parentBox.getSelectedItem().toString();
        String sname = snameTxt.getText().trim();
        String sdesc = sdescArea.getText();
        if ("=请选择=".equals(parent)) {
            JOptionPane.showMessageDialog(this, "请选择分类", "错误提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (null == sname || "".equals(sname)) {
            JOptionPane.showMessageDialog(this, "请填写分类名称", "错误提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Sort sort = new Sort(0, snameTxt.getText(), parent, sdesc);
        int row = new SortService().addSort(sort);
        if (1 == row) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "添加分类成功", "操作成功", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "添加失败", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
}
