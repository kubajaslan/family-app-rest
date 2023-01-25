package com.example.familyapp.service;

import com.example.familyapp.dto.FamilyDTO;
import com.example.familyapp.dto.FamilyMemberDTO;

public interface FamilyMemberService {
    int createFamilyMember(int familyId, FamilyMemberDTO familyMemberDTO);

    FamilyMemberDTO getFamilyMember(int familyMemberId);

    void updateFamilyMember(FamilyMemberDTO familyMemberDTO);

    void deleteFamilyMember(int familyMemberId);
}
