package pers.yiran.housekeeper.view;

import pers.yiran.housekeeper.domain.Ledger;
import pers.yiran.housekeeper.tools.DateChooser;
import pers.yiran.housekeeper.tools.DateUtils;
import pers.yiran.housekeeper.tools.GUITools;
import pers.yiran.housekeeper.tools.ListTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;


public abstract class AbstractLedgerMngDialog extends JDialog {
	protected JTextField beginDateTxt = new JTextField(6);// 开始查询时间
	protected JTextField endDateTxt = new JTextField(6);// 结束查询时间
	protected JComboBox parentBox = new JComboBox();// 父分类下拉列表
	protected JComboBox sortBox = new JComboBox();// 分类下拉列表
	protected JTable ledgerDataTable = new JTable();// 账务数据列表
	protected JLabel inMoneyTotalLabel = new JLabel("总收入：0.00元", SwingConstants.CENTER);
	protected JLabel payMoneyTotalLabel = new JLabel("总支出：0.00元", SwingConstants.CENTER);
	protected JLabel currentPageLabel = new JLabel("当前：1/1页", SwingConstants.CENTER);

	private final JButton queryBtn = new JButton("查　询");// 查询按钮
	private final JButton pieBtn = new JButton("收/支比重统计");
	private final JButton closeBtn = new JButton("关闭");

	private final JButton addBtn = new JButton("添加");
	private final JButton editBtn = new JButton("编辑");
	private final JButton delBtn = new JButton("删除");
	private final JButton nextBtn = new JButton("下一页");
	private final JButton preBtn = new JButton("上一页");

	public AbstractLedgerMngDialog(JFrame frame) {
		super(frame, true);
		initDialog();
	}

	protected void initDialog() {
		this.init();
		this.addComponent();
		this.addListener();
	}

