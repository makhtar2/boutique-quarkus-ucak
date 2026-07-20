package sn.edu.ucak.dar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "adresse_livraison", nullable = false, length = 254)
    private String adresseLivraison;


}