package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Member;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface MemberRepository extends ListCrudRepository<Member, Long> {

}
