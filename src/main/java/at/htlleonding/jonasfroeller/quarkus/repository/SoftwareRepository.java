package at.htlleonding.jonasfroeller.quarkus.repository;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class SoftwareRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Software addSoftware(Software software) {
        if (software == null) {
            throw new ClientErrorException("Software is invalid!", Response.status(400).build());
        }

        String softwareName = software.getName();
        if (this.getSoftware(softwareName) != null) {
            throw new ClientErrorException(String.format("Software with name equal to %s already exists!", softwareName), Response.status(400).build());
        }

        this.entityManager.persist(software);
        return software;
    }

    public Software findSoftware(Software software) {
        if (software == null) {
            throw new ClientErrorException("Software is invalid!", Response.status(400).build());
        }

        String softwareName = software.getName();
        Software softwareFound = this.getSoftware(softwareName);
        if (softwareFound == null) {
            throw new NotFoundException(String.format("Software with name equal to %s doesn't exist!", softwareName));
        }

        return softwareFound;
    }

    @Transactional
    public boolean replaceSoftware(Software software) {
        Software softwareFound = findSoftware(software);

        this.entityManager.remove(softwareFound);
        this.entityManager.persist(software);
        return true;
    }

    @Transactional
    public boolean updateSoftware(Software software) {
        Software softwareFound = findSoftware(software);

        Software s = this.entityManager.merge(softwareFound);
        s.update(software);
        return true;
    }

    @Transactional
    public boolean removeSoftware(String name) {
        if (name == null) {
            throw new ClientErrorException("Software is invalid!", Response.status(400).build());
        }

        Software software = this.getSoftware(name);
        if (software == null) {
            throw new NotFoundException(String.format("Software with name equal to %s doesn't exist!", name));
        }

        this.entityManager.remove(software);
        return true;
    }

    public List<Software> getEverySoftware() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL, Software.class).getResultList();
    }

    public List<Software> getEverySoftwareHavingDescription() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_DESCRIPTION, Software.class).getResultList();
    }

    public List<Software> getEverySoftwareHavingWebsite() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_WEBSITE, Software.class).getResultList();
    }

    public List<Software> getEverySoftwareHavingRepository() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_REPOSITORY, Software.class).getResultList();
    }

    public List<Software> getEverySoftwareBeingOpenSource() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_BEING_OPEN_SOURCE, Software.class).getResultList();
    }

    public Software getSoftware(String name) {
        try {
            TypedQuery<Software> query = entityManager.createNamedQuery(Software.QUERY_GET_ONE, Software.class);
            query.setParameter("name", name);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public long getAmount() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_COUNT, long.class).getSingleResult();
    }

    public long getAmountHavingDescription() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_DESCRIPTION_COUNT, long.class).getSingleResult();
    }

    public long getAmountHavingWebsite() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_WEBSITE_COUNT, long.class).getSingleResult();
    }

    public long getAmountHavingRepository() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_HAVING_REPOSITORY_COUNT, long.class).getSingleResult();
    }

    public long getAmountBeingOpenSource() {
        return this.entityManager.createNamedQuery(Software.QUERY_GET_ALL_BEING_OPEN_SOURCE_COUNT, long.class).getSingleResult();
    }
}