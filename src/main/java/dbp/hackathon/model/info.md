# Model 

Los modelos son clases que representan las tablas de la base de datos. Estas clases son utilizadas para mapear los datos de la base de datos a objetos de Java.

Hibernate tiene 4 tipos de relaciones entre modelos:

- OneToOne
- OneToMany
- ManyToOne
- ManyToMany

Nota: **ManyToOne** es lo mismo que **OneToMany** pero al revés. **ManyToOne** es utilizado para mapear la relación de muchos a uno. 

Nota: No olvides crear los getters, setters y constructores de las clases. Si no lo haces, no podrás acceder a los datos de la base de datos.

## Implementación OneToMany

Tenemos dos modelos uno es Student y el otro es Grade. Un Student puede tener muchas notas, pero una nota solo puede pertenecer a un estudiante. Por lo tanto, la relación entre Student y Grade es OneToMany.

```java
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    // getters and setters
}
```

```java
@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // getters and setters
}
```

## Implementación ManyToMany

Tenemos dos modelos uno es Student y el otro es Course. Un Student puede tener muchos cursos y un curso puede tener muchos estudiantes. Por lo tanto, la relación entre Student y Course es ManyToMany.

```java
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "student_course", 
        joinColumns = { @JoinColumn(name = "student_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    private Set<Course> courses = new HashSet<>();

    // getters and setters
}
```

```java
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    // getters and setters
}
```

## Implementación OneToOne

Tenemos dos modelos uno es Student y el otro es Address. Un Student puede tener una dirección y una dirección solo puede pertenecer a un estudiante. Por lo tanto, la relación entre Student y Address es OneToOne.

```java
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // getters and setters
}
```

```java
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    // getters and setters
}
```