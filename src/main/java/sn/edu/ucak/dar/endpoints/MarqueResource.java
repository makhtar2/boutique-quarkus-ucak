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
import sn.edu.ucak.dar.entities.Marque;

// endpoint marques
@Path("/marques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarqueResource {

    @Inject
    EntityManager em;

    // liste des marques
    @GET
    public List<Marque> listAll() {
        return em.createQuery("FROM Marque", Marque.class).getResultList();
    }

    // recuperation d'une marque par id
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id) {
        Marque marque = em.find(Marque.class, id);
        if (marque == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marque non trouvée pour l'ID : " + id)
                           .build();
        }
        return Response.ok(marque).build();
    }

    // ajout d'une marque
    @POST
    @Transactional
    public Response create(Marque marque) {
        if (marque.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("L'ID ne doit pas être fourni pour la création d'une marque.")
                           .build();
        }
        em.persist(marque);
        return Response.status(Response.Status.CREATED).entity(marque).build();
    }

    // modification d'une marque
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Marque updateData) {
        Marque marque = em.find(Marque.class, id);
        if (marque == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marque non trouvée pour l'ID : " + id)
                           .build();
        }

        marque.setNom(updateData.getNom());
        marque.setDescription(updateData.getDescription());

        return Response.ok(marque).build();
    }

    // suppression d'une marque
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        Marque marque = em.find(Marque.class, id);
        if (marque == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marque non trouvée pour l'ID : " + id)
                           .build();
        }

        // Avant de supprimer, nous devons gérer les produits associés
        // En JPA standard, si des produits pointent vers cette marque (relation obligatoire marque_id NOT NULL),
        // la base de données rejettera la suppression (clé étrangère).
        long count = em.createQuery("SELECT COUNT(p) FROM Produit p WHERE p.marque = :marque", Long.class)
                       .setParameter("marque", marque)
                       .getSingleResult();

        if (count > 0) {
            return Response.status(Response.Status.CONFLICT)
                           .entity("Impossible de supprimer la marque car elle est liée à " + count + " produit(s).")
                           .build();
        }

        em.remove(marque);
        return Response.noContent().build();
    }
}
