package org.nho.vs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name=TableNames.RESTAURANT)
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String ID = "ID";

    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    @Column(name=ID)
    private long id;
    
    @Column(name="NAME")
    private String name;
    
    @OneToMany(fetch=FetchType.EAGER, 
    		   cascade={CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="RESTAURANT_ID", referencedColumnName="ID")
    private List<Menu> menus = new ArrayList<>();
    
    protected Restaurant(){
    }

	public Restaurant(final String name, final List<Menu> menus) {
		this.name = name;
		if(null != menus){
		    this.menus = menus;
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
        this.name = name;
    }

	public List<Menu> getMenus() {
		return menus;
	}				

    public void setMenus(final List<Menu> menus) {
        if(null != menus){
            this.menus = menus;
        }
    }

    public void addMenu(final Menu menu) {
        menus.add(menu);        
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Restaurant other = (Restaurant) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(177);
		builder.append("Restaurant [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		if( !menus.isEmpty() ){
		    builder.append(", menus=");
		    builder.append(menus);
		}
		builder.append(']');
		return builder.toString();
	}    

}