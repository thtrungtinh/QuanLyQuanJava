package dao;

import entities.*;
import model.NguoiDungModel;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.transform.Transformers;

public class NguoiDungDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
	/**
     * Load Nguoidung theo ma nguoi dung va mat khau 
     *
     * @return Nguoidung entities
     */
	public Nguoidung GetListID(String maNguoiDung, String matKhau) {
		try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			return (Nguoidung) sessionFactory.getCurrentSession()
                .createSQLQuery(" exec NGUOIDUNG_GetListID @MaNguoiDung = '" + maNguoiDung +"' , @MatKhau='"+ matKhau +"'")
                .addEntity(Nguoidung.class).uniqueResult();                  
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
		
		
	/**
     * Load list danh sach 
     *
     * @return List<Nguoidung>
     */
	public List<Nguoidung> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Nguoidung> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Nguoidung").list();
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			return list;
	}
	
	/**
     * Kiem tra ma da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String maNguoiDung) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NGUOIDUNG_CheckInsert(?,?)}");
            cstmt.setString("MaNguoiDung", maNguoiDung);
            cstmt.registerOutParameter("Message", java.sql.Types.NVARCHAR);
            cstmt.execute();            
            errMessage = cstmt.getNString("Message");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
        }
        return errMessage;
    }
    
    /**
     * Them
     *
     */
    
    public String Insert(Nguoidung entity) {
		String errMesage = "";
    	Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			errMesage = "Thêm mới thành công";
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("Error: " + e.toString());
			errMesage = "Lỗi cập nhật, không thể thêm !";
		} finally {
			session.close();
		}
		return errMesage;
	}
    
    /**
     * Kiem tra ma duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String maNguoiDung) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NGUOIDUNG_CheckEdit(?,?)}");
            cstmt.setString("MaNguoiDung", maNguoiDung);
            cstmt.registerOutParameter("Message", java.sql.Types.NVARCHAR);
            cstmt.execute();
            errMessage = cstmt.getNString("Message");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
        }
        return errMessage;
    }
    
    /**
     * cap nhat 
     *
     * 
     */
    
	public void UpdateData(String maNguoiDung, String matKhau, String tenNguoiDung, String dienGiai, boolean gioiTinh, Date ngaySinh, String dienThoai, String diaChi, String cmnd, String maViTri, String maTrinhDo, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Nguoidung entity = (Nguoidung) session.get(Nguoidung.class, maNguoiDung);
			
			entity.setMatKhau(matKhau);
			entity.setTenNguoiDung(tenNguoiDung);
			entity.setDienGiai(dienGiai);
			entity.setGioiTinh(gioiTinh);
			entity.setNgaySinh(ngaySinh);
			entity.setDienThoai(dienThoai);
			entity.setDiaChi(diaChi);
			entity.setCmnd(cmnd);
			entity.setMaViTri(maViTri);
			entity.setMaTrinhDo(maTrinhDo);
			entity.setStatus(status);
						
			entity.setUpdatedBy(DataService.GetUserID());
			entity.setUpdatedDate(new Date());
			
			session.update(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
    
    /**
     * Kiem tra ma co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String maNguoiDung) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NGUOIDUNG_CheckDelete(?,?)}");
            cstmt.setString("MaNguoiDung", maNguoiDung);
            cstmt.registerOutParameter("Message", java.sql.Types.NVARCHAR);
            cstmt.execute();
            errMessage = cstmt.getNString("Message");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
        }
        return errMessage;
    }
    
    /**
     * Xoa 
     *
     */
    public void Delete(String maNguoiDung) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Nguoidung entity = (Nguoidung) session.get(Nguoidung.class, maNguoiDung);
            session.delete(entity);
            tx.commit();
        } catch (HibernateException  e) {
        	if(tx != null)
        		tx.rollback();
            e.printStackTrace();
            System.out.println("Error: " + e);
        } finally {            
            session.close();
        }

   }
	
}
