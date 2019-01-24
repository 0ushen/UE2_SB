package business;

import entity.Ue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class UeFacade extends AbstractFacade<Ue> {

    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UeFacade() {
        super(Ue.class);
    }
    
    public List<Ue> findByEntity(Ue ue) {
        
        java.lang.Integer sectionId = null;
        if(ue.getSection() != null)
            sectionId = ue.getSection().getSectionId();
        
        Query query = em.createNamedQuery("UE.findByEntity");
        query.setParameter("name", ue.getName())
             .setParameter("description", ue.getDescription())
             .setParameter("sectionId", sectionId)
             .setParameter("code", ue.getCode())
             .setParameter("numOfPeriods", ue.getNumOfPeriods())
             .setParameter("isDecisive", ue.getIsDecisive());
        
        return query.getResultList();    
    }
    
}
