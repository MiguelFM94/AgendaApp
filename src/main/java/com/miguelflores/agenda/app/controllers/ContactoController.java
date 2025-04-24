package com.miguelflores.agenda.app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.miguelflores.agenda.app.models.Contacto;
import com.miguelflores.agenda.app.services.ContactoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService service;

    public ContactoController(ContactoService service) {
        this.service = service;
    }


    @GetMapping
    public List<Contacto> getContactList() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactoById(@PathVariable Long id) {
        return service.getContactoById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Contacto saveContacto(@ModelAttribute Contacto contacto) {
        System.out.println("*** Nombre: "+contacto.getNombre());
        System.out.println("*** Telefono: "+contacto.getTelefono());
        System.out.println("*** ID: "+contacto.getId());
        return service.saveContacto(contacto); 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContacto(@PathVariable Long id, @RequestBody Contacto contacto) {   
        id = id != null ? id : (Long) throwBadRequest("El ID del contacto no puede ser nulo");

        return service.getContactoById(id).map(c -> {
            c.setNombre(contacto.getNombre());
            c.setTelefono(contacto.getTelefono());
            return ResponseEntity.ok(service.updateContacto(contacto));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactoById(@PathVariable Long id){

            return service.deleteContacto(id);
            

    } 

    private Object throwBadRequest(String message) {
        System.out.println("*** Error: " + message);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}

