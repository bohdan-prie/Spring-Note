package com.example.demo.entity;

import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "to_do_line")
@TypeDef(
		name = "json",
		typeClass = JsonType.class
)
public class ToDoLine extends AbstractTextContainer{
	private static final Logger LOG = LogManager.getLogger(ToDoLine.class.getName());

	@NotNull
	@Type(type = "json")
	@Column(name = "to_do", columnDefinition = "jsonb")
	private List<ToDo> toDo;

	@ManyToOne
	@JoinColumn(name = "user_login", nullable = false)
	private User user;

	public ToDoLine(String title, List<ToDo> toDo, String id) {
		this();
		changeTitle(title);
		setId(id);
		this.toDo = toDo;
	}

	public ToDoLine(String title, User user){
		this();
		changeTitle(title);
		this.user = user;
	}
	
	public ToDoLine() {
		super();
		toDo = new ArrayList<>();		
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ToDo> getToDo() {
		return toDo;
	}

	public void setToDo(List<ToDo> toDo) {
		if (toDo == null) {
			LOG.debug("ToDo list is null");
		} else {
			this.toDo = toDo;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(toDo, super.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (obj == null || (getClass() != obj.getClass())) return false;
		ToDoLine toDoLine = (ToDoLine) obj;
		return super.equals(toDoLine) && toDo.equals(toDoLine.toDo);
	}
}