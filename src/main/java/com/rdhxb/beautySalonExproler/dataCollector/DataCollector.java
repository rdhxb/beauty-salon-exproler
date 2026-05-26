package com.rdhxb.beautySalonExproler.dataCollector;


import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataCollector {

    private static final String APIKEY = System.getenv("API_KEY");

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SalonService salonService;


    private final List<String> queryList = List.of(
            "{\"textQuery\": \"salon beauty Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon kosmetyczny Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon fryzjerski Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"manicure Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"barber shop Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"spa Warszawa\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon urody Mokotów\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon urody Śródmieście\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon urody Wola\", \"maxResultCount\": 20}",
            "{\"textQuery\": \"salon urody Ursynów\", \"maxResultCount\": 20}"
    );


    public List<Salon> collectAndSave() throws IOException, InterruptedException {

        List<Salon> salons = new ArrayList<>();
//        to get just unique id's
        Set<String> seenIds = new HashSet<>();

        for (String query: queryList) {


            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://places.googleapis.com/v1/places:searchText"))
                    .header("Content-Type", "application/json")
                    .header("X-Goog-Api-Key", APIKEY)
                    .header("X-Goog-FieldMask", "places.id")
                    .POST(HttpRequest.BodyPublishers.ofString(query))
                    .build();

            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            JsonNode places = mapper.readTree(response.body()).path("places");


            for (JsonNode p : places) {
                String placeId = p.path("id").asString();

//                if id was seen continue
                if (!seenIds.add(placeId)) continue;

                HttpRequest requestDetails = HttpRequest.newBuilder()
                        .uri(URI.create("https://places.googleapis.com/v1/places/" + placeId + "?languageCode=pl"))
                        .header("X-Goog-Api-Key", APIKEY)
                        .header("X-Goog-FieldMask", "displayName,formattedAddress,addressComponents," +
                                "nationalPhoneNumber,websiteUri,rating,userRatingCount," +
                                "priceLevel,editorialSummary,priceRange,currentOpeningHours")
                        .GET()
                        .build();

                HttpResponse<String> responseDetails = client.send(requestDetails, HttpResponse.BodyHandlers.ofString());
                JsonNode placeDetails = mapper.readTree(responseDetails.body());

                Salon s = new Salon(
                        null,
                        placeDetails.path("displayName").path("text").asString(),
                        placeDetails.path("formattedAddress").asString(),
                        getDistrict(placeDetails),
                        placeDetails.path("nationalPhoneNumber").asString("brak"),
                        placeDetails.path("websiteUri").asString("brak"),
                        placeDetails.path("rating").asDouble(0),
                        placeDetails.path("userRatingCount").asInt(0),
                        placeDetails.path("currentOpeningHours").path("openNow").asBoolean(),
                        String.join(" | ", getOpeningHours(placeDetails))
                );
                salons.add(s);
            }
        }
        salonService.saveData(salons);
        return salons;
    }

    private String getDistrict(JsonNode responseDetails) {
        for (JsonNode detail : responseDetails.path("addressComponents")) {
            for (JsonNode type : detail.path("types")) {
                if (type.asString().equals("sublocality")) {
                    return detail.path("longText").asString();
                }
            }
        }
        return "";
    }

    private List<String> getOpeningHours(JsonNode placeDetails) {
        List<String> hours = new ArrayList<>();
        for (JsonNode day : placeDetails.path("currentOpeningHours").path("weekdayDescriptions")) {
            hours.add(day.asString());
        }
        return hours;
    }
}