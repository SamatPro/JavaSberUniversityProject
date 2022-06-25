package ru.itis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@ToString(exclude = "customers")
@EqualsAndHashCode(exclude = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CityStatus status;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Customer> customers;
}
