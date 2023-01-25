package com.example.familyapp.validation;

import com.example.familyapp.dto.FamilyDTO;
import com.example.familyapp.exception.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class FamilyDataValidator {

    public FamilyDataValidator() {
    }

    public void validate(FamilyDTO familyDTO) {


        if (familyDTO.getNrOfInfants() < 0) {

            throw new InvalidDataException("Number of infants cannot be less than 0");

        }
        if (familyDTO.getNrOfChildren() < 0) {
            throw new InvalidDataException("Number of children cannot be less than 0");


        }
        if (familyDTO.getNrOfAdults() < 0) {

            throw new InvalidDataException("Number of adults cannot be less than 0");

        }
    }


}
