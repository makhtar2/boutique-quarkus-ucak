package sn.edu.ucak.dar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Etudiant extends Personne{
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String filiere;
    private Short promotion;
}