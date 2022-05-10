package pers.yiran.housekeeper.tools;


import java.awt.*;

public class GUITools {
	public static void center(Component c) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int x = (kit.getScreenSize().width - c.getWidth()) / 2;
		int y = (kit.getScreenSize().height - c.getHeight()) / 2;
		c.setLocation(x, y);
	}
}
