package at.htlleonding.jonasfroeller.quarkus.repository;

import at.htlleonding.jonasfroeller.quarkus.model.Software;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SoftwareRepository {
    private static SoftwareRepository instance;
    private final Map<String, Software> softwareMap;

    private SoftwareRepository() {
        this.softwareMap = new HashMap<>();
    }

    public static SoftwareRepository getInstance() {
        if (instance == null) {
            instance = new SoftwareRepository();
        }
        return instance;
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

    public long getAmountOpenSource() {
        return this.softwareMap
                .values()
                .stream()
                .filter(Software::isOpenSource)
                .count();
    }
}