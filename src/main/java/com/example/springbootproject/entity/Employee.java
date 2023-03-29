package com.example.springbootproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String phoneNumber;

    // @ManyToOne
    //@JoinColumn(name = "storeEmployeeID",)

}
