package com.example.springbootproject.projection;

import com.example.springbootproject.entity.Member;

import java.util.List;

public interface ChainName {
    String getName();
    Long getId();

    List<Member> getMembers();
}
