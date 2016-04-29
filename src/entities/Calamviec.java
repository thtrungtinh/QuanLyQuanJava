package entities;
// Generated Apr 28, 2016 7:02:44 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Calamviec generated by hbm2java
 */
@Entity
@Table(name = "CALAMVIEC", catalog = "RMS")
public class Calamviec implements java.io.Serializable {

	private byte[] maCa;
	private byte[] batDau;
	private byte[] createdBy;
	private Date createdDate;
	private byte[] dienGiai;
	private int id;
	private byte[] ketThuc;
	private boolean status;
	private byte[] tenCa;
	private byte[] updatedBy;
	private Date updatedDate;

	public Calamviec() {
	}

	public Calamviec(byte[] maCa, byte[] batDau, byte[] createdBy, Date createdDate, byte[] dienGiai, int id,
			byte[] ketThuc, boolean status, byte[] tenCa, byte[] updatedBy, Date updatedDate) {
		this.maCa = maCa;
		this.batDau = batDau;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.dienGiai = dienGiai;
		this.id = id;
		this.ketThuc = ketThuc;
		this.status = status;
		this.tenCa = tenCa;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id

	@Column(name = "MaCa", unique = true, nullable = false)
	public byte[] getMaCa() {
		return this.maCa;
	}

	public void setMaCa(byte[] maCa) {
		this.maCa = maCa;
	}

	@Column(name = "BatDau", nullable = false)
	public byte[] getBatDau() {
		return this.batDau;
	}

	public void setBatDau(byte[] batDau) {
		this.batDau = batDau;
	}

	@Column(name = "CreatedBy", nullable = false)
	public byte[] getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(byte[] createdBy) {
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

	@Column(name = "DienGiai", nullable = false)
	public byte[] getDienGiai() {
		return this.dienGiai;
	}

	public void setDienGiai(byte[] dienGiai) {
		this.dienGiai = dienGiai;
	}

	@Column(name = "ID", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "KetThuc", nullable = false)
	public byte[] getKetThuc() {
		return this.ketThuc;
	}

	public void setKetThuc(byte[] ketThuc) {
		this.ketThuc = ketThuc;
	}

	@Column(name = "Status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "TenCa", nullable = false)
	public byte[] getTenCa() {
		return this.tenCa;
	}

	public void setTenCa(byte[] tenCa) {
		this.tenCa = tenCa;
	}

	@Column(name = "UpdatedBy", nullable = false)
	public byte[] getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(byte[] updatedBy) {
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