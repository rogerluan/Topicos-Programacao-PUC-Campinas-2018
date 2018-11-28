package bd.dbos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Donation {
	private Integer id;
	private String title;
	private String description;
	private String contact;
	private String city;
	private String state;
	private String address;
	
	public Donation(String title, String description, String contact, String city, String state, String address) throws Exception {
		super();
		setTitle(title);
		setDescription(description);
		setContact(contact);
		setCity(city);
		setState(state);
		setAddress(address);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
 
    public void setTitle(String title) throws Exception {
    	String result = title.trim();
    	if (result == null || result.equals("") || result.length() > 128) {
    		throw new Exception ("The title is mandatory and must be shorter than 128 characters.");
		}
        this.title = result;
    }
    
    public void setDescription(String description) throws Exception {
    	String result = description.trim();
    	if (result != null && result.length() > 1024) {
    		throw new Exception ("The description must be shorter than 1024 characters.");
		}
        this.description = result;
    }
    
    public void setContact(String contact) throws Exception {
    	String result = contact.trim();
    	// TODO: Validate if it's a valid email or phone number
    	if (result != null && result.length() > 355) {
    		throw new Exception ("The contact must be shorter than 355 characters.");
		}
        this.contact = result;
    }
    
    public void setCity(String city) throws Exception {
    	String result = city.trim();
    	if (result == null || result.equals("") || result.length() > 50) {
    		throw new Exception ("The city is mandatory and must be shorter than 50 characters.");
		}
        this.city = result;
    }
    
    public void setState(String state) throws Exception {
    	String result = state.trim();
    	if (result == null || result.length() != 2) {
    		throw new Exception ("The state is mandatory and must have 2 characters.");
		}
        this.state = result.toUpperCase();
    }
    
    public void setAddress(String address) throws Exception {
    	String result = address.trim();
    	if (result != null && result.length() > 128) {
    		throw new Exception ("The address must be shorter than 128 characters.");
		}
        this.address = result;
    }

    public Integer getId() {
    	return id;
    }

    public String getTitle() {
		return title;
	}
    
	public String getDescription() {
		return description;
	}
	
	public String getContact() {
		return contact;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public String getAddress() {
		return address;
	}
	
	@Override public String toString() {
		return "Donation {\n"
				+ "\tID: " + id + "\n"
				+ "\ttitle: " + title + "\n"
				+ "\tdescription: " + description + "\n"
				+ "\tcontact: " + contact + "\n"
				+ "\tcity: " + city + "\n"
				+ "\tstate: " + state + "\n"
				+ "\taddress: " + address + "\n"
				+ "}\n";
	}
	
	@Override public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}
	
	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Donation other = (Donation)obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (title == null) {
			if (other.title != null) return false;
		} else if (!title.equals(other.title)) {
			return false;
		}

		if (description == null) {
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) {
			return false;
		}
		
		if (contact == null) {
			if (other.contact != null) return false;
		} else if (!contact.equals(other.contact)) {
			return false;
		}
		
		if (city == null) {
			if (other.city != null) return false;
		} else if (!city.equals(other.city)) {
			return false;
		}
		
		if (state == null) {
			if (other.state != null) return false;
		} else if (!state.equals(other.state)) {
			return false;
		}

		if (address == null) {
			if (other.address != null) return false;
		} else if (!address.equals(other.address)) {
			return false;
		}
		
		return true;
	}
}