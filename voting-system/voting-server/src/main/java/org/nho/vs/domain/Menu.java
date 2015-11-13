package org.nho.vs.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name=TableNames.MENU)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String ID = "ID";

    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    @Column(name="ID")
    private long id;
    
    @Column(name="DATE")
    private LocalDate date;
    
    @OneToMany(fetch = FetchType.EAGER, 
    		   cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name=Dish.MENU_ID, referencedColumnName=Menu.ID)
    private List<Dish> dishes = new ArrayList<>();
    
    @OneToOne(fetch=FetchType.EAGER, targetEntity=Restaurant.class)
    @JoinColumn(name="RESTARAUNT_ID", referencedColumnName=Restaurant.ID)
    private Restaurant restaraunt;
     
    protected Menu(){
    }

	public Menu(final LocalDate date, final List<Dish> dishes) {
		this.date = date;
		this.dishes = dishes;
	}

	public long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public List<Dish> getDishes() {
		return dishes;
	}
	
	public void addDish(final Dish dish){
		if(null != dish){
			dishes.add(dish);
		}
	}

	public Restaurant getRestaraunt() {
		return restaraunt;
	}

	public void setRestaraunt(final Restaurant restaraunt) {
		this.restaraunt = restaraunt;
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
		Menu other = (Menu) obj;
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
		builder.append("Menu [id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", diches=");
		builder.append(dishes);
		builder.append(']');
		return builder.toString();
	}

}