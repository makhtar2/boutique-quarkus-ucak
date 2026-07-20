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

// endpoint etudiants
@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    @Inject
    EntityManager em;

    // liste des etudiants
    @GET
    public List<Etudiant> listAll() {
        return em.createQuery("FROM Etudiant", Etudiant.class).getResultList();
    }

    // recuperation d'un etudiant par id
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id) {
        Etudiant etudiant = em.find(Etudiant.class, id);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Etudiant non trouvé pour l'ID : " + id)
                           .build();
        }
        return Response.ok(etudiant).build();
    }

    // ajout d'un etudiant
    @POST
    @Transactional
    public Response create(Etudiant etudiant) {
        if (etudiant.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("L'ID ne doit pas être fourni pour la création d'un étudiant.")
                           .build();
        }
        em.persist(etudiant);
        return Response.status(Response.Status.CREATED).entity(etudiant).build();
    }

    // modification d'un etudiant
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Etudiant updateData) {
        Etudiant etudiant = em.find(Etudiant.class, id);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Etudiant non trouvé pour l'ID : " + id)
                           .build();
        }

        etudiant.setNom(updateData.getNom());
        etudiant.setPrenom(updateData.getPrenom());
        etudiant.setAdresse(updateData.getAdresse());
        etudiant.setTelephone(updateData.getTelephone());
        etudiant.setDateNaissance(updateData.getDateNaissance());
        etudiant.setLieuNaissance(updateData.getLieuNaissance());
        etudiant.setFiliere(updateData.getFiliere());
        etudiant.setPromotion(updateData.getPromotion());

        return Response.ok(etudiant).build();
    }

    // suppression d'un etudiant
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        Etudiant etudiant = em.find(Etudiant.class, id);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Etudiant non trouvé pour l'ID : " + id)
                           .build();
        }
        em.remove(etudiant);
        return Response.noContent().build();
    }
}
