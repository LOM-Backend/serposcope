package serposcope.controllers.google;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.serphacker.serposcope.db.base.BaseDB;
import com.serphacker.serposcope.db.google.GoogleDB;
import com.serphacker.serposcope.models.base.Group;
import com.serphacker.serposcope.models.base.Run;
import com.serphacker.serposcope.models.base.User;
import com.serphacker.serposcope.models.google.GoogleBest;
import com.serphacker.serposcope.models.google.GoogleSerp;
import com.serphacker.serposcope.models.google.GoogleSerpEntry;
import com.serphacker.serposcope.models.google.GoogleTarget;
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
import serposcope.controllers.google.entity.KeywordsSearchResultsOnly;
import serposcope.helpers.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.serphacker.serposcope.db.base.RunDB.STATUSES_DONE;

@Singleton
public class GoogleRestController {

    @Inject
    GoogleDB googleDB;

    @Inject
    BaseDB baseDB;

    public Result exportSerpRestAPI(
            @PathParam("groupId") Integer groupId,
            @PathParam("searchId") Integer searchId,
            @Param("date") String pDate,
            @Param("targetId") Integer targetId
    ) {
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

}
