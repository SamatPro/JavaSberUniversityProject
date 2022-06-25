package ru.itis.models;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private String passwordHash;

    @ManyToOne
    private City city;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
}
