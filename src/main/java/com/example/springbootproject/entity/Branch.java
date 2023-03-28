package com.example.springbootproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "chainId_fk")
    @JsonBackReference
    private Chain chain;
}
