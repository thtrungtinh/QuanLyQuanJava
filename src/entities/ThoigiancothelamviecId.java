package entities;
// Generated Apr 28, 2016 7:02:44 PM by Hibernate Tools 3.4.0.CR1

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ThoigiancothelamviecId generated by hbm2java
 */
@Embeddable
public class ThoigiancothelamviecId implements java.io.Serializable {

	private byte[] maCa;
	private byte[] maNguoiDung;

	public ThoigiancothelamviecId() {
	}

	public ThoigiancothelamviecId(byte[] maCa, byte[] maNguoiDung) {
		this.maCa = maCa;
		this.maNguoiDung = maNguoiDung;
	}

	@Column(name = "MaCa", nullable = false)
	public byte[] getMaCa() {
		return this.maCa;
	}

	public void setMaCa(byte[] maCa) {
		this.maCa = maCa;
	}

	@Column(name = "MaNguoiDung", nullable = false)
	public byte[] getMaNguoiDung() {
		return this.maNguoiDung;
	}

	public void setMaNguoiDung(byte[] maNguoiDung) {
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
				&& Arrays.equals(this.getMaCa(), castOther.getMaCa())))
				&& ((this.getMaNguoiDung() == castOther.getMaNguoiDung())
						|| (this.getMaNguoiDung() != null && castOther.getMaNguoiDung() != null
								&& Arrays.equals(this.getMaNguoiDung(), castOther.getMaNguoiDung())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMaCa() == null ? 0 : Arrays.hashCode(this.getMaCa()));
		result = 37 * result + (getMaNguoiDung() == null ? 0 : Arrays.hashCode(this.getMaNguoiDung()));
		return result;
	}

}
