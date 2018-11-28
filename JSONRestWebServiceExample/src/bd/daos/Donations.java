package bd.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bd.BDPostgreSQL;
import bd.core.MeuResultSet;
import bd.dbos.Donation;
import bd.dbos.Comment;

public class Donations {

	public static void insert(Donation donation) throws Exception {
		if (donation == null)
			throw new Exception("Donation can't be null.");
        try {
			String sql = "INSERT INTO DONATIONS " + "(TITLE, DESCRIPTION, CONTACT, CITY, STATE, ADDRESS) " + "VALUES "
					+ "(?, ?, ?, ?, ?, ?)";
	
			BDPostgreSQL.COMANDO.prepareStatement(sql);
			BDPostgreSQL.COMANDO.setString(1, donation.getTitle());
			BDPostgreSQL.COMANDO.setString(2, donation.getDescription());
			BDPostgreSQL.COMANDO.setString(3, donation.getContact());
			BDPostgreSQL.COMANDO.setString(4, donation.getCity());
			BDPostgreSQL.COMANDO.setString(5, donation.getState());
			BDPostgreSQL.COMANDO.setString(6, donation.getAddress());
			BDPostgreSQL.COMANDO.executeUpdate();
			BDPostgreSQL.COMANDO.commit();
        } catch (SQLException error) {
        	System.out.println (error.getMessage());
            throw new Exception ("Error when inserting new donation.");
        }
	}

	public static Donation getDonation(Integer id) throws Exception {
		Donation donation = null;
		try {
			String sql = "SELECT * FROM DONATIONS WHERE ID = ?";

			BDPostgreSQL.COMANDO.prepareStatement(sql);
			BDPostgreSQL.COMANDO.setInt(1, id);
			MeuResultSet result = (MeuResultSet) BDPostgreSQL.COMANDO.executeQuery();

			if (!result.first()) {
				System.out.println("Donation not found");
				throw new Exception("Donation not found.");
			}

			donation = new Donation(result.getString("title"), result.getString("description"),
					result.getString("contact"), result.getString("city"), result.getString("state"),
					result.getString("address"));
			donation.setId(result.getInt("id"));
		} catch (SQLException error) {
			System.out.println(error.getMessage());
			throw new Exception("Error when fetching donation.");
		}
		return donation;
	}

	public static List<Donation> getDonations() throws Exception {
        List<Donation> donations = null;
        try {
            String sql = "SELECT * FROM DONATIONS";
            BDPostgreSQL.COMANDO.prepareStatement(sql);
            MeuResultSet result = (MeuResultSet)BDPostgreSQL.COMANDO.executeQuery();
            donations = new ArrayList<Donation>();
    		while (result.next()) {
    			Donation donation = new Donation(
    				result.getString("title"),
    	            result.getString("description"),
    	            result.getString("contact"),
    	            result.getString("city"),
    	            result.getString("state"),
    	            result.getString("address")
    			);
    			donation.setId(result.getInt("id"));
    			donations.add(donation);
    		}
        } catch (SQLException erro) {
            throw new Exception ("Error when fetching donations.");
        }
        return donations;
    }
}