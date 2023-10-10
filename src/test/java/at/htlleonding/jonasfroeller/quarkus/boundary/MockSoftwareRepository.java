package at.htlleonding.jonasfroeller.quarkus.boundary;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import at.htlleonding.jonasfroeller.quarkus.repository.SoftwareRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
@Priority(999)
public class MockSoftwareRepository extends SoftwareRepository {
    @PostConstruct
    public void init() {
        this.addSoftware(new Software("Svelte", "cybernetically enhanced web apps", "https://svelte.dev", "https://github.com/sveltejs/svelte", true));
        this.addSoftware(new Software("SvelteKit", "web development, streamlined", "https://kit.svelte.dev", "https://github.com/sveltejs/kit", true));
        this.addSoftware(new Software("React", "The library for web and native user interfaces", "https://react.dev/", "https://github.com/facebook/react", true));
        this.addSoftware(new Software("Vue", "The Progressiv JS Framework", "https://vuejs.org/", "https://github.com/vuejs/vue", true));
    }
}
