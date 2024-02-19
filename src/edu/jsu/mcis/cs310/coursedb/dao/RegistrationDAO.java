package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    JsonArray jsonArray = new JsonArray();
    
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(CREATE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int update = ps.executeUpdate();
                if(update > 0){
                    result = true;
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                 String DELETE = "DELETE FROM registration WHERE studentid = ? AND termid=? AND crn=?";
                 ps = conn.prepareStatement(DELETE);
                 ps.setInt(1, studentid);
                 ps.setInt(2, termid);
                 ps.setInt(3, crn);
            
                int update = ps.executeUpdate();
                if (update > 0) {
                    result = true;
            }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String DELETE = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(DELETE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int update = ps.executeUpdate();
                if (update > 0)
                {
                    result = true;
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        JsonArray jsonArray = new JsonArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                //The ORDER BY crn = ?  isnt able to compare anything
                String LIST = "SELECT studentid, termid, crn FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(LIST);
                ps.setInt(2, studentid);
                ps.setInt(1, termid);
                
                rs = ps.executeQuery();
                
                
                while (rs.next()){
                    JsonObject jsonObject = new JsonObject();
                    //jsonObject.put("studentid", rs.getInt("studentid"));
                    jsonObject.put("studentid", rs.getInt("studentid"));
                    jsonObject.put("termid", rs.getInt("termid"));
                    jsonObject.put("crn", rs.getInt("crn"));
                    jsonArray.add(jsonObject);
                }
                
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        result = jsonArray.toString();
        return result;
        //return result;
        
    }
    
}
