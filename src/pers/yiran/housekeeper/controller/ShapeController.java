package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.services.LedgerService;
import pers.yiran.housekeeper.tools.DateUtils;
import pers.yiran.housekeeper.tools.JFreeChartUtils;
import pers.yiran.housekeeper.view.AbstractShapeDialog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShapeController extends AbstractShapeDialog {
    private final LedgerService ledgerService = new LedgerService();

    public ShapeController(JDialog dialog) {
        super(dialog);
        initDialog();
    }

    @Override
    public List<String> getImagePaths() {
        List<String> listPath = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("支出");
        list.add("收入");
        Double money;
        Map<String, Double> map;
        String title;
        for (String parent : list) {
            money = ledgerService.queryTotalMoneyByParent(parent);
            map = ledgerService.querySumMoneyBySort(parent);
            title = parent + " 占比图(" + money + ") (" + DateUtils.getYear() + "年)";
            JFreeChartUtils.pie(title, map, money, "source//pie//" + parent + ".png");
            listPath.add("source//pie//" + parent + ".png");
        }
        return listPath;
    }
}
