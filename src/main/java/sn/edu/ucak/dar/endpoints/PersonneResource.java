package sn.edu.ucak.dar.endpoints;

import sn.edu.ucak.dar.entities.Personne;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Ressource REST JAX-RS pour gérer les opérations CRUD sur l'entité Personne
 * (et ses sous-types Enseignant / Etudiant, héritage single table).
 * Disponible sur le chemin "/personnes".
 */
@Path("/personnes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonneResource {

    @Inject
    EntityManager em;


     // Récupère toutes les personnes .
    @GET
    public List<Personne> listAll() {
        return em.createQuery("FROM Personne", Personne.class).getResultList();
    }


     //Récupère une personne par son ID.
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id) {
        Personne personne = em.find(Personne.class, id);
        if (personne == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Personne non trouvée pour l'ID : " + id)
                           .build();
        }
        return Response.ok(personne).build();
    }


     //Crée une nouvelle personne (envoyer un Enseignant ou un Etudiant fonctionne aussi
    @POST
    @Transactional
    public Response create(Personne personne) {
        if (personne.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("L'ID ne doit pas être fourni pour la création d'une personne.")
                           .build();
        }
        em.persist(personne);
        return Response.status(Response.Status.CREATED).entity(personne).build();
    }


     //Met à jour les champs communs d'une personne existante.
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Personne updateData) {
        Personne personne = em.find(Personne.class, id);
        if (personne == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Personne non trouvée pour l'ID : " + id)
                           .build();
        }

        personne.setNom(updateData.getNom());
        personne.setPrenom(updateData.getPrenom());
        personne.setAdresse(updateData.getAdresse());
        personne.setTelephone(updateData.getTelephone());

        return Response.ok(personne).build();
    }


     //Supprime une personne.
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        Personne personne = em.find(Personne.class, id);
        if (personne == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Personne non trouvée pour l'ID : " + id)
                           .build();
        }
        em.remove(personne);
        return Response.noContent().build();
    }
}
