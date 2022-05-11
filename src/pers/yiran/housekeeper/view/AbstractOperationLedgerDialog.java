package pers.yiran.housekeeper.view;

import pers.yiran.housekeeper.tools.DateChooser;
import pers.yiran.housekeeper.tools.GUITools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;




/**
 * 
 * @author apple
 * 账务操作对话框，实现添加账务和编辑账务
 */
public abstract class AbstractOperationLedgerDialog extends JDialog {

	protected JComboBox parentBox = new JComboBox();//大分类下拉列表
	protected JComboBox sortBox = new JComboBox();//小分类下拉列表
	protected JTextField accountTxt = new JTextField();//账户文本框
	protected JTextField moneyTxt = new JTextField("0.0");//金额文本框
	protected JTextField createtimeTxt = new JTextField();//创建日期文本框
	protected JTextArea ldescTxt = new JTextArea();//说明文本域
	protected JLabel titleLabel = new JLabel("需要子类设置");

	private final JButton cancelBtn = new JButton("取消");
	private final JButton submitBtn = new JButton("确定");

	public AbstractOperationLedgerDialog(JDialog dialog) {
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
		this.setSize(400, 450);
		this.setLayout(null);
		GUITools.center(this);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void addComponent() {
		// 标题标签
		titleLabel.setFont(new java.awt.Font("宋体", 0, 18)); //
		titleLabel.setBounds(160, 10, 120, 28);
		this.add(titleLabel);

		// "选择"下拉列表
		JLabel parentLabel = new JLabel("收/支：");
		parentLabel.setBounds(30, 50, 50, 28);
		parentBox.setModel(new DefaultComboBoxModel(new String[] {"-请选择-", "收入", "支出"}));
		parentBox.setBounds(70, 50, 100, 28);
		this.add(parentLabel);
		this.add(parentBox);
		
		// 分类
		JLabel sortLabel = new JLabel("分类：");
		sortLabel.setBounds(30, 90, 50, 28);
		sortBox.setModel(new DefaultComboBoxModel(new String[] {"-请选择-"}));
		sortBox.setBounds(70, 90, 100, 28);
		this.add(sortLabel);
		this.add(sortBox);

		// 账户
		JLabel accountLabel = new JLabel("账户：");
		accountLabel.setBounds(30, 130, 50, 28);
		
		accountTxt.setBounds(70, 130, 200, 28);
		this.add(accountLabel);
		this.add(accountTxt);

		// 金额
		JLabel moneyLabel = new JLabel("金额：");
		moneyLabel.setBounds(30, 170, 50, 28);
		moneyTxt.setBounds(70, 170, 100, 28);
		moneyTxt.setHorizontalAlignment(JTextField.RIGHT);// 文本右对齐
		this.add(moneyLabel);
		this.add(this.moneyTxt);
		
		// 时间
		JLabel createtimeLabel = new JLabel("时间：");
		createtimeLabel.setBounds(30, 210, 50, 28);
		createtimeTxt.setBounds(70, 210, 80, 28);
		createtimeTxt.setText(String.format("%tF", new Date()));
		DateChooser.getInstance().register(createtimeTxt);
		this.add(createtimeLabel);
		this.add(this.createtimeTxt);

		// 说明
		JLabel ldescLabel = new JLabel("说明：");
		ldescLabel.setBounds(30, 250, 50, 28);
		ldescTxt.setColumns(20);
		ldescTxt.setRows(5);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 250, 260, 80);
		scrollPane.setViewportView(ldescTxt);
		this.add(ldescLabel);
		this.add(scrollPane);
		
		//取消按钮
		cancelBtn.setBounds(30, 340, 90, 28);
		this.add(cancelBtn);
		
		//确定按钮
		submitBtn.setBounds(260, 340, 90, 28);
		this.add(submitBtn);		
	}

	private void addListener() {
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AbstractOperationLedgerDialog.this.dispose();
			}
		});
		/*
		 * 点击确定按钮的事件监听器
		 */
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirm();
			}
		});
		/*
		 * 分类菜单的事件监听器
		 * 实现菜单联动
		 */
		parentBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				changeParent();
			}
		});
	}
	
	/**
	 * 父分类变化时调用本方法
	 */
	public abstract void changeParent();

	/**
	 * 点击确定按钮时调用的方法
	 */
	public abstract void confirm();
}
