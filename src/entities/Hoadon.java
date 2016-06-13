package entities;
// Generated May 10, 2016 4:25:58 PM by Hibernate Tools 5.1.0.Alpha1

import java.io.*;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Hoadon generated by hbm2java
 */
@Entity
@Table(name = "HOADON", catalog = "RMS")
public class Hoadon implements java.io.Serializable {

	private String maHd;
	private String maban;
	private int status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private int NumOfCus;
	
	public Hoadon() {
	}

	public Hoadon(String maHd, String maban, int status, String createdBy, Date createdDate,
			String updatedBy, Date updatedDate) {
		this.maHd = maHd;
		this.maban = maban;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id

	@Column(name = "MaHD", unique = true, nullable = false)
	public String getMaHd() {
		return this.maHd;
	}

	public void setMaHd(String maHd) {
		this.maHd = maHd;
	}

	@Column(name = "Maban", nullable = false)
	public String getMaban() {
		return this.maban;
	}

	public void setMaban(String maban) {
		this.maban = maban;
	}

	@Column(name = "Status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
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
	
	@Column(name = "NumOfCus", nullable = false)
	public int getNumOfCus() {
		return NumOfCus;
	}

	public void setNumOfCus(int numOfCus) {
		NumOfCus = numOfCus;
	}

}
