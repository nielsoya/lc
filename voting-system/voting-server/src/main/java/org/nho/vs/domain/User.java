package org.nho.vs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * User domain entity
 */
@Entity
@Table(name=TableNames.USER)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	public static final String ID = null;

	public static final String NAME = "NAME";

    @Id
    @Column(name=NAME)
    private String name;

    @Column(name="TYPE")
    @Enumerated(EnumType.STRING)
    private UserType type;

    /** Empty constructor required by JPA */
    protected User() {
    }

    public User(final String name, final UserType type) {
        this.name = name;
        this.type = type;
    }

	public String getName() {
		return name;
	}

	public UserType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(55);
		builder.append("User [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

 }