package dao;

import entities.*;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import java.sql.*;




public class NhomThucDonDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
	/**
     * Load list danh sach Trinh Do
     *
     * @return List<Vitri>
     */
	public List<Nhomthucdon> Load() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Nhomthucdon> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery("from Nhomthucdon").list();
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
     * Kiem tra ma trinh do da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String key) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NHOMTHUCDON_CheckInsert(?,?)}");
            cstmt.setString("MaNhom", key);
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
    
    public String Insert(Nhomthucdon entity) {
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
     * Kiem tra ma trinh do duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String key) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NHOMTHUCDON_CheckEdit(?,?)}");
            cstmt.setString("MaNhom", key);
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
     * cap nhat sua trinh do
     *
     * 
     */
    
	public void UpdateData(String key, String tenNhom, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Nhomthucdon entity = (Nhomthucdon) session.get(Nhomthucdon.class, key);
			entity.setTenNhom(tenNhom);
			entity.setDienGiai(dienGiai);
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
     * Kiem tra ma trinh do co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String key) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.NHOMTHUCDON_CheckDelete(?,?)}");
            cstmt.setString("MaNhom", key);
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
     * Xoa trinh do
     *
     */
    public void Delete(String key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Nhomthucdon entity = (Nhomthucdon) session.get(Nhomthucdon.class, key);
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
