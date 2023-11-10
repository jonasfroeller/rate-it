package at.htlleonding.jonasfroeller.quarkus.boundary;

import at.htlleonding.jonasfroeller.quarkus.model.Software;
import at.htlleonding.jonasfroeller.quarkus.repository.SoftwareRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/api/software")
public class SoftwareResource {
    @Context
    UriInfo uriInfo;

    @Inject
    SoftwareRepository softwareRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/add")
    public Response addSoftware(Software software) {
        Software softwareAdded = this.softwareRepository.addSoftware(software);

        UriBuilder uriBuilder = this.uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(softwareAdded.getId()));

        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/update")
    public boolean updateSoftware(Software software) {
        return this.softwareRepository.updateSoftware(software);
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/remove")
    public boolean removeSoftware(String name) {
        return this.softwareRepository.removeSoftware(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{softwareName}")
    public Software getSoftware(@PathParam("softwareName") String name) {
        return this.softwareRepository.getSoftware(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Software> root() {
        return this.softwareRepository.getEverySoftware();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/as-list")
    public List<Software> listSoftware() {
        return this.softwareRepository.getEverySoftware();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/having/description/as-list")
    public List<Software> listSoftwareHavingDescription() {
        return this.softwareRepository.getEverySoftwareHavingDescription();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/having/website/as-list")
    public List<Software> listSoftwareHavingWebsite() {
        return this.softwareRepository.getEverySoftwareHavingWebsite();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/having/repository/as-list")
    public List<Software> listSoftwareHavingRepository() {
        return this.softwareRepository.getEverySoftwareHavingRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/having/open-source/as-list")
    public List<Software> listSoftwareBeingOpenSource() {
        return this.softwareRepository.getEverySoftwareBeingOpenSource();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount")
    public int getAmount() {
        return this.softwareRepository.getAmount();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount/having/description")
    public int getAmountHavingDescription() {
        return this.softwareRepository.getAmountHavingDescription();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount/having/website")
    public int getAmountHavingWebsite() {
        return this.softwareRepository.getAmountHavingWebsite();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount/having/repository")
    public int getAmountHavingRepository() {
        return this.softwareRepository.getAmountHavingRepository();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/amount/having/open-source")
    public int getAmountOpenSource() {
        return this.softwareRepository.getAmountBeingOpenSource();
    }
}