# Repository

Los repositorios son interfaces que extienden de JpaRepository. Estas interfaces son utilizadas para realizar operaciones CRUD en la base de datos.

Simplemente crea una interfaz que extienda de JpaRepository y añade los métodos que necesites. Por ejemplo:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Long representa el tipo de dato del id de la tabla
    Student findByName(String name);
}
```

## Búsqueda con los métodos de JpaRepository

JpaRepository tiene varios métodos para realizar consultas en la base de datos. Por ejemplo:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Long representa el tipo de dato del id de la tabla
    Student findByName(String name); 
    
    List<Student> findByNameContaining(String name);
    
    List<Student> findByNameContainingIgnoreCaseAndAgeGreaterThan(String name, int age);
}
```

Que hemos hecho en el ejemplo anterior:

- **findByNameContaining**: Busca los estudiantes que contengan el nombre especificado.
- **findByNameContainingIgnoreCaseAndAgeGreaterThan**: Busca los estudiantes que contengan el nombre especificado, ignorando mayúsculas y minúsculas, y que tengan una edad mayor a la especificada.

## Implementación de métodos personalizados

Si necesitas realizar una consulta personalizada que la interfaz JpaRepository no tiene, puedes crear un método personalizado en el repositorio. Por ejemplo:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Long representa el tipo de dato del id de la tabla
    Student findByName(String name);
    
    @Query("SELECT s FROM Student s WHERE s.name = :name")
    Student findByNameCustomQuery(@Param("name") String name);
}
```