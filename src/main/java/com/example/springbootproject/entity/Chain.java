package com.example.springbootproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NamedEntityGraph(name = "Chain.members",
    attributeNodes = @NamedAttributeNode("members") )
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    String name;
    @NonNull
    String address;

    @ManyToMany
    private Set<Member> members = new HashSet<>();

}
