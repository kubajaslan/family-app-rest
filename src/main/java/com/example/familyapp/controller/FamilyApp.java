package com.example.familyapp.controller;

import com.example.familyapp.dao.FamilyDAO;
import com.example.familyapp.dto.FamilyDTO;
import com.example.familyapp.dto.FamilyMemberDTO;
import com.example.familyapp.entity.Family;
import com.example.familyapp.exception.InvalidDataException;
import com.example.familyapp.exception.ResourceNotFoundException;
import com.example.familyapp.service.FamilyService;
import com.example.familyapp.validation.FamilyDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/families")
public class FamilyApp {

    private final FamilyDataValidator familyDataValidator;

    private final FamilyDAO familyDAO;

    private final FamilyService familyServiceImpl;

    private final FamilyMemberApp familyMemberApp;

    @Autowired
    public FamilyApp(FamilyDataValidator familyDataValidator, FamilyDAO familyDAO, FamilyService familyServiceImpl, FamilyMemberApp familyMemberApp) {
        this.familyDataValidator = familyDataValidator;
        this.familyDAO = familyDAO;
        this.familyServiceImpl = familyServiceImpl;
        this.familyMemberApp = familyMemberApp;
    }

    @PostMapping
    public ResponseEntity<Object> createFamily(@RequestBody FamilyDTO familyDTO) {

        try {
            familyDataValidator.validate(familyDTO);
        } catch (InvalidDataException e) {
            throw e;
        }


        int familyId = familyServiceImpl.createFamily(familyDTO);

        for (FamilyMemberDTO member :
                familyDTO.getFamilyMembers()) {
            familyMemberApp.createFamilyMember(member, familyId);
        }


        return new ResponseEntity<>(familyId, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FamilyDTO> getFamily(@PathVariable int id) {

        //not optimal i think
        FamilyDTO family;

        try {
            family = familyServiceImpl.getFamily(id);

        } catch (ResourceNotFoundException e) {
            throw e;
        }

        return new ResponseEntity<>(family, HttpStatus.OK);
    }
}

