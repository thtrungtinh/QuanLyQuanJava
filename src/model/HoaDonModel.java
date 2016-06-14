package model;

public class HoaDonModel {
	
	private String maHD;
	private String maBan;
	private String tenBan;
	private int tongTien = 0;
	private int NumOfCus;
	public HoaDonModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDonModel(String maHD, String maBan, String tenBan, int tongTien, int numOfCus) {
		super();
		this.maHD = maHD;
		this.maBan = maBan;
		this.tenBan = tenBan;
		this.tongTien = tongTien;
		NumOfCus = numOfCus;
	}
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
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
	public int getTongTien() {
		return tongTien;
	}
	public void setTongTien(int tongTien) {
		this.tongTien = tongTien;
	}
	public int getNumOfCus() {
		return NumOfCus;
	}
	public void setNumOfCus(int numOfCus) {
		NumOfCus = numOfCus;
	}
	
	
	
	

}
