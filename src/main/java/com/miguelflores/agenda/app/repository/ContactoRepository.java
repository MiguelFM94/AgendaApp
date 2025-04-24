package com.miguelflores.agenda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelflores.agenda.app.models.Contacto;


public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}

