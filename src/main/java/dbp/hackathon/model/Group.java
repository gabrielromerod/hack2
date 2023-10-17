package dbp.hackathon.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Group {

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "id_group")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "group_persons",
            joinColumns = { @JoinColumn(name = "id_group") },
            inverseJoinColumns = { @JoinColumn(name = "id_person") }
    )
    private Set<Person> courses = new HashSet<>();

    public Group() {
    }

    public Group(String name, Long id, Set<Person> courses) {
        this.name = name;
        this.id = id;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Person> getCourses() {
        return courses;
    }

    public void setCourses(Set<Person> courses) {
        this.courses = courses;
    }
}
