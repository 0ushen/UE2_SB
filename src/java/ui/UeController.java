package ui;

import business.UeFacade;
import entity.Capacity;
import entity.Ue;
import ui.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;

@Named("ueController")
@ViewScoped
public class UeController implements Serializable {

    @EJB
    private business.UeFacade ejbFacade;
    
    private Ue ue = new Ue();
    private Ue ueToEdit = new Ue();
    private Ue lastSearch;
    private List<Ue> ueList = new ArrayList<>();
    private HtmlDataTable dataTableUe;
    private String lastCall;
    
    public UeController() {
    }
    
    public void create() {
        
        ejbFacade.create(ue);
        ue = new Ue();
        refresh();
        
    }
    
    public void listAll() {
        
        ueList = ejbFacade.findAll();
        lastCall = "listAll";
        
    }
    
    public void search() {
        
        ueList = ejbFacade.findByEntity(ue);
        lastCall = "search";
        lastSearch = new Ue(ue);
        
    }
    
    public void renderDetailsBox() {
        
        ueToEdit = (Ue) dataTableUe.getRowData();
        
    }
    
    public void update() {
        
        ejbFacade.edit(ueToEdit);
        refresh();
    }
    
    public void delete() {
        
        ejbFacade.remove(ueToEdit);
        refresh();
        
    }
    
    private void refresh() {
        
        if("search".equals(lastCall))
            ueList = ejbFacade.findByEntity(lastSearch);
        else if("listAll".equals(lastCall))
            listAll();
        
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Ue getUe(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    /* I had to get the isDecisive Boolean value with a changeListener because
     * when i try to get it directly into the isDecisive property of ue, it
     * automatically convert null to false somehow .*/
    public void changeListenerIsDecisive(ValueChangeEvent event) {
        
        if("true".equals(event.getNewValue()))
            ue.setIsDecisive(Boolean.TRUE);
        else if("false".equals(event.getNewValue()))
            ue.setIsDecisive(Boolean.FALSE);
        else if("null".equals(event.getNewValue()))
            ue.setIsDecisive(null);
    }

    @FacesConverter(forClass = Ue.class)
    public static class UeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UeController controller = (UeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ueController");
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
            if (object instanceof Ue) {
                Ue o = (Ue) object;
                return getStringKey(o.getUeId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Ue.class.getName());
            }
        }

    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public UeFacade getUeFacade() {
        return ejbFacade;
    }

    public void setUeFacade(UeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }

    public Ue getUeToEdit() {
        return ueToEdit;
    }

    public void setUeToEdit(Ue ueToEdit) {
        this.ueToEdit = ueToEdit;
    }

    public List<Ue> getUeList() {
        return ueList;
    }

    public void setUeList(List<Ue> ueList) {
        this.ueList = ueList;
    }
    
    public HtmlDataTable getDataTableUe() {
        return dataTableUe;
    }

    public void setDataTableUe(HtmlDataTable dataTableUe) {
        this.dataTableUe = dataTableUe;
    }

    public UeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Ue getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(Ue lastSearch) {
        this.lastSearch = lastSearch;
    }

    public String getLastCall() {
        return lastCall;
    }

    public void setLastCall(String lastCall) {
        this.lastCall = lastCall;
    }
    
    
    
    


}
