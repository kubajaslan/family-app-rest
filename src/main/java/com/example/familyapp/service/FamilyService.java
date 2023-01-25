package com.example.familyapp.service;

import com.example.familyapp.dto.FamilyDTO;

public interface FamilyService {
    int createFamily(FamilyDTO familyDTO);
    FamilyDTO getFamily(int familyId);
    void updateFamily(FamilyDTO familyDTO);
    void deleteFamily(int familyId);
}