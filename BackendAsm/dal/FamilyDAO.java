/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Family;

/**
 *
 * @author Tamkien
 */
public class FamilyDAO extends BaseDAO {

    public void insertFamily(Family f) {
        try {
            String sql = "INSERT INTO [Family]\n"
                    + "           ([FamilyID]\n"
                    + "           ,[RegionID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, f.getFamilyID());
            statement.setString(2, f.getRegionID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertFoodleft(Family f) {
        try {
            String sql = "INSERT INTO [FoodStorage]\n"
                    + "           ([FamilyID]\n"
                    + "           ,[DaysLeft])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, f.getFamilyID());
            statement.setFloat(2, f.getFoodLeft());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Family searchFamily(String id) {
        Family f = new Family();
        try {
            String sql = "SELECT * from Family f inner join Region r on f.RegionID = r.RID inner join FoodStorage s on f.FamilyID = s.FamilyID where f.FamilyID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                f.setFamilyID(id);
                f.setRegion(rs.getString("RegionName"));
                f.setFoodLeft(rs.getFloat("DaysLeft"));
                return f;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editFamily(Family d) {
        try {
            String sql = "UPDATE [Family]\n"
                    + "   SET     [RegionID] = ?\n"
                    + "     where [FamilyID] = ? \n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d.getRegionID());
            statement.setString(2, d.getFamilyID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sql = "UPDATE [FoodStorage]\n"
                    + "   SET     [DaysLeft] = ?\n"
                    + "     where [FamilyID] = ? \n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, d.getFoodLeft());
            statement.setString(2, d.getFamilyID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
