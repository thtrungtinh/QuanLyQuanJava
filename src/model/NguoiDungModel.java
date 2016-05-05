package model;

import java.util.Date;

public class NguoiDungModel {
	private String maNguoiDung;
	private String matKhau;
	private String tenNguoiDung;
	private String dienGiai;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String dienThoai;
	private String diaChi;
	private String cmnd;
	private String maViTri;
	private String maTrinhDo;
	private boolean status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String tenViTri;
	private String tenTrinhDo;

	public NguoiDungModel() {
	}

	public NguoiDungModel(String maNguoiDung, String matKhau, String tenNguoiDung, String dienGiai,
			boolean gioiTinh, Date ngaySinh, String dienThoai, String diaChi, String cmnd,
			String maViTri, String maTrinhDo, boolean status, String createdBy, Date createdDate,
			String updatedBy, Date updatedDate, String tenViTri, String tenTrinhDo) {
		this.maNguoiDung = maNguoiDung;
		this.matKhau = matKhau;
		this.tenNguoiDung = tenNguoiDung;
		this.dienGiai = dienGiai;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.dienThoai = dienThoai;
		this.diaChi = diaChi;
		this.cmnd = cmnd;
		this.maViTri = maViTri;
		this.maTrinhDo = maTrinhDo;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.tenViTri = tenViTri;
		this.tenTrinhDo = tenTrinhDo;
	}

	
	public String getMaNguoiDung() {
		return this.maNguoiDung;
	}

	public void setMaNguoiDung(String maNguoiDung) {
		this.maNguoiDung = maNguoiDung;
	}

	public String getMatKhau() {
		return this.matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getTenNguoiDung() {
		return this.tenNguoiDung;
	}

	public void setTenNguoiDung(String tenNguoiDung) {
		this.tenNguoiDung = tenNguoiDung;
	}

	public String getDienGiai() {
		return this.dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public boolean isGioiTinh() {
		return this.gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return this.ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDienThoai() {
		return this.dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public String getDiaChi() {
		return this.diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getCmnd() {
		return this.cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getMaViTri() {
		return this.maViTri;
	}

	public void setMaViTri(String maViTri) {
		this.maViTri = maViTri;
	}

	public String getMaTrinhDo() {
		return this.maTrinhDo;
	}

	public void setMaTrinhDo(String maTrinhDo) {
		this.maTrinhDo = maTrinhDo;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getTenViTri() {
		return this.tenViTri;
	}

	public void setTenViTri(String tenViTri) {
		this.tenViTri = tenViTri;
	}

	public String getTenTrinhDo() {
		return this.tenTrinhDo;
	}

	public void setTenTrinhDo(String maTrinhDo) {
		this.tenTrinhDo = maTrinhDo;
	}
}
