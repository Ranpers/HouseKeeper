package pers.yiran.housekeeper.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import pers.yiran.housekeeper.tools.GUITools;

@SuppressWarnings("ALL")
public abstract class AbstractOperationSortDialog extends JDialog {
	protected JComboBox parentBox = new JComboBox(new DefaultComboBoxModel(new String[]{"=请选择=", "收入", "支出"}));//父分类
	protected JTextField snameTxt = new JTextField();//分类名称
	protected JTextArea sdescArea = new JTextArea();//说明文本域
	protected JLabel titleLabel = new JLabel("需要子类设置");
	
	private JButton cancelBtn = new JButton("取消");
	private JButton submitBtn = new JButton("确定");
	
	public AbstractOperationSortDialog(JDialog dialog) {
		super(dialog, true);
		this.initDialog();
	}
	
	public void initDialog() {
		init();
		addComponent();
		addListener();
	}
	
	private void init() {
		this.setResizable(false);
		this.setSize(350, 320);
		this.setLayout(null);
		GUITools.center(this);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void addComponent() {
		// 标题标签
		titleLabel.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
		titleLabel.setBounds(140, 10, 120, 28);
		this.add(titleLabel);

		// 父分类名称
		JLabel parentLabel = new JLabel("父分类：");
		parentLabel.setBounds(30, 50, 60, 28);
		parentBox.setBounds(100, 50, 150, 28);
		this.add(parentLabel);
		this.add(parentBox);
		
		// 分类名称
		JLabel snameLabel = new JLabel("分类名称：");
		snameLabel.setBounds(30, 90, 60, 28);
		snameTxt.setBounds(100, 90, 150, 28);
		this.add(snameLabel);
		this.add(snameTxt);

		// 分类说明
		JLabel sdescLabel = new JLabel("分类说明：");
		sdescLabel.setBounds(30, 130, 60, 28);
		sdescArea.setColumns(20);
		sdescArea.setRows(5);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 130, 200, 80);
		scrollPane.setViewportView(sdescArea);
		this.add(sdescLabel);
		this.add(scrollPane);
		
		//取消按钮
		cancelBtn.setBounds(80, 220, 90, 28);
		this.add(cancelBtn);
		
		//确定按钮
		submitBtn.setBounds(210, 220, 90, 28);
		this.add(submitBtn);
	}

	private void addListener() {
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AbstractOperationSortDialog.this.dispose();
			}
		});
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirm();
			}
		});
	}

	/**
	 * 点击确定按钮时调用
	 */
	public abstract void confirm();
}
