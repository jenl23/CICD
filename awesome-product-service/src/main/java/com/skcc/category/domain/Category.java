package com.skcc.category.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Category {
	
	private long id;
	private String name;
	private long priority;
	private String active;
	private String createdAt;

	public Category(long id, String name, long priority, String active, String createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.active = active;
		this.createdAt = createdAt;
	}

	public Category() {}

	public long getId() {
		return id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", priority=" + priority + ", active=" + active
				+ ", createdAt=" + createdAt + "]";
	}
	
}
