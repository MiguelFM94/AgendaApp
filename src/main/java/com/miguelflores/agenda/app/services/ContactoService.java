package com.miguelflores.agenda.app.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.miguelflores.agenda.app.models.Contacto;
import com.miguelflores.agenda.app.repository.ContactoRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;


@Service
public class ContactoService {

    private final ContactoRepository repository;

    public ContactoService(ContactoRepository repository){
        this.repository = repository;
    }

    public List<Contacto> getAll(){
         return this.repository.findAll();
    }

    public Optional<Contacto> getContactoById(Long id){
        return repository.findById(id);
    }

    public Contacto saveContacto(Contacto contacto){
        if (contacto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El contacto no puede ser nulo");
        }
        
        if (contacto.getNombre() == null || contacto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del contacto es obligatorio");
        }
        return repository.save(contacto);
    }

    public ResponseEntity<Void> deleteContacto(Long id){
        
       return getContactoById(id)
        .map(contactoDelete -> {
            repository.delete(contactoDelete);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElseThrow(() -> {
            return new EntityNotFoundException("No existe ningún contacto con el ID: " + id);
        });
    }

    public Object updateContacto(Contacto contactoDetails) {
       
        Contacto contacto = getContactoById(contactoDetails.getId())
            .orElseThrow(() -> new EntityNotFoundException("Contacto no encontrado con id: " + contactoDetails.getId()));
        
        if (contactoDetails.getNombre() != null) {
            contacto.setNombre(contactoDetails.getNombre());
        }
        
        if (contactoDetails.getTelefono() != null) {
            contacto.setTelefono(contactoDetails.getTelefono());
        }
        
        return saveContacto(contacto);
    }
}
