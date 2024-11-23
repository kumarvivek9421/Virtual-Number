package com.virtualNumber.virtualNumber.controllers;

import com.virtualNumber.virtualNumber.entites.VirtualPhoneNumber;
import com.virtualNumber.virtualNumber.repositores.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone-numbers")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @PostMapping("/phone")
    public ResponseEntity<VirtualPhoneNumber> createPhoneNumber(@RequestBody VirtualPhoneNumber phoneNumber){
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneNumberRepository.save(phoneNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VirtualPhoneNumber> getPhoneNumber(@PathVariable Long id){
        return phoneNumberRepository.findById(id).map(ResponseEntity::ok).orElseThrow();
    }
}
