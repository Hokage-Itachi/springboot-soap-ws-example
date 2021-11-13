package com.example.producer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq"
            , sequenceName = "users_id_seq"
            , allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;
    @Column
    private Integer age;
}
