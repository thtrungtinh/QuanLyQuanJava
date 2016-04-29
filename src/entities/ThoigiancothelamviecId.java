package entities;
// Generated Apr 29, 2016 4:28:59 PM by Hibernate Tools 5.1.0.Alpha1

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ThoigiancothelamviecId generated by hbm2java
 */
@Embeddable
public class ThoigiancothelamviecId implements java.io.Serializable {

	private Serializable maCa;
	private Serializable maNguoiDung;

	public ThoigiancothelamviecId() {
	}

	public ThoigiancothelamviecId(Serializable maCa, Serializable maNguoiDung) {
		this.maCa = maCa;
		this.maNguoiDung = maNguoiDung;
	}

	@Column(name = "MaCa", nullable = false)
	public Serializable getMaCa() {
		return this.maCa;
	}

	public void setMaCa(Serializable maCa) {
		this.maCa = maCa;
	}

	@Column(name = "MaNguoiDung", nullable = false)
	public Serializable getMaNguoiDung() {
		return this.maNguoiDung;
	}

	public void setMaNguoiDung(Serializable maNguoiDung) {
		this.maNguoiDung = maNguoiDung;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ThoigiancothelamviecId))
			return false;
		ThoigiancothelamviecId castOther = (ThoigiancothelamviecId) other;

		return ((this.getMaCa() == castOther.getMaCa()) || (this.getMaCa() != null && castOther.getMaCa() != null
				&& this.getMaCa().equals(castOther.getMaCa())))
				&& ((this.getMaNguoiDung() == castOther.getMaNguoiDung())
						|| (this.getMaNguoiDung() != null && castOther.getMaNguoiDung() != null
								&& this.getMaNguoiDung().equals(castOther.getMaNguoiDung())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMaCa() == null ? 0 : this.getMaCa().hashCode());
		result = 37 * result + (getMaNguoiDung() == null ? 0 : this.getMaNguoiDung().hashCode());
		return result;
	}

}
