package model;

public class CaModel {
	private String MaCa;
	private String TenCa;
	public CaModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CaModel(String maCa, String tenCa) {
		super();
		MaCa = maCa;
		TenCa = tenCa;
	}
	public String getMaCa() {
		return MaCa;
	}
	public void setMaCa(String maCa) {
		MaCa = maCa;
	}
	public String getTenCa() {
		return TenCa;
	}
	public void setTenCa(String tenCa) {
		TenCa = tenCa;
	}
	
}
