package northwind.entities;

import java.util.Date;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "User")
public class User {
	@GraphId
	Long id;
	
	@Property(name = "Email")
	private String email;
	private String password;
	private String name;
	private Date birthdate;
	private String birthplace;
	private String profileImage;

	public User() {
	}

	public User(String email, String password, String name, Date birthdate, String birthplace, String profileImage) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthdate = birthdate;
		this.birthplace = birthplace;
		this.profileImage = profileImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		User person = (User) o;

		if (email != null ? !email.equals(person.email) : person.email != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return email != null ? email.hashCode() : 0;
	}
}
