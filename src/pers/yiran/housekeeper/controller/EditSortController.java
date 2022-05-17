package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.domain.Sort;
import pers.yiran.housekeeper.services.SortService;
import pers.yiran.housekeeper.view.AbstractOperationSortDialog;

import javax.swing.*;

@SuppressWarnings("all")
public class EditSortController extends AbstractOperationSortDialog {
    private Sort sort;
    private Sort sort1;

    public EditSortController(JDialog dialog, Sort sort) {
        super(dialog);
        titleLabel.setText("编辑分类");
        setTitle("编辑分类");
        this.sort = sort;
        parentBox.setSelectedItem(sort.getParent());
        snameTxt.setText(sort.getSname());
        sdescArea.setText(sort.getSdesc());
    }

    @Override
    public void confirm() {
        String parent = parentBox.getSelectedItem().toString();
        String sname = snameTxt.getText().trim();
        String sdesc = sdescArea.getText();
        if ("=请选择=".equals(parent)) {
            JOptionPane.showMessageDialog(this, "请选择分类", "错误提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (null == sname || sname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写分类名称", "错误提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        sort.setSname(snameTxt.getText());
        sort.setParent(parent);
        sort.setSdesc(sdesc);
        int row = new SortService().editSort(sort);
        if (1 == row) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "编辑分类成功", "操作成功", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "编辑失败", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
}
