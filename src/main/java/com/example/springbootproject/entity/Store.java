package com.example.springbootproject.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String address;
    String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Chain chain;
}
