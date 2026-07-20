package sn.edu.ucak.dar.endpoints;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import sn.edu.ucak.dar.entities.Cotisation;
import sn.edu.ucak.dar.entities.Marque;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Path("/api/cotisations")
public class CotisationResource {

    @Inject
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cotisation> getAllCotisations() {
        log.info("getAllCotisations");
        //Query query = em.createQuery("select c from Cotisation c");
        TypedQuery<Cotisation> query=em.createNamedQuery("Cotisation.findAll", Cotisation.class);
        List<Cotisation> cotisations=query.getResultList();
        log.info("getAllCotisations returned {} cotisations", cotisations.size());
        return cotisations;
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cotisation saveCotisation(Cotisation cotisation) {
        log.info("saveCotisation {}", cotisation);
        em.persist(cotisation);
        return cotisation;
    }

    @GET
    @Path("/{annee}/{mois}")
    public Cotisation findCotisation(
            @PathParam("annee") Short annee,
            @PathParam("mois") Byte mois) {
        log.info("recherche cotisation annee:{} mois:{}", annee, mois);
        TypedQuery<Cotisation> query=em.createNamedQuery("Cotisation.findByAnneeAndMois", Cotisation.class);
        query.setParameter("annee", annee);
        query.setParameter("mois", mois);
        List<Cotisation> cotisations = query.getResultList();
        if (cotisations.isEmpty() || cotisations.isEmpty()) {
            return null;
        }
        return cotisations.get(0);

    }
}
