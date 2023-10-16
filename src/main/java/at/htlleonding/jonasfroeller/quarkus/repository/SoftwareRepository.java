package at.htlleonding.jonasfroeller.quarkus.repository;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class SoftwareRepository {
    private final Map<String, Software> softwareMap;

    public SoftwareRepository() {
        this.softwareMap = new HashMap<>();
    }

    public boolean addSoftware(Software software) {
        String softwareName = software.getName();

        if (this.getSoftware(softwareName) != null) {
            throw new IllegalArgumentException(String.format("Software with name equal to %s already exists!", softwareName));
        }

        this.softwareMap.put(softwareName, software);
        return true;
    }

    public boolean removeSoftware(String name) {
        if (this.getSoftware(name) == null) {
            throw new IllegalArgumentException(String.format("Software with name equal to %s doesn't exist!", name));
        }

        this.softwareMap.remove(name);
        return true;
    }

    public List<Software> getEverySoftware() {
        return new LinkedList<>(this.softwareMap.values());
    }

    public List<Software> getEverySoftwareHavingDescription() {
        return new LinkedList<>(
                this.softwareMap.values()
                        .stream()
                        .filter(software -> Objects.nonNull(software.getDescription()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareHavingWebsite() {
        return new LinkedList<>(
                this.softwareMap.values()
                        .stream()
                        .filter(software -> Objects.nonNull(software.getWebsite()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareHavingRepository() {
        return new LinkedList<>(
                this.softwareMap.values()
                        .stream()
                        .filter(software -> Objects.nonNull(software.getRepository()))
                        .toList()
        );
    }

    public List<Software> getEverySoftwareBeingOpenSource() {
        return new LinkedList<>(
                this.softwareMap.values()
                        .stream()
                        .filter(Software::isOpenSource)
                        .toList()
        );
    }

    public Software getSoftware(String name) {
        for (Map.Entry<String, Software> entry : this.softwareMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public int getAmount() {
        return this.softwareMap
                .values()
                .stream()
                .toList()
                .size();
    }

    public int getAmountHavingDescription() {
        return this.softwareMap
                .values()
                .stream()
                .filter(software -> Objects.nonNull(software.getDescription()))
                .toList()
                .size();
    }

    public int getAmountHavingWebsite() {
        return this.softwareMap
                .values()
                .stream()
                .filter(software -> Objects.nonNull(software.getWebsite()))
                .toList()
                .size();
    }

    public int getAmountHavingRepository() {
        return this.softwareMap
                .values()
                .stream()
                .filter(software -> Objects.nonNull(software.getRepository()))
                .toList()
                .size();
    }

    public long getAmountBeingOpenSource() {
        return this.softwareMap
                .values()
                .stream()
                .filter(Software::isOpenSource)
                .count();
    }
}