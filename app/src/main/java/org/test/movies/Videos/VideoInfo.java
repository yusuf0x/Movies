package org.test.movies.Videos;

import java.util.List;

public class VideoInfo {
    private Integer id;
    private List<Video> results = null;

    public VideoInfo(Integer id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public VideoInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
