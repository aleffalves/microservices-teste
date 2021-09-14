package com.github.aleff.alves.cambioservice.controller;

import com.github.aleff.alves.cambioservice.model.Cambio;
import com.github.aleff.alves.cambioservice.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("cambio-service")
public class CambioController{

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository cambioRepository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to){
        Cambio byFromAndTo = cambioRepository.findByFromAndTo(from, to);
        if(byFromAndTo == null) throw new RuntimeException("Currency Unsupported");

        String property = environment.getProperty("local.server.port");

        BigDecimal conversionFactor = byFromAndTo.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        byFromAndTo.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        byFromAndTo.setEnvironment(property);

        return byFromAndTo;
    }
}
