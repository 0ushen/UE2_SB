/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laurence
 */
@Entity
@Table(name = "organized_ue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganizedUe.findAll", query = "SELECT o FROM OrganizedUe o")
    , @NamedQuery(name = "OrganizedUe.findByOrganizedUeId", query = "SELECT o FROM OrganizedUe o WHERE o.organizedUeId = :organizedUeId")
    , @NamedQuery(name = "OrganizedUe.findByEndDate", query = "SELECT o FROM OrganizedUe o WHERE o.endDate = :endDate")
    , @NamedQuery(name = "OrganizedUe.findByName", query = "SELECT o FROM OrganizedUe o WHERE o.name = :name")
    , @NamedQuery(name = "OrganizedUe.findByStartDate", query = "SELECT o FROM OrganizedUe o WHERE o.startDate = :startDate")})
public class OrganizedUe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organized_ue_id")
    private Integer organizedUeId;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinTable(name = "teacher_organized_ue", joinColumns = {
        @JoinColumn(name = "organized_ue_id", referencedColumnName = "organized_ue_id")}, inverseJoinColumns = {
        @JoinColumn(name = "person_id", referencedColumnName = "person_id")})
    @ManyToMany
    private Collection<Person> personCollection;
    @OneToMany(mappedBy = "organizedUeId")
    private Collection<Indicator> indicatorCollection;
    @OneToMany(mappedBy = "organizedUeId")
    private Collection<Planning> planningCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizedUe")
    private Collection<StudentOrganizedUe> studentOrganizedUeCollection;
    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    @ManyToOne
    private Level levelId;
    @JoinColumn(name = "ue_id", referencedColumnName = "ue_id")
    @ManyToOne
    private Ue ueId;

    public OrganizedUe() {
    }

    public OrganizedUe(Integer organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Integer getOrganizedUeId() {
        return organizedUeId;
    }

    public void setOrganizedUeId(Integer organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

    @XmlTransient
    public Collection<Indicator> getIndicatorCollection() {
        return indicatorCollection;
    }

    public void setIndicatorCollection(Collection<Indicator> indicatorCollection) {
        this.indicatorCollection = indicatorCollection;
    }

    @XmlTransient
    public Collection<Planning> getPlanningCollection() {
        return planningCollection;
    }

    public void setPlanningCollection(Collection<Planning> planningCollection) {
        this.planningCollection = planningCollection;
    }

    @XmlTransient
    public Collection<StudentOrganizedUe> getStudentOrganizedUeCollection() {
        return studentOrganizedUeCollection;
    }

    public void setStudentOrganizedUeCollection(Collection<StudentOrganizedUe> studentOrganizedUeCollection) {
        this.studentOrganizedUeCollection = studentOrganizedUeCollection;
    }

    public Level getLevelId() {
        return levelId;
    }

    public void setLevelId(Level levelId) {
        this.levelId = levelId;
    }

    public Ue getUeId() {
        return ueId;
    }

    public void setUeId(Ue ueId) {
        this.ueId = ueId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizedUeId != null ? organizedUeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganizedUe)) {
            return false;
        }
        OrganizedUe other = (OrganizedUe) object;
        if ((this.organizedUeId == null && other.organizedUeId != null) || (this.organizedUeId != null && !this.organizedUeId.equals(other.organizedUeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrganizedUe[ organizedUeId=" + organizedUeId + " ]";
    }
    
}
