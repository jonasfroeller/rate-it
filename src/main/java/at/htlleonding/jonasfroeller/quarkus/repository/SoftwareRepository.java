package at.htlleonding.jonasfroeller.quarkus.repository;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.*;

@ApplicationScoped
public class SoftwareRepository {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public boolean addSoftware(Software software) {
        String softwareName = software.getName();

        if (this.getSoftware(softwareName) != null) {
            throw new IllegalArgumentException(String.format("Software with name equal to %s already exists!", softwareName));
        }

        this.entityManager.persist(software);
        return true;
    }

    @Transactional
    public boolean removeSoftware(String name) {
        Software software = this.getSoftware(name);

        if (software == null) {
            throw new IllegalArgumentException(String.format("Software with name equal to %s doesn't exist!", name));
        }

        this.entityManager.remove(software);
        return true;
    }

    public List<Software> getEverySoftware() {
        return this.entityManager.createQuery("SELECT s FROM Software s").getResultList();
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
        TypedQuery<Software> query =
                entityManager.createQuery("SELECT s FROM Software s WHERE s.name = :name", Software.class).setParameter("name", name);

        List<Software> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
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

    public long getAmountBeingOpenSource() {
        return this.getEverySoftwareBeingOpenSource().size();
    }
}