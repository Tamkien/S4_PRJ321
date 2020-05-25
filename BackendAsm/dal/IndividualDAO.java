/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Individual;
import model.Status;

/**
 *
 * @author Tamkien
 */
public class IndividualDAO extends BaseDAO {

    public void insertPerson(Individual i) {
        try {
            String sql = "INSERT INTO [Individual]\n"
                    + "           ([PersonID],\n"
                    + "           [PersonName],\n"
                    + "           [FamilyID],\n"
                    + "           [DoB],\n"
                    + "           [Gender])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, i.getPersonID());
            statement.setString(2, i.getPersonName());
            statement.setString(3, i.getFamilyID());
            statement.setDate(4, (java.sql.Date) i.getDob());
            statement.setBoolean(5, i.isMale());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Individual> searchIndividual(Individual input) {
        ArrayList<Individual> al = new ArrayList<>();
        try {
            String sql = "select i.PersonID, PersonName, DoB, i.FamilyID, RegionName, HStatus from \n"
                    + "Individual i inner join Family f on i.FamilyID = f.FamilyID\n"
                    + "inner join HealthStatus h on i.PersonID = h.PersonID\n"
                    + "inner join Region r on f.RegionID = r.RID\n"
                    + "inner join StatusTypes s on h.StatusID = s.StatusTypeID\n"
                    + "  where i.PersonID like '%' + ? + '%'"
                    + "  and PersonName like '%' + ? + '%'"
                    + "  and i.FamilyID like '%' + ? + '%'";
            String id = input.getPersonID();
            String name = input.getPersonName();
            String address = input.getRegionID();
            String fid = input.getFamilyID();
            String sid = input.getStatusID();
            if (!address.equals("0")) {
                sql += " and RegionID = ?";
            }
            if (!sid.equals("0")) {
                sql += " and StatusID = ?";
            }
            sql += " order by RegionName";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id.isEmpty() ? "" : id);
            statement.setString(2, name.isEmpty() ? "" : name);
            statement.setString(3, fid.isEmpty() ? "" : fid);
            if (!address.equals("0")) {
                statement.setString(4, address);
            } else if (!sid.equals("0")) {
                statement.setString(4, sid);
            }
            if (!address.equals("0") && !sid.equals("0")) {
                statement.setString(5, sid);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Individual d = new Individual();
                d.setPersonID(rs.getString("PersonID"));
                d.setPersonName(rs.getString("PersonName"));
                d.setFamilyID(rs.getString("FamilyID"));
                d.setDob(rs.getDate("DoB"));
                d.setRegion(rs.getString("RegionName"));
                d.setStatus(rs.getString("HStatus"));
                al.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }

    public void insertHealthStatus(Individual i) {
        try {
            String sql = "INSERT INTO [HealthStatus]\n"
                    + "           ([PersonID],\n"
                    + "           [StatusID],\n"
                    + "           [Note])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, i.getPersonID());
            statement.setString(2, i.getStatusID());
            statement.setString(3, i.getHealthNote());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Status> getStatus() {
        ArrayList<Status> regions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM StatusTypes";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Status r = new Status();
                r.setStatusID(Integer.parseInt(rs.getString("StatusTypeID")));
                r.setStatusContent(rs.getString("HStatus"));
                regions.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regions;
    }

    public void editPerson(Individual d) {
        try {
            String sql = "UPDATE [Individual]\n"
                    + "   SET     [PersonName] = ?,\n"
                    + "           [DoB] = ?,\n"
                    + "           [Gender] = ?\n"
                    + "     where [PersonID] = ? \n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d.getPersonName());
            statement.setDate(2, d.getDob());
            statement.setBoolean(3, d.isMale());
            statement.setString(4, d.getPersonID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editHealthStatus(Individual d) {
        try {
            String sql = "UPDATE [HealthStatus]\n"
                    + "   SET     [StatusID] = ?,\n"
                    + "           [Note] = ?\n"
                    + "     where [PersonID] = ? \n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d.getStatusID());
            statement.setString(2, d.getHealthNote());
            statement.setString(3, d.getPersonID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteInfo(String id) {
        try {
            String sql = "DELETE Individual WHERE PersonID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
            sql = "DELETE HealthStatus WHERE PersonID=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
            sql = "DELETE TravelHistory WHERE PersonID=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Individual searchIndividualByID(String id) {
        Individual f = new Individual();
        try {
            String sql = "select i.PersonID, PersonName, DoB, Gender, RegionName, Note, HStatus, TravelContent from Individual i\n"
                    + "inner join Family f on i.FamilyID = f.FamilyID\n"
                    + "inner join Region r on f.RegionID = r.RID\n"
                    + "inner join HealthStatus hs on i.PersonID = hs.PersonID\n"
                    + "inner join StatusTypes st on hs.StatusID = st.StatusTypeID\n"
                    + "left join TravelHistory th on i.PersonID = th.PersonID\n"
                    + "where i.PersonID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                f.setPersonID(id);
                f.setRegion(rs.getString("RegionName"));
                f.setDob(rs.getDate("DoB"));
                f.setPersonName(rs.getString("PersonName"));
                f.setMale(rs.getBoolean("Gender"));
                f.setHealthNote(rs.getString("Note"));
                f.setStatus(rs.getString("HStatus"));
                String th = rs.getString("TravelContent");
                if (th == null) {
                    f.setTravelHistory("Đang cập nhật");
                } else {
                    f.setTravelHistory(th);
                }
                return f;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editTravelHistory(Individual d) {
        if (historyExist(d)) {
            try {
                String sql = "UPDATE [TravelHistory]\n"
                        + "   SET     [TravelContent] = ?\n"
                        + "     where [PersonID] = ? \n";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, d.getTravelHistory());
                statement.setString(2, d.getPersonID());
                statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String sql = "INSERT INTO [TravelHistory]\n"
                        + "           ([PersonID],\n"
                        + "           [TravelContent])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, d.getPersonID());
                statement.setString(2, d.getTravelHistory());
                statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(IndividualDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean historyExist(Individual i) {
        try {
            String sql = "select * from TravelHistory where PersonID = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, i.getPersonID());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
