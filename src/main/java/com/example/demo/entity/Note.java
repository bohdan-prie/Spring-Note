package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "notes")
public class Note extends AbstractTextContainer {

	private String body;

	@ManyToOne
	@JoinColumn(name = "user_login", nullable = false)
	private User user;

    public Note(String title, String body, String id) {
    	this();
    	this.body = body;
    	changeTitle(title);
    	setId(id);
    }

	public Note(String title, String body, User user) {
		this();
		this.body = body;
		this.user = user;
		changeTitle(title);
	}
    
    public Note() {
		super();
    }

	public void setUser(User user) {
		this.user = user;
	}

	public String getBody() {
		return body;
	}

	public void changeBody(String body) {
		this.body = body;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(body, super.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (obj == null || (getClass() != obj.getClass())) return false;
		Note note = (Note) obj;
		return body.equals(note.body) && super.equals(note);
	}
}