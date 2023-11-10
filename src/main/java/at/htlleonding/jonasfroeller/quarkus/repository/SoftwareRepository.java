package at.htlleonding.jonasfroeller.quarkus.repository;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class SoftwareRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Software addSoftware(Software software) {
        if (software == null) {
            return null;
        }

        String softwareName = software.getName();
        if (this.getSoftware(softwareName) != null) {
            throw new IllegalArgumentException(String.format("Software with name equal to %s already exists!", softwareName));
        }

        this.entityManager.persist(software);
        return software;
    }

    @Transactional
    public boolean updateSoftware(Software software) {
        if (software == null) {
            return false;
        }

        String softwareName = software.getName();
        Software softwareFound = this.getSoftware(softwareName);
        if (softwareFound == null) {
            throw new NotFoundException(String.format("Software with name equal to %s doesn't exist!", softwareName));
        }

        this.entityManager.remove(softwareFound);
        this.entityManager.persist(software);
        return true;
    }

    @Transactional
    public boolean removeSoftware(String name) {
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
        return new LinkedList<>(
                this.getEverySoftware().stream()
                        .filter(software -> Objects.nonNull(software.getDescription()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareHavingWebsite() {
        return new LinkedList<>(
                this.getEverySoftware().stream()
                        .filter(software -> Objects.nonNull(software.getWebsite()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareHavingRepository() {
        return new LinkedList<>(
                this.getEverySoftware().stream()
                        .filter(software -> Objects.nonNull(software.getRepository()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareBeingOpenSource() {
        return new LinkedList<>(
                this.getEverySoftware().stream()
                        .filter(Software::isOpenSource)
                        .toList()
        );
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

    public int getAmount() {
        return this.getEverySoftware().size();
    }

    public int getAmountHavingDescription() {
        return this.getEverySoftwareHavingDescription().size();
    }

    public int getAmountHavingWebsite() {
        return this.getEverySoftwareHavingWebsite().size();
    }

    public int getAmountHavingRepository() {
        return this.getEverySoftwareHavingRepository().size();
    }

    public int getAmountBeingOpenSource() {
        return this.getEverySoftwareBeingOpenSource().size();
    }
}