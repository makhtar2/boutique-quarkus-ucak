package sn.edu.ucak.dar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Enseignant extends Personne {
    private String matricule;
    private String matiere;
}