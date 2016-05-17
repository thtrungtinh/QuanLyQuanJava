package model;

public class ChiTietHoaDonModel {
	
	private int iD;
	private String maHD;
	private String maThucDon;
	private int soLuong;
	private int gia;
	private String ghiChu;
	private String tenThucDon;
	
	public ChiTietHoaDonModel(){}
	
	public ChiTietHoaDonModel(int iD, String maHD, String maThucDon, int soLuong, int gia, String ghiChu,
			String tenThucDon) {
		super();
		this.iD = iD;
		this.maHD = maHD;
		this.maThucDon = maThucDon;
		this.soLuong = soLuong;
		this.gia = gia;
		this.ghiChu = ghiChu;
		this.tenThucDon = tenThucDon;
	}


	public int getiD() {
		return iD;
	}


	public void setiD(int iD) {
		this.iD = iD;
	}


	public String getMaHD() {
		return maHD;
	}


	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}


	public String getMaThucDon() {
		return maThucDon;
	}


	public void setMaThucDon(String maThucDon) {
		this.maThucDon = maThucDon;
	}


	public int getSoLuong() {
		return soLuong;
	}


	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}


	public int getGia() {
		return gia;
	}


	public void setGia(int gia) {
		this.gia = gia;
	}


	public String getGhiChu() {
		return ghiChu;
	}


	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}


	public String getTenThucDon() {
		return tenThucDon;
	}


	public void setTenThucDon(String tenThucDon) {
		this.tenThucDon = tenThucDon;
	}
	
	
	
}
