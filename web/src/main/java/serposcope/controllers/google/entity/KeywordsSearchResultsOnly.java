package serposcope.controllers.google.entity;

public class KeywordsSearchResultsOnly {

    public String url;
    public Integer position;

    public KeywordsSearchResultsOnly() {
    }

    public KeywordsSearchResultsOnly(String url, Integer position) {
        this.url = url;
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "KeywordsSearchResultsOnly{" +
                "url='" + url + '\'' +
                ", position=" + position +
                '}';
    }
}
