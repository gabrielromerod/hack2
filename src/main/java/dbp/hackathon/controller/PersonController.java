package dbp.hackathon.controller;


import dbp.hackathon.model.Group;
import dbp.hackathon.model.Person;
import dbp.hackathon.repository.GroupRepository;
import dbp.hackathon.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

// import donde esta el     person repository y group repository

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    public ResponseEntity<List<Person>> persons(){
        List<Person> persons = personRepository.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> person(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Los a los que pertenece la persona por su id
    @GetMapping("/groups/{id}")
    public ResponseEntity<List<Group>> groups(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            List<Group> groupsList = new ArrayList<>(person.get().getGroups());
            return new ResponseEntity<>(groupsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}