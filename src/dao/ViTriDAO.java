package dao;

import entities.*;
import java.util.*;
import org.hibernate.*;
import java.sql.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;



public class ViTriDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
	public List<Vitri> Load() {
		try {
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			 
 	            return sessionFactory.getCurrentSession().createSQLQuery(" exec VITRI_Load ").addEntity(Vitri.class).list();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
	
	/**
     * Kiem tra ma vi tri da duoc su dung chua
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
}
