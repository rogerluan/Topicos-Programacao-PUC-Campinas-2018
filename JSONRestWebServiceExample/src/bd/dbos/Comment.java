package bd.dbos;

import java.sql.Timestamp;
import java.text.DateFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

@XmlRootElement
public class Comment {
	private Integer id;
	private Integer donationID;
	private Timestamp creationDate;
	private String comment;
	
	public Comment(Integer id, Integer donationID, Timestamp creationDate, String comment) throws Exception {
		super();
		setId(id);
		setDonationId(donationID);
		setCreationDate(creationDate);
		setComment(comment);
	}
	
	public Comment(Integer donationID, String comment) throws Exception {
		super();
		setDonationId(donationID);
		setComment(comment);
	}

	public void setId(Integer id) { 
		this.id = id;
	}
	
	public void setDonationId(Integer donationID) {
		this.donationID = donationID;
	}
	
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public void setComment(String comment) throws Exception {
		String result = comment.trim();
		if (result == null || result.equals("") || result.length() > 1024) {
    		throw new Exception("The comment is mandatory and must be shorter than 1024 characters.");
		}
		this.comment = result;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getDonationId() {
		return donationID;
	}
	
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public String getComment() {
		return comment;
	}
	
	@Override public String toString() {
		return "Comment {\n"
				+ "\tID: " + id + "\n"
				+ "\tdonationID: " + donationID + "\n"
				+ "\tcreationDate: " + creationDate.toString() + "\n"
				+ "\tcomment: " + comment + "\n"
				+ "}\n";
	}
	
	@Override public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((donationID == null) ? 0 : donationID.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		return result;
	}
	
	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (donationID == null) {
			if (other.donationID != null) return false;
		} else if (!donationID.equals(other.donationID)) {
			return false;
		}
		
		if (creationDate == null) {
			if (other.creationDate != null) return false;
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		
		if (comment == null) {
			if (other.comment != null) return false;
		} else if (!comment.equals(other.comment)) {
			return false;
		}
		return true;
	}
}