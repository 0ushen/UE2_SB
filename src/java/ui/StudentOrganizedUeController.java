package ui;

import entity.StudentOrganizedUe;
import ui.util.JsfUtil;
import ui.util.PaginationHelper;
import business.StudentOrganizedUeFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("studentOrganizedUeController")
@SessionScoped
public class StudentOrganizedUeController implements Serializable {

    private StudentOrganizedUe current;
    private DataModel items = null;
    @EJB
    private business.StudentOrganizedUeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public StudentOrganizedUeController() {
    }

    public StudentOrganizedUe getSelected() {
        if (current == null) {
            current = new StudentOrganizedUe();
            current.setStudentOrganizedUePK(new entity.StudentOrganizedUePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private StudentOrganizedUeFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (StudentOrganizedUe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new StudentOrganizedUe();
        current.setStudentOrganizedUePK(new entity.StudentOrganizedUePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getStudentOrganizedUePK().setPersonId(current.getPerson().getPersonId());
            current.getStudentOrganizedUePK().setOrganizedUeId(current.getOrganizedUe().getOrganizedUeId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentOrganizedUeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (StudentOrganizedUe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getStudentOrganizedUePK().setPersonId(current.getPerson().getPersonId());
            current.getStudentOrganizedUePK().setOrganizedUeId(current.getOrganizedUe().getOrganizedUeId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentOrganizedUeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (StudentOrganizedUe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentOrganizedUeDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public StudentOrganizedUe getStudentOrganizedUe(entity.StudentOrganizedUePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = StudentOrganizedUe.class)
    public static class StudentOrganizedUeControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StudentOrganizedUeController controller = (StudentOrganizedUeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "studentOrganizedUeController");
            return controller.getStudentOrganizedUe(getKey(value));
        }

        entity.StudentOrganizedUePK getKey(String value) {
            entity.StudentOrganizedUePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entity.StudentOrganizedUePK();
            key.setPersonId(Integer.parseInt(values[0]));
            key.setOrganizedUeId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(entity.StudentOrganizedUePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPersonId());
            sb.append(SEPARATOR);
            sb.append(value.getOrganizedUeId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof StudentOrganizedUe) {
                StudentOrganizedUe o = (StudentOrganizedUe) object;
                return getStringKey(o.getStudentOrganizedUePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + StudentOrganizedUe.class.getName());
            }
        }

    }

}
