package pers.yiran.housekeeper.domain;
/*
 * 根据 ledger 账务表 编写Ledger类 
 */
public class Ledger {
   /*
    * 账务表中 
    *    lid   parent  money  sid  account  createtime  ldesc  
    */
	private int lid;  //账务id
	private String parent;//父分类
	private double money;//金额
	private int sid;//分类id
	
	//添加一个没有对应关系字段 但是 为了显示更方便 
	private String sname ;//分类名称 为了显示时更加方便 
	
	private String account;//账户 
	private String createtime;// 创建时间 数据库中是date类型 为了显示简单,定义为String
	private String ldesc; //账务描述
	public Ledger() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ledger(int lid, String parent, double money, int sid, String sname, String account, String createtime,
			String ldesc) {
		super();
		this.lid = lid;
		this.parent = parent;
		this.money = money;
		this.sid = sid;
		this.sname = sname;
		this.account = account;
		this.createtime = createtime;
		this.ldesc = ldesc;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLdesc() {
		return ldesc;
	}
	public void setLdesc(String ldesc) {
		this.ldesc = ldesc;
	}
	@Override
	public String toString() {
		return "Ledger [账务id=" + lid + ", 父分类=" + parent + ", 金额=" + money + ", 分类id=" + sid + ", 分类名字=" + sname
				+ ", 账户=" + account + ", 创建时间=" + createtime + ", 描述=" + ldesc + "]";
	}
}
