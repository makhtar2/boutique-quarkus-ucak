package sn.edu.ucak.dar.endpoints;

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

// endpoint personnes
@Path("/personnes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonneResource {

    @Inject
    EntityManager em;

    // liste des personnes
    @GET
    public List<Personne> listAll() {
        return em.createQuery("FROM Personne", Personne.class).getResultList();
    }

    // recuperation d'une personne par id
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

    // ajout d'une personne
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
    // modification d'une personne
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
    // suppression d'une personne
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
