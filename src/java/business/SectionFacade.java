package business;

import entity.Section;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SectionFacade extends AbstractFacade<Section> {

    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SectionFacade() {
        super(Section.class);
    }
    
    public List<Section> findByEntity(Section section) {
        
       
        java.lang.Integer teacherId = null;
        if(section.getTeacher() != null)
            teacherId = section.getTeacher().getPersonId();
        
        //System.out.println(teacherId);
        
        Query query = em.createNamedQuery("Section.findByEntity");
        query.setParameter("name", section.getName())
             .setParameter("name2", "%" + section.getName() + "%")
             .setParameter("description", section.getDescription())
             .setParameter("description2", "%" + section.getDescription() + "%")
             .setParameter("teacherId", teacherId);
        
        return query.getResultList();
    }
    
}
