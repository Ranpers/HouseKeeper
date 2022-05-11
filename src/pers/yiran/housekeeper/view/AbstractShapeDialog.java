package pers.yiran.housekeeper.view;

import pers.yiran.housekeeper.tools.GUITools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

@SuppressWarnings("all")
public abstract class AbstractShapeDialog extends JDialog {

	public AbstractShapeDialog(JDialog dialog) {
		super(dialog);

	}

	private void init() {
		this.pack();
		GUITools.center(this);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public abstract List<String> getImagePaths();

	/*
	 * 调用子类重写方法getImagePaths,获取生成的图片的路径 遍历集合List,获取所有的图片路径 放到组件Label中
	 */
	private void addComponent() {
		List<String> imagePaths = getImagePaths();
		if (imagePaths == null) {
			return;
		}
		JPanel panel = new JPanel();
		this.add(panel);
		for (String imagePath : imagePaths) {
			try {
				Image image = ImageIO.read(new File(imagePath));
				panel.add(new JLabel(new ImageIcon(image)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void initDialog() {
		this.addComponent();
		this.init();
	}
}