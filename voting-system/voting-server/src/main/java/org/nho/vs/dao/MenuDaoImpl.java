package org.nho.vs.dao;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.nho.vs.domain.Menu;
import org.nho.vs.repository.MenuRepository;

/** JPA-based Menu DAO implementation  */
@Service
@Transactional
@Repository("menuDao")
public class MenuDaoImpl extends BaseDao<Menu, Long, MenuRepository> implements MenuDao {

	@Override
	public Menu findByRestarauntAndDate(final long rstId, final LocalDate date){
		return getRepo().findByRestarauntAndDate(rstId, date);
	}
	
    @Override
    protected Class<MenuRepository> getRepositoryClass() {      
        return MenuRepository.class;
    }

}
