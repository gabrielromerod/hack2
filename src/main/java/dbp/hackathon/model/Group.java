package dbp.hackathon.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
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
    private Set<Person> members = new HashSet<>();

    public Group() {
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

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }


    public Group(String name, Long id, Set<Person> members) {
        this.name = name;
        this.id = id;
        this.members = members;
    }
}
