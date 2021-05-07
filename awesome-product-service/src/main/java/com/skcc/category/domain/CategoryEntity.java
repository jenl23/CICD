package com.skcc.category.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="categories")
public class CategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long id;

    @Column(name="name")
    private String name;

    @Column(name="priority")
    private long priority;

    @Column(name="active")
    private String active;

    @Column(name="createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;

    public CategoryEntity() {
    }
//    public CategoryEntity(String name, long priority, String active) {
//        this.name = name;
//        this.priority = priority;
//        this.active = active;
//    }

}
