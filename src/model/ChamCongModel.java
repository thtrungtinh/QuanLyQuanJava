package model;

import java.util.Date;

public class ChamCongModel {

	private int maChamCong;
	private String maCa;
	private String maNguoiDung;
	private String dienGiai;	
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String tenCa;
	private String tenNguoiDung;
	private int ngay;
	private int thang;
	private int nam;
	private int luongCaTheoNgay;
	
	public ChamCongModel() {
		
	}	
	
	
	public int getMaChamCong() {
		return maChamCong;
	}


	public void setMaChamCong(int maChamCong) {
		this.maChamCong = maChamCong;
	}


	public String getMaCa() {
		return maCa;
	}

	public void setMaCa(String maCa) {
		this.maCa = maCa;
	}

	public String getMaNguoiDung() {
		return maNguoiDung;
	}

	public void setMaNguoiDung(String maNguoiDung) {
		this.maNguoiDung = maNguoiDung;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getTenCa() {
		return tenCa;
	}

	public void setTenCa(String tenCa) {
		this.tenCa = tenCa;
	}

	public String getTenNguoiDung() {
		return tenNguoiDung;
	}

	public void setTenNguoiDung(String tenNguoiDung) {
		this.tenNguoiDung = tenNguoiDung;
	}

	public int getNgay() {
		return ngay;
	}

	public void setNgay(int ngay) {
		this.ngay = ngay;
	}

	public int getThang() {
		return thang;
	}

	public void setThang(int thang) {
		this.thang = thang;
	}

	public int getNam() {
		return nam;
	}

	public void setNam(int nam) {
		this.nam = nam;
	}


	public int getLuongCaTheoNgay() {
		return luongCaTheoNgay;
	}


	public void setLuongCaTheoNgay(int luongCaTheoNgay) {
		this.luongCaTheoNgay = luongCaTheoNgay;
	}
	
	

}
