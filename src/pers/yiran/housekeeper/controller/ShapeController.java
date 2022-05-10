package pers.yiran.housekeeper.controller;

import pers.yiran.housekeeper.view.AbstractShapeDialog;

import javax.swing.*;
import java.util.List;

public class ShapeController extends AbstractShapeDialog {
    public ShapeController(JDialog dialog) {
        super(dialog);
    }

    @Override
    public List<String> getImagePaths() {
        return null;
    }
}
