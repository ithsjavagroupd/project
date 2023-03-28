package com.example.springbootproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "chain",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Branch> branches = new ArrayList<>();

}
