package com.github.aleff.alves.cambioservice.repository;

import com.github.aleff.alves.cambioservice.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioRepository extends JpaRepository<Cambio,Long> {
    Cambio findByFromAndTo(String from, String to);
}
