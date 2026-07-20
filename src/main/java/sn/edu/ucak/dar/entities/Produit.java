package sn.edu.ucak.dar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marque_id", nullable = false)
    private Marque marque;

    @Column(name = "prix", nullable = false, precision = 13, scale = 2)
    private BigDecimal prix;

    @Column(name = "code", nullable = false, length = 7)
    private String code;

    @Column(name = "dateCreation", nullable = false)
    private Instant dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Instant dateModification;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "description", length = 500)
    private String description;


}