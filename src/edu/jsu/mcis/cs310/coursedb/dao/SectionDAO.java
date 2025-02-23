package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
//import com.github.cliftonlabs.json_simple.JsonArray;
//import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.*;

public class SectionDAO {
    
    //private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        JsonArray jsonArray = new JsonArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                rs = ps.executeQuery();
                
                while(rs.next()){
                   JsonObject jsonObject = new JsonObject();
                   
                   
                  
                   //jsonObject.put("crn", rs.getInt("crn"));
                   jsonObject.put("termid", rs.getString("termid"));
                   jsonObject.put("num", rs.getInt("num"));
                   jsonObject.put("subjectid", rs.getInt("subjectid"));
                   jsonArray.add(jsonObject);
                   
                   
                   
                   
                }
                
            //INSERT CODE   
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        String sizeAsString = Integer.toString(jsonArray.size());
        return sizeAsString;
        
    }
    
}