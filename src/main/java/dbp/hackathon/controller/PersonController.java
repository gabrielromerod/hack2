package dbp.hackathon.controller;


import dbp.hackathon.model.Group;
import dbp.hackathon.model.Person;
import dbp.hackathon.repository.GroupRepository;
import dbp.hackathon.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// import donde esta el     person repository y group repository

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

    // GET/PERSONS/GROUPS/X
    // We have group as an entity, so we can use the group repository to get the group
    @GetMapping("/groups/{id}")
    public ResponseEntity<List<Person>> findByPersonsById(@PathVariable Long id){
        personRepository.findGroupsById(id);
    }
}