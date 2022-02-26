package serposcope.controllers.google.entity;

import java.util.ArrayList;

public class KeywordSearchResults {

    public String targetRank;
    public String targetName;
    public String targetURL;

    public ArrayList<KeywordsSearchResultsOnly> results;

    public KeywordSearchResults() {
    }

    public KeywordSearchResults(String targetRank, String targetName, String targetURL) {
        this.targetRank = targetRank;
        this.targetName = targetName;
        this.targetURL = targetURL;
    }

    public KeywordSearchResults(String targetRank, String targetName, String targetURL, ArrayList<KeywordsSearchResultsOnly> results) {
        this.targetRank = targetRank;
        this.targetName = targetName;
        this.targetURL = targetURL;
        this.results = results;
    }

    public String getTargetRank() {
        return targetRank;
    }

    public void setTargetRank(String targetRank) {
        this.targetRank = targetRank;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public ArrayList<KeywordsSearchResultsOnly> getResults() {
        return results;
    }

    public void setResults(ArrayList<KeywordsSearchResultsOnly> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "KeywordSearchResults{" +
                "targetRank='" + targetRank + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetURL='" + targetURL + '\'' +
                ", results=" + results +
                '}';
    }
}
