package com.example.demo.entity;

import java.util.Objects;

public class ToDo {
	private String body;
	private boolean marked;

	public ToDo(String body, boolean marked) {
		this.body = body;
		this.marked = marked;
	}
	
	public ToDo() {

	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public String getBody() {
		return body;
	}

	public void changeBody(String body) {
		this.body = body;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(body, marked);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || (getClass() != obj.getClass())) return false;
		ToDo other = (ToDo) obj;
		return Objects.equals(body, other.body) && marked == other.marked;
	}
}