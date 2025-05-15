package com.cy_siao.dao;

import com.cy_siao.model.RestrictionType;
import com.cy_siao.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class RestrictionTypeDao {
    private final DatabaseUtil databaseUtil;

    public RestrictionTypeDao(){

        this.databaseUtil= new DatabaseUtil();
    }

    public void create(RestrictionType restrictionType){
        String sql= "INSERT INTO RestrictionType (label, minAge, maxAge, genderRestriction) VALUES (?, ?, ?, ?)";
        try (Connection connect = databaseUtil.getConnection();
        PreparedStatement pst= connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pst.setString(1, restrictionType.getDescription());
        pst.setInt(2, restrictionType.getMinAge());
        pst.setInt(3, restrictionType.getMaxAge());
        pst.setString(4, restrictionType.getGenderRestriction().name());
        pst.executeUpdate();
        }

        catch(SQLException e){
            System.err.println("An error occurred when trying to create restrictionType: " + e.getMessage());
        }
    }

    public RestrictionType findById(int id){
        String sql ="SELECT * FROM RestrictionType WHERE Id=?";
        try (Connection connect= databaseUtil.getConnection();
        PreparedStatement pst=connect.prepareStatement(sql)){
        pst.setInt(1, id);
            try (ResultSet rset=pst.executeQuery()){
                if (rset.next()){
                    return extractRestrictionTypeFromResultSet(rset);
                }
            }
            catch( SQLException e){
                System.err.println("An error occurred when trying to find restrictionType: "+e.getMessage());
            }
        }
        catch( SQLException e){
            System.err.println("An error occurred when trying to find restrictionType: "+e.getMessage());
        }
        return null;
    }

public List<RestrictionType> findAll(){
    List<RestrictionType> restrictions=new ArrayList<>();
    String sql= "SELECT * FROM RestrictionType";
    try (Connection connect=databaseUtil.getConnection();
    Statement st=connect.createStatement();
    ResultSet rset= st.executeQuery(sql)){
        while (rset.next()){
            restrictions.add(extractRestrictionTypeFromResultSet(rset));
        }
   
    }
    catch (SQLException e){
        System.err.println("An error occurred when trying to findAll restrictionType: "+ e.getMessage());
    }
    return restrictions;
    
}

public void update(RestrictionType restrictionType){
    String sql ="UPDATE RestrictionType SET label=?, minAge=?, maxAge=?, genderRestriction=? WHERE Id=?";
    try (Connection connect = databaseUtil.getConnection();
        PreparedStatement pst= connect.prepareStatement(sql)){
        pst.setString(1, restrictionType.getDescription());
        pst.setInt(2, restrictionType.getMinAge());
        pst.setInt(3, restrictionType.getMaxAge());
        pst.setString(4, restrictionType.getGenderRestriction().name());
        pst.setInt(5, restrictionType.getId());
        pst.executeUpdate();
        }
    catch(SQLException e){
        System.err.println("An error occurred when trying to update restrictionType: "+e.getMessage());
    }
}

public void delete(int id){
    String sql="DELETE FROM RestrictionType WHERE Id=?";
    try (Connection connect= databaseUtil.getConnection();
    PreparedStatement pst=connect.prepareStatement(sql)){
        pst.setInt(1, id);
        pst.executeUpdate();
    }
    catch(SQLException e){
        System.err.println("An error occurred when trying to delete restrictionType: "+e.getMessage());
    }
}


private RestrictionType extractRestrictionTypeFromResultSet(ResultSet rset){
    try {RestrictionType restrictionType=new RestrictionType();
    restrictionType.setId(rset.getInt("Id"));
    restrictionType.setDescription(rset.getString("label"));
    restrictionType.setMinAge(rset.getInt("minAge"));
    restrictionType.setMaxAge(rset.getInt("maxAge"));
    restrictionType.setGenderRestriction(Gender.valueOf(rset.getString("genderRestriction")));
    return restrictionType;
}
catch(SQLException e){
    System.err.println("An error occurred when trying to extract restrictionType: "+e.getMessage());
}
return null;
        
}
}


