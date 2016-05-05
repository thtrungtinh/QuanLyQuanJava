package dao;

import entities.*;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import java.sql.*;




public class CaLamViecDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
	/**
     * Load list danh sach Trinh Do
     *
     * @return List<Vitri>
     */
	public List<Trinhdo> Load() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Trinhdo> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery("from Trinhdo").list();
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
    public String CheckInsert(String maTrinhDo) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.TRINHDO_CheckInsert(?,?)}");
            cstmt.setString("MaTrinhDo", maTrinhDo);
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
    
    public String Insert(Trinhdo entity) {
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
    public String CheckEdit(String maTrinhDo) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.TRINHDO_CheckEdit(?,?)}");
            cstmt.setString("MaTrinhDo", maTrinhDo);
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
    
	public void UpdateData(String maTrinhDo, String tenTrinhDo, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Trinhdo entity = (Trinhdo) session.get(Trinhdo.class, maTrinhDo);
			entity.setTenTrinhDo(tenTrinhDo);
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
    public String CheckDelete(String maTrinhDo) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.TRINHDO_CheckDelete(?,?)}");
            cstmt.setString("MaTrinhDo", maTrinhDo);
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
    public void Delete(String maTrinhDo) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Trinhdo entity = (Trinhdo) session.get(Trinhdo.class, maTrinhDo);
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
