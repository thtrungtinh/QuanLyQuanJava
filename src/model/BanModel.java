package model;

public class BanModel {
	
	private String maBan;	
	private String tenBan;
	public BanModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BanModel(String maBan, String tenBan) {
		super();
		this.maBan = maBan;
		this.tenBan = tenBan;
	}
	public String getMaBan() {
		return maBan;
	}
	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}
	public String getTenBan() {
		return tenBan;
	}
	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}
	
	
	
}
