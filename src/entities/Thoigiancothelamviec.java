package entities;
// Generated May 6, 2016 11:58:11 AM by Hibernate Tools 5.1.0.Alpha1

import java.io.*;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Thoigiancothelamviec generated by hbm2java
 */
@Entity
@Table(name = "THOIGIANCOTHELAMVIEC", catalog = "RMS")
public class Thoigiancothelamviec implements java.io.Serializable {

	private ThoigiancothelamviecId id;
	private String dienGiai;
	private boolean status;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	public Thoigiancothelamviec() {
	}

	public Thoigiancothelamviec(ThoigiancothelamviecId id, String dienGiai, boolean status,
			String createdBy, Date createdDate, String updatedBy, Date updatedDate) {
		this.id = id;
		this.dienGiai = dienGiai;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "maCa", column = @Column(name = "MaCa", nullable = false)),
			@AttributeOverride(name = "maNguoiDung", column = @Column(name = "MaNguoiDung", nullable = false)) })
	public ThoigiancothelamviecId getId() {
		return this.id;
	}

	public void setId(ThoigiancothelamviecId id) {
		this.id = id;
	}

	@Column(name = "DienGiai", nullable = false)
	public String getDienGiai() {
		return this.dienGiai;
	}

	public void setDienGiai(String dienGiai) {
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
