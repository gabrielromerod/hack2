package dbp.hackathon.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "persons")
    private Set<dbp.hackathon.model.Group> groups = new HashSet<>();

    // getters and setters


    public Person() {
    }

    public Person(Long id, String name, Set<Group> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Group> getCourses() {
        return groups;
    }

    public void setCourses(Set<Group> groups) {
        this.groups = groups;
    }
}