package serposcope.controllers.google.entity;


import java.util.ArrayList;

public class KeywordsMongoSystem {

    public String keyword;

    public ArrayList<Long> postIds;
    public ArrayList<String> industryCategories;
    public String searchVolume;
    public Object topRanking;

    public KeywordsMongoSystem() {
    }

    public KeywordsMongoSystem(String keyword, ArrayList<String> industryCategories) {
        this.keyword = keyword;
        this.industryCategories = industryCategories;
    }

    public KeywordsMongoSystem(String keyword, ArrayList<Long> postIds, ArrayList<String> industryCategories, String searchVolume) {
        this.keyword = keyword;
        this.postIds = postIds;
        this.industryCategories = industryCategories;
        this.searchVolume = searchVolume;
    }

    public KeywordsMongoSystem(String keyword, ArrayList<Long> postIds, ArrayList<String> industryCategories, String searchVolume, Object topRanking) {
        this.keyword = keyword;
        this.postIds = postIds;
        this.industryCategories = industryCategories;
        this.searchVolume = searchVolume;
        this.topRanking = topRanking;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArrayList<Long> getPostIds() {
        return postIds;
    }

    public void setPostIds(ArrayList<Long> postIds) {
        this.postIds = postIds;
    }

    public ArrayList<String> getIndustryCategories() {
        return industryCategories;
    }

    public void setIndustryCategories(ArrayList<String> industryCategories) {
        this.industryCategories = industryCategories;
    }

    public String getSearchVolume() {
        return searchVolume;
    }

    public void setSearchVolume(String searchVolume) {
        this.searchVolume = searchVolume;
    }

    @Override
    public String toString() {
        return "KeywordsMongoSystem{" +
                "keyword='" + keyword + '\'' +
                ", postIds=" + postIds +
                ", industryCategories=" + industryCategories +
                ", searchVolume='" + searchVolume + '\'' +
                ", topRanking=" + topRanking +
                '}';
    }
}
