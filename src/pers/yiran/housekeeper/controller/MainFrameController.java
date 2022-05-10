package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.view.AbstractMainFrame;

public class MainFrameController extends AbstractMainFrame {
    @Override
    public void ledgerMng() {
        new LedgerMngController(this).setVisible(true);
    }

    @Override
    public void sortMng() {
        new SortMngController(this).setVisible(true);
    }
}
