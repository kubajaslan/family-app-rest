package com.example.familyapp.dao;

import com.example.familyapp.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyDAO extends JpaRepository<Family, Integer> {
    Family findByFamilyName(String familyName);
}
