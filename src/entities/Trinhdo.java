package entities;
// Generated Apr 29, 2016 4:28:59 PM by Hibernate Tools 5.1.0.Alpha1

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Trinhdo generated by hbm2java
 */
@Entity
@Table(name = "TRINHDO", catalog = "RMS")
public class Trinhdo implements java.io.Serializable {

	private Serializable maTrinhDo;
	private int id;
	private Serializable tenTrinhDo;
	private Serializable dienGiai;
	private boolean status;
	private Serializable createdBy;
	private Date createdDate;
	private Serializable updatedBy;
	private Date updatedDate;

	public Trinhdo() {
	}

	public Trinhdo(Serializable maTrinhDo, int id, Serializable tenTrinhDo, Serializable dienGiai, boolean status,
			Serializable createdBy, Date createdDate, Serializable updatedBy, Date updatedDate) {
		this.maTrinhDo = maTrinhDo;
		this.id = id;
		this.tenTrinhDo = tenTrinhDo;
		this.dienGiai = dienGiai;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id

	@Column(name = "MaTrinhDo", unique = true, nullable = false)
	public Serializable getMaTrinhDo() {
		return this.maTrinhDo;
	}

	public void setMaTrinhDo(Serializable maTrinhDo) {
		this.maTrinhDo = maTrinhDo;
	}

	@Column(name = "ID", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "TenTrinhDo", nullable = false)
	public Serializable getTenTrinhDo() {
		return this.tenTrinhDo;
	}

	public void setTenTrinhDo(Serializable tenTrinhDo) {
		this.tenTrinhDo = tenTrinhDo;
	}

	@Column(name = "DienGiai", nullable = false)
	public Serializable getDienGiai() {
		return this.dienGiai;
	}

	public void setDienGiai(Serializable dienGiai) {
		this.dienGiai = dienGiai;
	}

	@Column(name = "Status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "CreatedBy", nullable = false)
	public Serializable getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Serializable createdBy) {
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
	public Serializable getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Serializable updatedBy) {
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
