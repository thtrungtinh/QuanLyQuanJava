package model;

public class ThucDonModel {
	private String MaThucDon;
	private String TenThucDon;
	public ThucDonModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ThucDonModel(String maThucDon, String tenThucDon) {
		super();
		MaThucDon = maThucDon;
		TenThucDon = tenThucDon;
	}
	public String getMaThucDon() {
		return MaThucDon;
	}
	public void setMaThucDon(String maThucDon) {
		MaThucDon = maThucDon;
	}
	public String getTenThucDon() {
		return TenThucDon;
	}
	public void setTenThucDon(String tenThucDon) {
		TenThucDon = tenThucDon;
	}
	
}
