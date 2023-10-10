package at.htlleonding.jonasfroeller.quarkus.boundary;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(SoftwareResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SoftwareResourceTest {
    @Test
    @Order(-10)
    void testIfMapContainsAddedSoftware() {
        given()
                .when().get("asList")
                .then()
                .body("size()", is(4))
                .body("name", hasItems("Svelte", "SvelteKit", "React", "Vue"));
    }

    @Test
    @Order(-5)
    void testDirectSoftwareInfoRoute() {
        String response =
                given()
                        .when().get("Vue")
                        .then()
                        .extract().body().asString();

        assertThat(response, containsString("Vue"));
    }

    @Test
    @Order(-1)
    void testDirectSoftwareInfoRouteWithNonExistingSoftware() {
        given()
                .when().get("JavaFX")
                .then().statusCode(204);
    }

    @Test
    void testAddingSoftwareHavingInvalidName() {
        JsonObject invalidSoftwareJson = Json.createObjectBuilder()
                .add("name", "")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(invalidSoftwareJson.toString())
                .when().post("/add")
                .then().statusCode(400);
    }

    @Test
    void testAddingSoftwareHavingInvalidLink() { // no tld
        JsonObject invalidLinkJson = Json.createObjectBuilder()
                .add("name", "InvalidLink")
                .add("description", "Software with invalid link")
                .add("website", "https://invalid-link")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(invalidLinkJson.toString())
                .when().post("/add")
                .then().statusCode(400);
    }

    @Test
    void testAddingDuplicateSoftware() {
        String listBeforeRequest =
                given()
                        .when().get("asList")
                        .then()
                        .extract().body().asString();

        JsonObject svelteJson = Json.createObjectBuilder()
                .add("name", "Svelte")
                .add("description", "cybernetically enhanced web apps")
                .add("website", "https://svelte.dev")
                .add("repository", "https://github.com/sveltejs/svelte")
                .add("isOpenSource", true)
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(svelteJson.toString())
                .when().post("/add")
                .then().statusCode(500);

        String listAfterRequest =
                given()
                        .when().get("asList")
                        .then()
                        .extract().body().asString();

        Assertions.assertEquals(listBeforeRequest, listAfterRequest);
    }

    @Test
    @Order(999)
    void testRemovingSoftware() {
        String listBeforeRequest = given()
                .when().get("asList")
                .then()
                .extract().body().asString();

        given()
                .when().post("/remove/React")
                .then().statusCode(200);

        String listAfterRequest = given()
                .when().get("asList")
                .then()
                .extract().body().asString();

        Assertions.assertNotEquals(listBeforeRequest, listAfterRequest);
    }
}