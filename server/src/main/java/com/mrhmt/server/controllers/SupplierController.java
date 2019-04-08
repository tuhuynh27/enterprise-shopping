package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Supplier;
import com.mrhmt.server.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    
    @GetMapping(value="")
    Iterable<Supplier> readAll() {
        return supplierRepository.findAll();
    }

    @GetMapping(value="/{id}")
    Supplier read(@PathVariable int id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping(value="")
    Supplier create(@RequestBody Supplier newSupplier) {
        return supplierRepository.save(newSupplier);
    }

    @PutMapping(value="/{id}")
    Supplier update(@RequestBody Supplier updatingSupplier, @PathVariable int id) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                    supplier.setName(updatingSupplier.getName());
                    supplier.setCompany((updatingSupplier.getCompany()));
                    supplier.setAddress(updatingSupplier.getAddress());
                    supplier.setCity(updatingSupplier.getCity());
                    supplier.setPhone(updatingSupplier.getPhone());
                    supplier.setFax(updatingSupplier.getFax());
                    supplier.setValid(updatingSupplier.isValid());
                    supplier.setModified(Calendar.getInstance().getTime());

                    return supplierRepository.save(supplier);
                })
                .orElseGet(() -> {
                    updatingSupplier.setId(id);

                    return supplierRepository.save(updatingSupplier);
                });
    }

    @DeleteMapping(value="/{id}")
    void delete(@PathVariable int id) {
        supplierRepository.deleteById(id);
    }
}
