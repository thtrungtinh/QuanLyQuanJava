package dao;

import entities.*;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;

public class NguoiDungDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
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
     * @return List<Vitri>
     */
	public List<Vitri> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Vitri> list = null;
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
    public String CheckInsert(String maViTri) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.VITRI_CheckInsert(?,?)}");
            cstmt.setString("MaViTri", maViTri);
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
     * Kiem tra ma duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String maViTri) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.VITRI_CheckEdit(?,?)}");
            cstmt.setString("MaViTri", maViTri);
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
    
	public void UpdateData(String maViTri, String tenViTri, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Vitri entity = (Vitri) session.get(Vitri.class, maViTri);
			entity.setTenViTri(tenViTri);
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
     * Kiem tra ma co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String maViTri) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.VITRI_CheckDelete(?,?)}");
            cstmt.setString("MaViTri", maViTri);
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
    public void Delete(String maViTri) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Vitri vtri = (Vitri) session.get(Vitri.class, maViTri);
            session.delete(vtri);
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
