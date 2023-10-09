package at.htlleonding.jonasfroeller.quarkus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    public int id;
    public int softwareId;
    public RatingType rating;
}
