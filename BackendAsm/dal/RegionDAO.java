/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

/**
 *
 * @author Tamkien
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Region;

public class RegionDAO extends BaseDAO {
    public ArrayList<Region> getRegions() {
        ArrayList<Region> regions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Region";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Region r = new Region();
                r.setRegionID(rs.getString("RID"));
                r.setRegionName(rs.getString("RegionName"));
                regions.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regions;
    }
}
