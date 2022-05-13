package pers.yiran.housekeeper.view;

import pers.yiran.housekeeper.domain.Sort;
import pers.yiran.housekeeper.tools.GUITools;
import pers.yiran.housekeeper.tools.ListTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public abstract class AbstractSortMngDialog extends JDialog {
	protected JTable sortDataTable = new JTable();//账户数据列表
	private final JButton closeBtn = new JButton("关闭");

	private final JButton addBtn = new JButton("添加");
	private final JButton editBtn = new JButton("编辑");
	private final JButton delBtn = new JButton("删除");

	public AbstractSortMngDialog(JFrame frame) {
		super(frame, true);
		this.initDialog();
	}

	protected void initDialog() {
		this.init();
		this.addComponent();
		this.addListener();
	}
	
	private void init() {
		this.setResizable(false);// 设置窗体大小不可变
		this.setTitle("分类管理");// 设置标题
		this.setSize(680, 400);// 设置大小
		GUITools.center(this);//设置居中
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);// 设置关闭按钮
		this.setTableModel(null);//查询所有分类列表信息并展示在表格中
	}
	
	private void addComponent() {
		// 设置标签标题
		JLabel titleLable = new JLabel();
		titleLable.setFont(new Font("宋体", Font.ITALIC, 18));
		titleLable.setText("分类管理");
		titleLable.setBounds(280, 20, 165, 20);
		this.add(titleLable);

		// 滚动面板
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 100, 620, 160);
		
		sortDataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//单选
		sortDataTable.getTableHeader().setReorderingAllowed(false);//列不能移动

		scrollPane.setViewportView(sortDataTable);
		this.add(scrollPane);
		
		// 按钮
		addBtn.setBounds(30, 290, 140, 28);
		this.add(addBtn);
		editBtn.setBounds(270, 290, 140, 28);
		this.add(editBtn);
		delBtn.setBounds(510, 290, 140, 28);
		this.add(delBtn);

		// 关闭按钮
		closeBtn.setBounds(570, 330, 80, 28);
		this.add(closeBtn);
	}

	/**
	 * 显示分类表格
	 */
	protected void setTableModel(List<Sort> sortList) {
		String[] colNames = new String[] {"ID", "分类名称", "父分类", "说明"};
		String[] propNames = new String[] {"sid", "sname", "parent", "sdesc"};
		if(sortList == null || sortList.size() == 0) {
			sortDataTable.setModel(new DefaultTableModel(new Object[][] {
					{null, null, null, null}, {null, null, null, null},
					{null, null, null, null}, {null, null, null, null},
					{null, null, null, null}, {null, null, null, null},
					{null, null, null, null}, {null, null, null, null}
				},colNames));
			sortDataTable.setEnabled(false);
			return;
		}
		try {
			sortDataTable.setModel(new ListTableModel<>(sortList, Sort.class, colNames, propNames));
			sortDataTable.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Sort getSortByTableRow(int row) {
		return ((ListTableModel<Sort>)sortDataTable.getModel()).getInstance(row);
	}

	/**
	 * 给组件添加监听器
	 */
	private void addListener() {
		closeBtn.addActionListener(evt -> AbstractSortMngDialog.this.dispose());

		addBtn.addActionListener(evt -> addSort());
		editBtn.addActionListener(evt -> editSort());
		delBtn.addActionListener(evt -> deleteSort());
		sortDataTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					if (e.getClickCount() >= 2) {
						editSort();
					}
				}
			}
		});
	}
	
	public abstract void addSort();
	public abstract void editSort();
	public abstract void deleteSort();
}
