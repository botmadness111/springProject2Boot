package ru.andrey.springProject2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Name should be not Empty")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Author should be not Empty")
    private String author;

    @Column(name = "yearofrelease")
    @NotNull(message = "Year of release should be not Empty")
    private Integer yearOfRelease;

    @ManyToOne
    @JoinColumn(name = "id_human", referencedColumnName = "id")
    private Human owner;

    @Column(name = "data_of_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_of_taken;

    @Transient
    private Boolean isOverdue;

    public Book(String name, String author, Integer yearOfRelease) {
        this.name = name;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Human getOwner() {
        return owner;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public Date getData_of_taken() {
        return data_of_taken;
    }

    public void setData_of_taken(Date data_of_taken) {
        this.data_of_taken = data_of_taken;
    }

    public Boolean getOverdue() {
        return isOverdue;
    }

    public void setOverdue(Boolean overdue) {
        isOverdue = overdue;
    }
}
