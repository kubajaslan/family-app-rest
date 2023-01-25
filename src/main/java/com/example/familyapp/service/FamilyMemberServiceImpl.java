package com.example.familyapp.service;

import com.example.familyapp.dao.FamilyDAO;
import com.example.familyapp.dao.FamilyMemberDAO;
import com.example.familyapp.dto.FamilyMemberDTO;
import com.example.familyapp.entity.Family;
import com.example.familyapp.entity.FamilyMember;
import com.example.familyapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    @Autowired
    private FamilyDAO familyDAO;

    @Autowired
    private FamilyMemberDAO familyMemberDAO;

    @Autowired
    private com.example.familyapp.validation.FamilyMemberDataValidator familyMemberDataValidator;

    @Override
    public int createFamilyMember(int familyId,FamilyMemberDTO familyMemberDTO) {

        //Validate family member data and throw an InvalidDataException if sth is negative
        familyMemberDataValidator.validate(familyMemberDTO);

        // Create a new family member
        FamilyMember familyMember = new FamilyMember();

        familyMember.setGivenName(familyMemberDTO.getGivenName());

        familyMember.setFamilyName(familyMemberDTO.getFamilyName());

        familyMember.setFamilyId(familyMemberDTO.getFamilyId());

        familyMember.setFamilyId(familyId);


        familyMemberDAO.save(familyMember);

        return familyMemberDTO.getId();
    }


    @Override
    public FamilyMemberDTO getFamilyMember(int familyMemberId) {

        FamilyMember familyMember = familyMemberDAO.findById(familyMemberId)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Family not found"));
        List<FamilyMember> familyMembers = familyMemberDAO.findByFamilyId(familyMemberId);

        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();

        familyMemberDTO.setFamilyName(familyMember.getFamilyName());

        familyMemberDTO.setGivenName(familyMember.getGivenName());

        familyMemberDTO.setId(familyMember.getId());


        return familyMemberDTO;
    }

    @Override
    public void updateFamilyMember(FamilyMemberDTO familyMemberDTO) {
        Family family = familyDAO.findById(familyMemberDTO.getId())
                                 .orElseThrow(() -> new ResourceNotFoundException("Family member not found"));
        family.setFamilyName(familyMemberDTO.getFamilyName());

        familyDAO.save(family);
    }

    @Override
    public void deleteFamilyMember(int familyMemberId) {
        FamilyMember familyMember = familyMemberDAO.findById(familyMemberId)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Family member not found"));

        //do we want to cascade?
        familyMemberDAO.delete(familyMember);
    }
}