	private void init() {
		this.setResizable(false);// 设置窗体大小不可变
		this.setTitle("账务管理");// 设置标题
		this.setSize(680, 400);// 设置大小
		GUITools.center(this);// 设置居中
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);// 设置关闭按钮
	}

	private void addComponent() {
		// 设置标签标题
		JLabel titleLabel = new JLabel();
		titleLabel.setFont(new Font("宋体", Font.ITALIC, 18));
		titleLabel.setText("账务管理");
		titleLabel.setBounds(280, 20, 165, 20);
		this.add(titleLabel);

		// 起始日期
		JLabel beginDateLabel = new JLabel("起始：");
		beginDateLabel.setBounds(30, 70, 60, 28);
		this.beginDateTxt.setBounds(70, 70, 80, 28);
		this.beginDateTxt.setEditable(false);
		beginDateTxt.setText(String.format("%tF", DateUtils.getFirstDayOfMethod()));
		DateChooser.getInstance().register(this.beginDateTxt);

		this.add(beginDateLabel);
		this.add(this.beginDateTxt);

		// 结束日期
		JLabel endDateLabel = new JLabel("至：");
		endDateLabel.setBounds(160, 70, 30, 28);
		this.endDateTxt.setBounds(190, 70, 80, 28);
		this.endDateTxt.setEditable(false);
		endDateTxt.setText(String.format("%tF", new Date()));
		DateChooser.getInstance().register(this.endDateTxt);

		this.add(endDateLabel);
		this.add(this.endDateTxt);

		// 收支选择
		JLabel inAndPayLabel = new JLabel("收/支：");
		inAndPayLabel.setBounds(280, 70, 50, 28);
		parentBox.setModel(new DefaultComboBoxModel(new String[] { "-请选择-", "收入/支出", "收入", "支出" }));
		this.parentBox.setBounds(320, 70, 90, 28);
		this.add(inAndPayLabel);
		this.add(this.parentBox);

		// 收支项目
		JLabel sortLabel = new JLabel("分类：");
		sortLabel.setBounds(420, 70, 50, 28);
		sortBox.setModel(new DefaultComboBoxModel(new String[] { "-请选择-" }));
		this.sortBox.setBounds(460, 70, 110, 28);
		this.add(sortLabel);
		this.add(this.sortBox);

		// 查询按钮
		queryBtn.setBounds(580, 70, 70, 28);
		this.add(queryBtn);

		// 滚动面板
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 100, 620, 160);
		setTableModel(null);

		ledgerDataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 单选
		ledgerDataTable.getTableHeader().setReorderingAllowed(false);// 列不能移动

		scrollPane.setViewportView(ledgerDataTable);
		this.add(scrollPane);

		//当前页数
		this.currentPageLabel.setBounds(260, 260, 160, 28);
		this.add(currentPageLabel);
		// 总收入标签
		this.inMoneyTotalLabel.setBounds(500, 260, 145, 28);
		this.add(inMoneyTotalLabel);
		inMoneyTotalLabel.setForeground(new java.awt.Color(0, 102, 0));

		// 总支出标签
		this.payMoneyTotalLabel.setBounds(500, 275, 145, 28);
		this.add(this.payMoneyTotalLabel);
		payMoneyTotalLabel.setForeground(new java.awt.Color(255, 0, 0));

		// 按钮
		addBtn.setBounds(30, 300, 140, 28);
		this.add(addBtn);
		editBtn.setBounds(270, 330, 140, 28);
		this.add(editBtn);
		delBtn.setBounds(30, 330, 140, 28);
		this.add(delBtn);
		nextBtn.setBounds(340, 300, 70, 28);
		this.add(nextBtn);
		preBtn.setBounds(270, 300, 70, 28);
		this.add(preBtn);

		// 收支统计报表按钮
		pieBtn.setBounds(510, 300, 140, 28);
		this.add(pieBtn);

		// 关闭按钮
		closeBtn.setBounds(570, 330, 80, 28);
		this.add(closeBtn);
	}

	/**
	 * 通过表格行号获取账务对象
	 */
	@SuppressWarnings("unchecked")
	protected Ledger getLedgerByTableRow(int row) {
		return ((ListTableModel<Ledger>) ledgerDataTable.getModel()).getInstance(row);
	}

	public  void setBoxModel(List<Object> sortItems) {
		sortBox.setModel(new DefaultComboBoxModel(sortItems.toArray()));
	}

	/**
	 * 显示账务表格
	 */
	protected void setTableModel(List<Ledger> ledgerList) {
		String[] colNames = new String[] { "ID", "收/支", "分类", "金额", "账户", "创建时间", "说明" };
		String[] propNames = new String[] { "lid", "parent", "sname", "money", "account", "createtime", "ldesc" };
		if (ledgerList == null || ledgerList.size() == 0) {
			ledgerDataTable
					.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null } }, colNames));
			ledgerDataTable.setEnabled(false);
			return;
		}
		try {
			ledgerDataTable.setModel(new ListTableModel<>(ledgerList, Ledger.class, colNames, propNames));
			ledgerDataTable.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给组件添加监听器
	 */
	private void addListener() {
		queryBtn.addActionListener(evt -> queryLedger());
		pieBtn.addActionListener(evt -> pie());
		closeBtn.addActionListener(evt -> AbstractLedgerMngDialog.this.dispose());

		addBtn.addActionListener(evt -> addLedger());
		editBtn.addActionListener(evt -> editLedger());
		delBtn.addActionListener(evt -> deleteLedger());
		preBtn.addActionListener(e -> pageTurning("pre"));
		nextBtn.addActionListener(e -> pageTurning("next"));
		parentBox.addActionListener(evt -> {
			parentChange();
		});
		ledgerDataTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					if (e.getClickCount() >= 2) {
						editLedger();
					}
				}
			}
		});
	}

	/**
	 * 添加账务
	 */
	public abstract void addLedger();

	/**
	 * 添加账务
	 */
	public abstract void editLedger();

	/**
	 * 删除账务
	 */
	public abstract void deleteLedger();

	/**
	 * 查询账务
	 *
	 */
	public abstract void queryLedger();

	/**
	 * 父分类变化
	 */
	public abstract void parentChange();

	/**
	 * 生成饼形图
	 */
	public abstract void pie();

	/**
	 * 根据传参 账务翻页
	 */
	public abstract void pageTurning(String op);
}
