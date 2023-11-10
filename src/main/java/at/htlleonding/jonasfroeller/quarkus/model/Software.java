package at.htlleonding.jonasfroeller.quarkus.model;

import jakarta.persistence.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity

@NamedQueries({
        @NamedQuery(name = Software.QUERY_GET_ALL, query = "SELECT s FROM Software s ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ONE, query = "SELECT s FROM Software s WHERE s.name = :name")
})

public class Software {
    public static final String QUERY_GET_ALL = "Software.GET.all";
    public static final String QUERY_GET_ONE = "Software.GET.one";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String website;
    private String repository;
    private boolean isOpenSource;

    public Software() {
    }

    public Software(String name) {
        this.setName(name);
    }

    public Software(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public Software(String name, String description, String website) { // set description to null if you don't want one
        this.setName(name);
        this.setDescription(description);
        this.setWebsite(website);
    }

    public Software(String name, String description, String website, String repository, boolean isOpenSource) {
        this.setName(name);
        this.setDescription(description);
        this.setWebsite(website);
        this.setRepository(repository);
        this.setIsOpenSource(isOpenSource);
    }

    public static boolean isValidLink(String link) {
        link = link.trim();

        // add protocol, if it doesn't appear in the string
        if (!link.startsWith("https://") && !link.startsWith("http://")) {
            link = "http://" + link;
        }

        // tested on https://regexr.com
        // looks for protocol, host, domain, tld, port, path
        String urlPattern = "^(https?:\\/\\/)([A-Za-z0-9\\-_]+\\.)*[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+(:\\d+)?(\\/[A-Za-z0-9\\-_]+(\\.?)[A-Za-z0-9\\-_]+)*(\\/?)$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(link);

        return matcher.matches();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name != null ? name.trim() : "";

        if (!name.isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Software has to have a name!");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        if (website != null && isValidLink(website)) {
            this.website = website;
        } else {
            throw new IllegalArgumentException("Website url is not a valid url!");
        }
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        if (repository != null && isValidLink(repository)) {
            this.repository = repository;
        } else {
            throw new IllegalArgumentException("Repository url is not a valid url!");
        }
    }

    public boolean isOpenSource() {
        return isOpenSource;
    }

    public void setIsOpenSource(boolean isOpenSource) {
        this.isOpenSource = isOpenSource;
    }
}