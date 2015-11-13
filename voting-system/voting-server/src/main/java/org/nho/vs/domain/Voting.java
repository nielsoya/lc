package org.nho.vs.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name=TableNames.VOTING, 
       uniqueConstraints={@UniqueConstraint(columnNames={"USER_NAME","DATE"})})
public class Voting implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String ID = "ID";

    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    @Column(name=ID)
    private long id;
    
    @Column(name="DATE")
    private LocalDate date;
    
    @OneToOne(fetch=FetchType.EAGER, targetEntity=Restaurant.class)
    @JoinColumn(name="RESTARAUNT_ID", referencedColumnName=Restaurant.ID)
    private Restaurant restaraunt;
    
    @OneToOne(fetch=FetchType.EAGER, targetEntity=User.class)
    @JoinColumn(name="USER_NAME", referencedColumnName=User.NAME)
    private User user;
    
    protected Voting(){
    }

	public Voting(final LocalDate date, final Restaurant restaraunt, final User user) {
		this.date = date;
		this.restaraunt = restaraunt;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public Restaurant getRestaraunt() {
		return restaraunt;
	}

	public void setRestaraunt(final Restaurant restaraunt) {
		this.restaraunt = restaraunt;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Voting other = (Voting) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(144);
		builder.append("Voting [date=");
		builder.append(date);
		builder.append(", id=");
		builder.append(id);		
		builder.append(", restaraunt=");
		builder.append(restaraunt);
		builder.append(", user=");
		builder.append(user);
		builder.append(']');
		return builder.toString();
	}
	
}