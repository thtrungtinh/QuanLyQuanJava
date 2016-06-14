package model;

public class NhanVienModel {

	private String MaNguoiDung;
	private String TenNguoiDung;
	
	public NhanVienModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMaNguoiDung() {
		return MaNguoiDung;
	}

	public void setMaNguoiDung(String maNguoiDung) {
		MaNguoiDung = maNguoiDung;
	}

	public String getTenNguoiDung() {
		return TenNguoiDung;
	}

	public void setTenNguoiDung(String tenNguoiDung) {
		TenNguoiDung = tenNguoiDung;
	}

	public NhanVienModel(String maNguoiDung, String tenNguoiDung) {
		super();
		MaNguoiDung = maNguoiDung;
		TenNguoiDung = tenNguoiDung;
	}
	
}
