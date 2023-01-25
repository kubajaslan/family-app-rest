package com.example.familyapp.controller;

import com.example.familyapp.dao.FamilyMemberDAO;
import com.example.familyapp.dto.FamilyMemberDTO;
import com.example.familyapp.entity.FamilyMember;
import com.example.familyapp.exception.InvalidDataException;
import com.example.familyapp.service.FamilyMemberService;
import com.example.familyapp.validation.FamilyMemberDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/familymembers")
public class FamilyMemberApp {

    private final FamilyMemberDataValidator familyMemberDataValidator;

    private final FamilyMemberService familyMemberService;

    private final FamilyMemberDAO familyMemberDAO;

    @Autowired
    public FamilyMemberApp(FamilyMemberDataValidator familyMemberDataValidator, FamilyMemberService familyMemberService, FamilyMemberDAO familyMemberDAO) {


        this.familyMemberDataValidator = familyMemberDataValidator;
        this.familyMemberService = familyMemberService;
        this.familyMemberDAO = familyMemberDAO;
    }

    @PostMapping
    public ResponseEntity<Integer> createFamilyMember(@RequestBody FamilyMemberDTO familyMemberDTO, @PathVariable int familyId) {


        try {
            familyMemberDataValidator.validate(familyMemberDTO);
        } catch (InvalidDataException e) {
            throw e;
        }

        int familyMemberId = familyMemberService.createFamilyMember(familyId, familyMemberDTO);


        //verify the thing below
        return new ResponseEntity<>(familyMemberId, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FamilyMember>> getFamilyMember(@PathVariable int id) {

        //rather than try catch?
        Optional<FamilyMember> familyMember = familyMemberDAO.findById(id);

        if (familyMember.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(familyMember, HttpStatus.OK);
    }

}
