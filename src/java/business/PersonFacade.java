package business;

import entity.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;


@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    public List<Person> findByEntity(Person person) {
        System.out.println("PersonFacade findByEntity start");
        
        Query query = em.createNamedQuery("Person.findByEntity");
        query.setParameter("firstName", person.getFirstName())
             .setParameter("lastName", person.getLastName())
             .setParameter("country", person.getCountry())
             .setParameter("city", person.getCity())
             .setParameter("postalCode", person.getPostalCode())
             .setParameter("address", person.getAddress())
             .setParameter("address2", "%" + person.getAddress() + "%")
             .setParameter("dateOfBirth", person.getDateOfBirth(), TemporalType.DATE)
             .setParameter("email", person.getEmail())
             .setParameter("email2", "%" + person.getEmail() + "%")
             .setParameter("isTeacher", person.getIsTeacher())
             .setParameter("mobile", person.getMobile());
        
        System.out.println("PersonFacade findByEntity en | query value : " + query);
        
        return query.getResultList();
    }
    
    public List<Person> findByIsTeacher(boolean isTeacher) {
        
        Query query = em.createNamedQuery("Person.findByIsTeacher");
        query.setParameter("isTeacher", isTeacher);
        
        return query.getResultList();
    }
    
}
