package com.backend.ecoponto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "associacoes")
public class Associacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cnpj;
    private String email;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "associacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itensColetados;
}
