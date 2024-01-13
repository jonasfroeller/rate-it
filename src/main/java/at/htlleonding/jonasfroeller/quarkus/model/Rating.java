package at.htlleonding.jonasfroeller.quarkus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "RATING")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private RatingType rating;
    @ManyToOne
    @JsonIgnoreProperties({"ratings"})
    private Software software;

    public RatingType getRating() {
        return rating;
    }

    public void setRating(RatingType rating) {
        this.rating = rating;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public int getId() {
        return id;
    }
}
