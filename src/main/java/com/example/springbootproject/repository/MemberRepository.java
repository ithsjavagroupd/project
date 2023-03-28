package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Member;
import com.example.springbootproject.projection.MemberName;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface MemberRepository extends ListCrudRepository<Member, Long> {

    List<MemberName> findAllNamesBy();
    MemberName findNamesById(long id);
}
