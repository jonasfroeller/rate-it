package at.htlleonding.jonasfroeller.quarkus.boundary;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import at.htlleonding.jonasfroeller.quarkus.repository.SoftwareRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/software")
public class SoftwareResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public boolean addSoftware(Software software) {
        return SoftwareRepository.getInstance().addSoftware(software);
    }

    @POST
    @Path("/remove/{softwareName}")
    public boolean removeSoftware(@PathParam("softwareName") String name) {
        return SoftwareRepository.getInstance().removeSoftware(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{softwareName}")
    public Software getSoftware(@PathParam("softwareName") String name) {
        return SoftwareRepository.getInstance().getSoftware(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Software> root() {
        return SoftwareRepository.getInstance().getEverySoftware();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/asList")
    public List<Software> listSoftware() {
        return SoftwareRepository.getInstance().getEverySoftware();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount")
    public int getAmount() {
        return SoftwareRepository.getInstance().getAmount();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amountOpenSource")
    public long getAmountOpenSource() {
        return SoftwareRepository.getInstance().getAmountOpenSource();
    }
}
