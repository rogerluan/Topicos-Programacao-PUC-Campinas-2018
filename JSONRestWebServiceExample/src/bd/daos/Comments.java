package bd.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bd.BDPostgreSQL;
import bd.core.MeuResultSet;
import bd.dbos.Comment;

public class Comments {
	
    public static Comment insert(Comment comment) throws Exception {
        if (comment == null) throw new Exception ("Comment can't be null.");
        try {
            String sql = "INSERT INTO COMMENTS " +
            			 "(DONATION_ID, COMMENT) " +
            			 "VALUES " +
            			 "(?, ?)";

            BDPostgreSQL.COMANDO.prepareStatement(sql);
            BDPostgreSQL.COMANDO.setInt(1, comment.getDonationId());
            BDPostgreSQL.COMANDO.setString(2, comment.getComment());
            BDPostgreSQL.COMANDO.executeUpdate();
            BDPostgreSQL.COMANDO.commit();
            List<Comment> comments = getCommentsForDonation(comment.getDonationId()); 
            return comments.get(comments.size() - 1);
        } catch (SQLException error) {
        	System.out.println (error.getMessage());
            throw new Exception ("Error when inserting new comment.");
        }
    }
    
	public static Comment getComment(Integer id) throws Exception {
		Comment comment = null;
		try {
			String sql = "SELECT * FROM COMMENTS WHERE ID = ?";
			BDPostgreSQL.COMANDO.prepareStatement(sql);
			BDPostgreSQL.COMANDO.setInt(1, id);
			MeuResultSet result = (MeuResultSet) BDPostgreSQL.COMANDO.executeQuery();
			if (!result.first()) {
				System.out.println("Comment not found");
				throw new Exception("Comment not found.");
			}
			comment = new Comment(Integer.valueOf(result.getInt("id")), Integer.valueOf(result.getInt("donation_id")), result.getTimestamp("created_on"), result.getString("comment"));
		} catch (SQLException error) {
			System.out.println(error.getMessage());
			throw new Exception("Error when fetching comment.");
		}
		return comment;
	}
    
    public static List<Comment> getCommentsForDonation(Integer donationID) throws Exception {
    	List<Comment> comments = null;
        try {
        	String sql = "SELECT * FROM COMMENTS WHERE DONATION_ID = ?";
            BDPostgreSQL.COMANDO.prepareStatement(sql);
            BDPostgreSQL.COMANDO.setInt(1, donationID);
            MeuResultSet result = (MeuResultSet)BDPostgreSQL.COMANDO.executeQuery();
            comments = new ArrayList<Comment>();
    		while (result.next()) {
    			Comment comment = new Comment(
    				Integer.valueOf(result.getInt("id")),
    				Integer.valueOf(result.getInt("donation_id")),
    				result.getTimestamp("created_on"),
    				result.getString("comment")
    			);
    			comments.add(comment);
    		}
        } catch (SQLException error) {
        	System.out.println (error.getMessage());
        	throw new Exception ("Error when fetching comments for donation ID = " + donationID + ".");
        }
        return comments;
    }
}