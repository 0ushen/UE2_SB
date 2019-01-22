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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laurence
 */
@Entity
@Table(name = "planning")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planning.findAll", query = "SELECT p FROM Planning p")
    , @NamedQuery(name = "Planning.findByPlanningId", query = "SELECT p FROM Planning p WHERE p.planningId = :planningId")
    , @NamedQuery(name = "Planning.findByRoom", query = "SELECT p FROM Planning p WHERE p.room = :room")
    , @NamedQuery(name = "Planning.findBySeanceDate", query = "SELECT p FROM Planning p WHERE p.seanceDate = :seanceDate")
    , @NamedQuery(name = "Planning.findByStartHour", query = "SELECT p FROM Planning p WHERE p.startHour = :startHour")})
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "planning_id")
    private Integer planningId;
    @Size(max = 255)
    @Column(name = "room")
    private String room;
    @Column(name = "seance_date")
    @Temporal(TemporalType.DATE)
    private Date seanceDate;
    @Column(name = "start_hour")
    @Temporal(TemporalType.TIME)
    private Date startHour;
    @JoinColumn(name = "organized_ue_id", referencedColumnName = "organized_ue_id")
    @ManyToOne
    private OrganizedUe organizedUeId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @OneToMany(mappedBy = "planningId")
    private Collection<Presence> presenceCollection;

    public Planning() {
    }

    public Planning(Integer planningId) {
        this.planningId = planningId;
    }

    public Integer getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Integer planningId) {
        this.planningId = planningId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getSeanceDate() {
        return seanceDate;
    }

    public void setSeanceDate(Date seanceDate) {
        this.seanceDate = seanceDate;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public OrganizedUe getOrganizedUeId() {
        return organizedUeId;
    }

    public void setOrganizedUeId(OrganizedUe organizedUeId) {
        this.organizedUeId = organizedUeId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @XmlTransient
    public Collection<Presence> getPresenceCollection() {
        return presenceCollection;
    }

    public void setPresenceCollection(Collection<Presence> presenceCollection) {
        this.presenceCollection = presenceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planningId != null ? planningId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planning)) {
            return false;
        }
        Planning other = (Planning) object;
        if ((this.planningId == null && other.planningId != null) || (this.planningId != null && !this.planningId.equals(other.planningId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Planning[ planningId=" + planningId + " ]";
    }
    
}
