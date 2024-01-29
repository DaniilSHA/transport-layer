package ru.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.model.Gender;
import ru.example.model.Person;
import ru.example.store.Store;

import java.util.List;

@RestController
@RequestMapping("api/v0/person")
public class PersonController {

    @Autowired
    private Store store;

    @Operation(summary = "postPerson", description = "...")
    @ApiResponses(value = @ApiResponse(responseCode = "400", description = "NoValidIdException"))
    @PostMapping
    private void postPerson(@RequestBody Person person) {
        store.create(person);
    }

    @Operation(summary = "getPersonById", description = "...")
    @ApiResponses(value = @ApiResponse(responseCode = "404", description = "PersonNotFoundException"))
    @GetMapping("/{id}")
    @ResponseBody
    private Person getPersonById(@PathVariable int id) {
        return store.read(id);
    }

    @Operation(summary = "putPerson", description = "...")
    @ApiResponses(value = @ApiResponse(responseCode = "404", description = "PersonNotFoundException"))
    @PutMapping
    @ResponseBody
    private void putPerson(@RequestBody Person person) {
        store.update(person);
    }

    @Operation(summary = "deletePersonById", description = "...")
    @ApiResponses(value = @ApiResponse(responseCode = "404", description = "PersonNotFoundException"))
    @DeleteMapping("/{id}")
    private void deletePersonById(@PathVariable int id) {
        store.delete(id);
    }

    @Operation(summary = "getAllPersons", description = "...")
    @GetMapping
    @ResponseBody
    private List<Person> getAllPersons(@RequestParam(name = "gender", required = false) Gender gender) {
        return store.readAll(gender);
    }
}
