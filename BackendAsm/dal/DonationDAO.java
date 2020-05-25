/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Donation;

/**
 *
 * @author Tamkien
 */
public class DonationDAO extends BaseDAO {

    public boolean insertDonation(Donation d) {
        try {
            String sql = "INSERT INTO [Donation]\n"
                    + "           ([Amount],\n"
                    + "           [DonationID],\n"
                    + "           [DonatorName],\n"
                    + "           [DonatorAddress])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d.getAmount());
            statement.setString(2, d.getDonationID());
            statement.setString(3, d.getDonatorName());
            statement.setString(4, d.getDonatorAddress());
            try {
                statement.executeUpdate();
            } catch (SQLServerException ex) {
                if (ex.getMessage().contains("PRIMARY")) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public ArrayList<Donation> searchDonation(Donation input) {
        ArrayList<Donation> donators = new ArrayList<>();
        try {
            String sql = "SELECT [DonationID]\n"
                    + "      ,[DonatorName]\n"
                    + "      ,[DonatorAddress]\n"
                    + "      ,[Amount]\n"
                    + "  FROM Donation\n"
                    + "  where DonationID like '%' + ? + '%'"
                    + "  and DonatorName like '%' + ? + '%'"
                    + "  and DonatorAddress like '%' + ? + '%'"
                    + "  and Amount like '%' + ? + '%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            String id = input.getDonationID();
            String name = input.getDonatorName();
            String address = input.getDonatorAddress();
            String amount = input.getAmount();
            statement.setString(1, id.isEmpty() ? "" : id);
            statement.setString(2, name.isEmpty() ? "" : name);
            statement.setString(3, address.isEmpty() ? "" : address);
            statement.setString(4, amount.isEmpty() ? "" : amount);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Donation d = new Donation();
                d.setDonationID(rs.getString("DonationID"));
                d.setDonatorName(rs.getString("DonatorName"));
                d.setAmount(rs.getString("Amount"));
                d.setDonatorAddress(rs.getString("DonatorAddress"));
                donators.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donators;
    }

    public void updateDonation(Donation d) {
        try {
            String sql = "UPDATE [Donation]\n"
                    + "   SET     [Amount] = ?,\n"
                    + "           [DonatorName] = ?,\n"
                    + "           [DonatorAddress] = ?\n"
                    + "     where [DonationID] = ? \n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, d.getAmount());
            statement.setString(2, d.getDonatorName());
            statement.setString(3, d.getDonatorAddress());
            statement.setString(4, d.getDonationID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DonationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteDonation(String id) {
        try {
            String sql = "DELETE Donation WHERE DonationID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DonationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Donation existedDonation(String input) {
        try {
            String sql = "SELECT [DonationID]\n"
                    + "      ,[DonatorName]\n"
                    + "      ,[DonatorAddress]\n"
                    + "      ,[Amount]\n"
                    + "  FROM Donation where DonationID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,input);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Donation d = new Donation();
                d.setDonationID(rs.getString("DonationID"));
                d.setDonatorName(rs.getString("DonatorName"));
                d.setAmount(rs.getString("Amount"));
                d.setDonatorAddress(rs.getString("DonatorAddress"));
                return d;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
