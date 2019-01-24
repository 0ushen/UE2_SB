package ui;

import entity.Section;
import ui.util.JsfUtil;
import business.SectionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("sectionController")
@ViewScoped
public class SectionController implements Serializable {
    
    @Inject
    private SectionFacade ejbFacade;
    
    private Section section = new Section();
    private Section sectionToEdit = new Section();
    private Section lastSearch;
    private List<Section> sectionList = new ArrayList<>();
    private HtmlDataTable dataTableSection;
    private String lastCall;
    
    
    public void listAll() {
        
        sectionList = ejbFacade.findAll();
        lastCall = "listAll";
        
    }
    
    public void search() {
        
        System.out.println("sectionController search() start | person List : " + 
                sectionList);
        sectionList = ejbFacade.findByEntity(section);
        lastCall = "search";
        lastSearch = new Section(section);
        System.out.println("sectionController search() end | person List : " + 
                sectionList);
        
    }
    
    public void create() {
        
        ejbFacade.create(section);
        section = new Section();
        refresh();
        
    }
    
    public void update() {
        
        ejbFacade.edit(sectionToEdit);
        refresh();
    }
    
    public void delete() {
        
        ejbFacade.remove(sectionToEdit);
        refresh();
        
    }
    
    private void refresh() {
        
        if("search".equals(lastCall))
            sectionList = ejbFacade.findByEntity(lastSearch);
        else if("listAll".equals(lastCall))
            listAll();
        
    }
    
    public void renderDetailsBox() {
        
        sectionToEdit = (Section) dataTableSection.getRowData();
        
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public List<Section> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }


    @FacesConverter(forClass = Section.class)
    public static class SectionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SectionController controller = 
                    (SectionController) facesContext.getApplication().
                    getELResolver().getValue(facesContext.getELContext(),
                    null, "sectionController");
            
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
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Section) {
                Section o = (Section) object;
                return getStringKey(o.getSectionId());
            } else {
                throw new IllegalArgumentException("object " 
                        + object + " is of type " + object.getClass().getName() 
                        + "; expected type: " + Section.class.getName());
            }
        }

    }
    
    
       
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    

    public SectionFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SectionFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSectionToEdit() {
        return sectionToEdit;
    }

    public void setSectionToEdit(Section sectionToEdit) {
        this.sectionToEdit = sectionToEdit;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public HtmlDataTable getDataTableSection() {
        return dataTableSection;
    }

    public void setDataTableSection(HtmlDataTable dataTableSection) {
        this.dataTableSection = dataTableSection;
    }

    public String getLastCall() {
        return lastCall;
    }

    public void setLastCall(String lastCall) {
        this.lastCall = lastCall;
    }
    

}
