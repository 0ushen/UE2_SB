package ui;

import entity.Capacity;
import ui.util.JsfUtil;
import business.CapacityFacade;
import entity.Ue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("capacityController")
@ViewScoped
public class CapacityController implements Serializable {

    @Inject
    private CapacityFacade ejbFacade;
    
    private Capacity capacity = new Capacity();
    private Capacity capacityToEdit = new Capacity();
    private Capacity lastSearch;
    private List<Capacity> capacityList = new ArrayList<>();
    private HtmlDataTable dataTableCapacity;
    private String lastCall;
    private Map editableRows = new HashMap();

    public CapacityController() {
    }
    
    public void listAll() {
        
        capacityList = ejbFacade.findAll();
        lastCall = "listAll";
        
    }
    
    public void search() {
        
        System.out.println("capacityController search() start | capacity value : " + capacity);
        capacityList = ejbFacade.findByEntity(capacity);
        lastCall = "search";
        lastSearch = new Capacity(capacity);
        System.out.println("capacityController search() end | capacity List : " + capacityList);
        
    }
    
    public void create() {
        
        ejbFacade.create(capacity);
        capacity = new Capacity();
        refresh();
        
    }
    
    public void createFromUe(Ue ue) {
        
        capacity.setUe(ue);
        ejbFacade.create(capacity);
        capacity = new Capacity();
        refresh();
        
    }
    
    public void update() {
        
        ejbFacade.edit(capacityToEdit);
        refresh();
        
    }
    
    public void delete() {
        
        ejbFacade.remove(capacityToEdit);
        refresh();
        
    }
    
    public void deleteCapacityFromUe() {
        
        Capacity capacityToDelete = (Capacity) dataTableCapacity.getRowData();
        ejbFacade.remove(capacityToDelete);
        
    }
    
    public void saveAction() {
        
	System.out.println("Editing capacity : " + capacityToEdit.getName() + " | " + capacityToEdit.getDescription());
        ejbFacade.edit(capacityToEdit);
        editableRows.clear();
        System.out.println("All rows in dataTableCapacity are now not editable");
        
    }
    
    public void cancelAction() {
        
        editableRows.clear();
        
    }


    public void editAction() {
            
        int rowIndex = dataTableCapacity.getRowIndex();
        editableRows.clear();
        editableRows.put(rowIndex, true);
        capacityToEdit = (Capacity) dataTableCapacity.getRowData();
        System.out.println("Row in editAction() : " + editableRows.get(rowIndex));
            
    }
    
    public void renderDetailsBox() {
        
        capacityToEdit = (Capacity) dataTableCapacity.getRowData();
        editableRows.clear();
        
    }
    
    private void refresh() {
        
        if("search".equals(lastCall))
            capacityList = ejbFacade.findByEntity(lastSearch);
        else if("listAll".equals(lastCall))
            listAll();
        
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public List<Capacity> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }

    public List<Capacity> getCapacityListForUe(Ue ue) {
        
        return ejbFacade.findByUe(ue);
    }

    @FacesConverter(forClass = Capacity.class)
    public static class CapacityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CapacityController controller = (CapacityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "capacityController");
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
            if (object instanceof Capacity) {
                Capacity o = (Capacity) object;
                return getStringKey(o.getCapacityId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Capacity.class.getName());
            }
        }

    }
    
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public CapacityFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CapacityFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public Capacity getCapacityToEdit() {
        return capacityToEdit;
    }

    public void setCapacityToEdit(Capacity capacityToEdit) {
        this.capacityToEdit = capacityToEdit;
    }

    public Capacity getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(Capacity lastSearch) {
        this.lastSearch = lastSearch;
    }

    public List<Capacity> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Capacity> capacityList) {
        this.capacityList = capacityList;
    }

    public HtmlDataTable getDataTableCapacity() {
        return dataTableCapacity;
    }

    public void setDataTableCapacity(HtmlDataTable dataTableCapacity) {
        this.dataTableCapacity = dataTableCapacity;
    }

    public String getLastCall() {
        return lastCall;
    }

    public void setLastCall(String lastCall) {
        this.lastCall = lastCall;
    }

    public Map getEditableRows() {
        return editableRows;
    }

    public void setEditableRows(Map editableRows) {
        this.editableRows = editableRows;
    }
    
    
    
    

}
