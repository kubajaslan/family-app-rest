package com.example.familyapp.service;

import com.example.familyapp.dao.FamilyDAO;
import com.example.familyapp.dao.FamilyMemberDAO;
import com.example.familyapp.dto.FamilyDTO;
import com.example.familyapp.dto.FamilyMemberDTO;
import com.example.familyapp.entity.Family;
import com.example.familyapp.entity.FamilyMember;
import com.example.familyapp.exception.ResourceNotFoundException;
import com.example.familyapp.validation.FamilyDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyDAO familyDAO;

    @Autowired
    private FamilyMemberDAO familyMemberDAO;

    @Autowired
    private FamilyDataValidator FamilyDataValidator;



    @Override
    public int createFamily(FamilyDTO familyDTO) {


        // Create a new family
        Family family = new Family();
        family.setFamilyName(familyDTO.getFamilyName());
        family.setNrOfAdults(familyDTO.getNrOfAdults());
        family.setNrOfChildren(familyDTO.getNrOfChildren());
        family.setNrOfInfants(familyDTO.getNrOfInfants());
        family = familyDAO.save(family);

        // Create family members
        List<FamilyMemberDTO> familyMembers = familyDTO.getFamilyMembers();
        for (FamilyMemberDTO familyMemberDTO : familyMembers) {
            FamilyMember familyMember = new FamilyMember();
            familyMember.setFamilyId(family.getId());
            familyMember.setGivenName(familyMemberDTO.getGivenName());
            familyMember.setFamilyName(familyMemberDTO.getFamilyName());

            familyMemberDAO.save(familyMember);
        }
        return family.getId();
    }

    @Override
    public FamilyDTO getFamily(int familyId) {

        Family family = familyDAO.findById(familyId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        List<FamilyMember> familyMembers = familyMemberDAO.findByFamilyId(familyId);

        List<FamilyMemberDTO> familyMemberDTOs = new ArrayList<>();

        for (FamilyMember familyMember : familyMembers) {
            FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
            familyMemberDTO.setGivenName(familyMember.getGivenName());
            familyMemberDTO.setFamilyId(familyId);
            familyMemberDTO.setId(familyMember.getId());
            familyMemberDTO.setFamilyName(familyMember.getFamilyName());
            familyMemberDTOs.add(familyMemberDTO);
        }

        FamilyDTO familyDTO = new FamilyDTO();
        familyDTO.setFamilyName(family.getFamilyName());
        familyDTO.setId(family.getId());
        familyDTO.setNrOfAdults(family.getNrOfAdults());
        familyDTO.setNrOfChildren(family.getNrOfChildren());
        familyDTO.setNrOfInfants(family.getNrOfInfants());
        familyDTO.setFamilyMembers(familyMemberDTOs);
        return familyDTO;
    }

    @Override
    public void updateFamily(FamilyDTO familyDTO) {
        Family family = familyDAO.findById(familyDTO.getId())
                                 .orElseThrow(() -> new ResourceNotFoundException("Family not found"));
        family.setFamilyName(familyDTO.getFamilyName());
        family.setNrOfInfants(familyDTO.getNrOfInfants());
        family.setNrOfChildren(familyDTO.getNrOfChildren());
        family.setNrOfAdults(familyDTO.getNrOfAdults());
        familyDAO.save(family);
    }

    @Override
    public void deleteFamily(int familyId) {
        Family family = familyDAO.findById(familyId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        //do we want to cascade? or leave orphans?
        familyDAO.delete(family);
    }
}