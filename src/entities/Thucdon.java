package entities;
// Generated May 9, 2016 9:46:32 AM by Hibernate Tools 5.1.0.Alpha1

import java.io.*;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Thucdon generated by hbm2java
 */
@Entity
@Table(name = "THUCDON", catalog = "RMS")
public class Thucdon implements java.io.Serializable {

	private String maThucDon;
	private String tenThucDon;
	private byte[] hinhAnh;
	private String dienGiai;
	private int gia;
	private String maNhom;
	private boolean status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	public Thucdon() {
	}

	

	public Thucdon(String maThucDon, String tenThucDon, byte[] hinhAnh, String dienGiai, int gia,
			String maNhom, boolean status, String createdBy, Date createdDate, String updatedBy,
			Date updatedDate) {
		this.maThucDon = maThucDon;
		this.tenThucDon = tenThucDon;
		this.hinhAnh = hinhAnh;
		this.dienGiai = dienGiai;
		this.gia = gia;
		this.maNhom = maNhom;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id

	@Column(name = "MaThucDon", unique = true, nullable = false)
	public String getMaThucDon() {
		return this.maThucDon;
	}

	public void setMaThucDon(String maThucDon) {
		this.maThucDon = maThucDon;
	}

	@Column(name = "TenThucDon", nullable = false)
	public String getTenThucDon() {
		return this.tenThucDon;
	}

	public void setTenThucDon(String tenThucDon) {
		this.tenThucDon = tenThucDon;
	}

	@Column(name = "HinhAnh", nullable = true)
	public byte[] getHinhAnh() {
		return this.hinhAnh;
	}

	public void setHinhAnh(byte[] hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	@Column(name = "DienGiai", nullable = false)
	public String getDienGiai() {
		return this.dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	@Column(name = "Gia", nullable = false)
	public int getGia() {
		return this.gia;
	}

	public void setGia(int gia) {
		this.gia = gia;
	}

	@Column(name = "MaNhom", nullable = false)
	public String getMaNhom() {
		return this.maNhom;
	}

	public void setMaNhom(String maNhom) {
		this.maNhom = maNhom;
	}

	@Column(name = "Status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "CreatedBy", nullable = false)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedDate", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UpdatedBy", nullable = false)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedDate", nullable = false, length = 23)
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
