package com.example.familyapp.dao;

import com.example.familyapp.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberDAO extends JpaRepository<FamilyMember, Integer> {
    List<FamilyMember> findByFamilyId(int familyId);
}
