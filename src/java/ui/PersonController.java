package ui;

import business.PersonFacade;
import entity.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ui.util.JsfUtil;


@Named
@ViewScoped
public class PersonController implements Serializable {
    
    @Inject
    private PersonFacade ejbFacade;
    
    private Person person = new Person();
    private Person personToEdit = new Person();
    private Person lastSearch;
    private List<Person> personList = new ArrayList<>();
    private HtmlDataTable dataTablePerson;
    private String lastCall;
    
    
    public void listAll() {
        
        personList = ejbFacade.findAll();
        lastCall = "listAll";
        
    }
    
    public void search() {
        
        System.out.println("personController search() start | person value : " + person);
        personList = ejbFacade.findByEntity(person);
        lastCall = "search";
        lastSearch = new Person(person);
        System.out.println("personController search() end | person List : " + personList);
        
    }
    
    public void create() {
        
        ejbFacade.create(person);
        person = new Person();
        refresh();
        
    }
    
    public void update() {
        
        ejbFacade.edit(personToEdit);
        refresh();
        
    }
    
    public void delete() {
        
        ejbFacade.remove(personToEdit);
        refresh();
        
    }
    
    public void renderDetailsBox() {
        
        personToEdit = (Person) dataTablePerson.getRowData();
        //System.out.println(personToEdit);
        
    }
    
    private void refresh() {
        
        if("search".equals(lastCall))
            personList = ejbFacade.findByEntity(lastSearch);
        else if("listAll".equals(lastCall))
            listAll();
        
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public List<Person> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public List<Person> getItemsAvailableSelectOneTeacher() {
        return ejbFacade.findByIsTeacher(true);
    }
    
    /* I had to get the isDecisive Boolean value with a changeListener because
     * when i try to get it directly into the isDecisive property of ue, it
     * automatically convert null to false somehow .*/
    public void changeListenerIsTeacher(ValueChangeEvent event) {
        
        if("true".equals(event.getNewValue()))
            person.setIsTeacher(Boolean.TRUE);
        else if("false".equals(event.getNewValue()))
            person.setIsTeacher(Boolean.FALSE);
        else if("null".equals(event.getNewValue()))
            person.setIsTeacher(null);
    }
    
    @FacesConverter(forClass = Person.class)
    public static class PersonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonController controller = (PersonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Person) {
                Person o = (Person) object;
                return getStringKey(o.getPersonId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Person.class.getName());
            }
        }

    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public PersonFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(PersonFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPersonToEdit() {
        return personToEdit;
    }

    public void setPersonToEdit(Person personToEdit) {
        this.personToEdit = personToEdit;
    }
    
    public List<Person> getPersonList() {
        return personList;
    }
    
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }
    
    public HtmlDataTable getDataTablePerson() {
        return dataTablePerson;
    }

    public void setDataTablePerson(HtmlDataTable dataTablePerson) {
        this.dataTablePerson = dataTablePerson;
    }
    

}