package at.htlleonding.jonasfroeller.quarkus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "SOFTWARE")
@NamedQueries({
        @NamedQuery(name = Software.QUERY_GET_ONE, query = "SELECT s FROM Software s WHERE s.name LIKE :name"),
        @NamedQuery(name = Software.QUERY_GET_ALL, query = "SELECT s FROM Software s ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_DESCRIPTION, query = "SELECT s FROM Software s WHERE s.description IS NOT NULL AND s.description NOT LIKE '' ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_WEBSITE, query = "SELECT s FROM Software s WHERE s.website IS NOT NULL AND s.website NOT LIKE '' ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_REPOSITORY, query = "SELECT s FROM Software s WHERE s.repository IS NOT NULL AND s.repository NOT LIKE '' ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ALL_BEING_OPEN_SOURCE, query = "SELECT s FROM Software s WHERE s.isOpenSource = true ORDER BY s.name"),
        @NamedQuery(name = Software.QUERY_GET_ALL_COUNT, query = "SELECT COUNT(s) FROM Software s"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_DESCRIPTION_COUNT, query = "SELECT COUNT(s) FROM Software s WHERE s.description IS NOT NULL AND s.description NOT LIKE ''"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_WEBSITE_COUNT, query = "SELECT COUNT(s) FROM Software s WHERE s.website IS NOT NULL AND s.website NOT LIKE ''"),
        @NamedQuery(name = Software.QUERY_GET_ALL_HAVING_REPOSITORY_COUNT, query = "SELECT COUNT(s) FROM Software s WHERE s.repository IS NOT NULL AND s.repository NOT LIKE ''"),
        @NamedQuery(name = Software.QUERY_GET_ALL_BEING_OPEN_SOURCE_COUNT, query = "SELECT COUNT(s) FROM Software s WHERE s.isOpenSource = true")
})

public class Software {
    public static final String QUERY_GET_ALL = "Software.GET.all";
    public static final String QUERY_GET_ALL_HAVING_DESCRIPTION = "Software.GET.all.having.description";
    public static final String QUERY_GET_ALL_HAVING_WEBSITE = "Software.GET.all.having.website";
    public static final String QUERY_GET_ALL_HAVING_REPOSITORY = "Software.GET.all.having.repository";
    public static final String QUERY_GET_ALL_BEING_OPEN_SOURCE = "Software.GET.all.being.openSource";
    public static final String QUERY_GET_ALL_COUNT = "Software.GET.all.count()";
    public static final String QUERY_GET_ALL_HAVING_DESCRIPTION_COUNT = "Software.GET.all.having.description.count()";
    public static final String QUERY_GET_ALL_HAVING_WEBSITE_COUNT = "Software.GET.all.having.website.count()";
    public static final String QUERY_GET_ALL_HAVING_REPOSITORY_COUNT = "Software.GET.all.having.repository.count()";
    public static final String QUERY_GET_ALL_BEING_OPEN_SOURCE_COUNT = "Software.GET.all.being.openSource.count()";
    public static final String QUERY_GET_ONE = "Software.GET.one";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String website;
    private String repository;
    @Column(name = "is_open_source")
    private boolean isOpenSource;
    @OneToMany(mappedBy = "software")
    @JsonIgnoreProperties({"software"})
    private List<Rating> ratings = new LinkedList<>();

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

    public void update(Software software) {
        this.setName(software.name);
        this.setDescription(software.description);
        this.setWebsite(software.website);
        this.setRepository(software.repository);
        this.setIsOpenSource(software.isOpenSource);
    }

    public long getId() {
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
            if (website != null) throw new IllegalArgumentException("Website url is not a valid url!");
        }
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        if (repository != null && isValidLink(repository)) {
            this.repository = repository;
        } else {
            if (repository != null) throw new IllegalArgumentException("Repository url is not a valid url!");
        }
    }

    public boolean isOpenSource() {
        return isOpenSource;
    }

    public void setIsOpenSource(boolean isOpenSource) {
        this.isOpenSource = isOpenSource;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}