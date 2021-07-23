package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractTextContainer {

    private String title;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_creation", nullable = false, updatable = false)
    private Date timeCreation;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_change", nullable = false)
    private Date timeChange;

    public AbstractTextContainer() {
        timeCreation = new Date();
        timeChange = new Date();
    }

    public Date getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(Date timeChange) {
        this.timeChange = timeChange;
    }

    public Date getTimeCreation() {
        return timeCreation;
    }

    public void setTimeCreation(Date timeCreation) {
        this.timeCreation = timeCreation;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, timeChange, timeCreation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || (getClass() != obj.getClass())) return false;
        AbstractTextContainer other = (AbstractTextContainer) obj;
        return Objects.equals(title, other.title) && id == other.id && timeChange == other.timeChange && timeCreation == other.timeCreation;
    }
}