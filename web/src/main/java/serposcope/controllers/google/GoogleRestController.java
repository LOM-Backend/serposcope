package serposcope.controllers.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.serphacker.serposcope.db.base.BaseDB;
import com.serphacker.serposcope.db.base.GroupDB;
import com.serphacker.serposcope.db.google.GoogleDB;
import com.serphacker.serposcope.models.base.Group;
import com.serphacker.serposcope.models.base.Run;
import com.serphacker.serposcope.models.base.User;
import com.serphacker.serposcope.models.google.*;
import com.serphacker.serposcope.scraper.google.GoogleCountryCode;
import com.serphacker.serposcope.scraper.google.GoogleDevice;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import ninja.session.Session;
import serposcope.controllers.AuthController;
import serposcope.controllers.HomeController;
import serposcope.controllers.google.entity.KeywordSearchResults;
import serposcope.controllers.google.entity.KeywordSearchResultsResponse;
import serposcope.controllers.google.entity.KeywordsMongoSystem;
import serposcope.controllers.google.entity.KeywordsSearchResultsOnly;
import serposcope.helpers.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static com.serphacker.serposcope.db.base.RunDB.STATUSES_DONE;

@Singleton
public class GoogleRestController {

    @Inject
    GoogleDB googleDB;

    @Inject
    BaseDB baseDB;

    @Inject
    GroupDB groupDB;

    public Result exportSerpRestAPI(
            @Param("igName") String groupName,
            @Param("keywordName") String searchKeyword,
            @Param("date") String pDate,
            @Param("lordName") String targetName
    ) {

        Group groupByName = groupDB.findByName(groupName, false);
        int groupId = groupByName.getId();
        int searchId = 0;
        List<GoogleSearch> googleSearches = googleDB.search.listByGroup(Collections.singletonList(groupByName.getId()));
        for (GoogleSearch g : googleSearches) {
            if (g.getKeyword().equals(searchKeyword)) {
                searchId = g.getId();
                break;
            }
        }
        int targetId = googleDB.target.list(Collections.singletonList(groupByName.getId()), targetName).get(0).getId();

        GoogleSerp serp = null;
        LocalDate date = null;

        KeywordSearchResults keywordSearchResults = new KeywordSearchResults();

        GoogleBest best = googleDB.rank.getBest(groupId, targetId, searchId);
        if (best != null) {
            GoogleTarget googleTarget = googleDB.target.get(targetId);

            keywordSearchResults = new KeywordSearchResults(
                    String.valueOf(best.getRank()),
                    googleTarget.getName(),
                    best.getUrl()
            );
        }

        try {
            date = LocalDate.parse(pDate);
        } catch (Exception ignored) {
        }
        if (date != null) {
            List<Run> runs = baseDB.run.findByDay(Group.Module.GOOGLE, date);
            if (!runs.isEmpty()) {
                serp = googleDB.serp.get(runs.get(0).getId(), searchId);
            }
        }

        if (serp == null) {
            return Results.ok().text().renderRaw("SERP not found");
        }

        int position = 0;
        ArrayList<KeywordsSearchResultsOnly> urls = new ArrayList<>();
        for (GoogleSerpEntry entry : serp.getEntries()) {
            ++position;
            urls.add(new KeywordsSearchResultsOnly(entry.getUrl(), position));
        }

        if (urls.isEmpty()) {
            return Results.json().render(
                    new KeywordSearchResultsResponse(
                            false,
                            "No URL's found for the given details"
                    )
            );
        } else {
            keywordSearchResults.setResults(urls);
            return Results.json().render(
                    new KeywordSearchResultsResponse(
                            true,
                            "URL's retrieved successfully",
                            keywordSearchResults
                    )
            );
        }

    }

    public Result createGoogleSearch(@Param("keywords") String keywords1) {

        try {
            System.out.println(keywords1);
            ObjectMapper objectMapper = new ObjectMapper();
            KeywordsMongoSystem keywords = objectMapper.readValue(keywords1, KeywordsMongoSystem.class);
            System.out.println(keywords.toString());

            GoogleSearch googleSearch1 = new GoogleSearch(0);
            googleSearch1.setKeyword(keywords.getKeyword());
            googleSearch1.setCountry(GoogleCountryCode.US);
            googleSearch1.setDevice(GoogleDevice.DESKTOP);

            for (String igName : keywords.getIndustryCategories()) {
                googleDB.search.insert(Collections.singletonList(googleSearch1), groupDB.findByName(igName, true).getId());
            }

            return Results.ok();
        } catch (Exception ex) {
            ex.printStackTrace();

            return Results.internalServerError();
        }

    }

}
