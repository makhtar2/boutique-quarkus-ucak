package sn.edu.ucak.dar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NamedQuery(
        name = "Cotisation.findAll",
        query = "select c from Cotisation c")
@NamedQuery(
        name = "Cotisation.findByAnneeAndMois" ,
        query = "select c from Cotisation c where c.annee=:annee and c.mois=:mois")
@Entity
@Table(name = "cotisation")
public class Cotisation {
    @Id
    private Short annee;

    @Id
    private Byte mois;

    private Double montant;

    private String devise;

}