package com.example.familyapp.validation;

import com.example.familyapp.dto.FamilyMemberDTO;
import com.example.familyapp.exception.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class FamilyMemberDataValidator {
    public FamilyMemberDataValidator() {
    }

    public void validate(FamilyMemberDTO familyMemberDTO) {


        validateGivenName(familyMemberDTO.getGivenName());
        validateFamilyName(familyMemberDTO.getFamilyName());

    }

    private void validateGivenName(String givenName) {
        if (givenName == null || givenName.trim()
                                          .isEmpty()) {
            throw new InvalidDataException("Given name cannot be null or empty");
        }
    }

    private void validateFamilyName(String familyName) {
        if (familyName == null || familyName.trim()
                                            .isEmpty()) {
            throw new InvalidDataException("Family name cannot be null or empty");
        }
    }


}