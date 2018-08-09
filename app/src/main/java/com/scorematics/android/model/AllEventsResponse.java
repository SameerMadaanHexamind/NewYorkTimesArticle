package com.scorematics.android.model;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AllEventsResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("copyright")
    @Expose
    public String copyright;
    @SerializedName("num_results")
    @Expose
    public Long numResults;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

    @Data
    public class MediaMetadatum {

        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("format")
        @Expose
        public String format;
        @SerializedName("height")
        @Expose
        public Long height;
        @SerializedName("width")
        @Expose
        public Long width;

    }

    @Data
    public class Medium {

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("subtype")
        @Expose
        public String subtype;
        @SerializedName("caption")
        @Expose
        public String caption;
        @SerializedName("copyright")
        @Expose
        public String copyright;
        @SerializedName("approved_for_syndication")
        @Expose
        public Long approvedForSyndication;
        @SerializedName("media-metadata")
        @Expose
        public List<MediaMetadatum> mediaMetadata = null;

    }

    @Data
    public class Result {

        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("adx_keywords")
        @Expose
        public String adxKeywords;
        @SerializedName("column")
        @Expose
        public Object column;
        @SerializedName("section")
        @Expose
        public String section;
        @SerializedName("byline")
        @Expose
        public String byline;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("abstract")
        @Expose
        public String _abstract;
        @SerializedName("published_date")
        @Expose
        public String publishedDate;
        @SerializedName("source")
        @Expose
        public String source;
        @SerializedName("id")
        @Expose
        public Long id;
        @SerializedName("asset_id")
        @Expose
        public Long assetId;
        @SerializedName("views")
        @Expose
        public Long views;
        @SerializedName("des_facet")
        @Expose
        public Object desFacet = null;
        @SerializedName("org_facet")
        @Expose
        public Object orgFacet = null;
        @SerializedName("per_facet")
        @Expose
        public Object  perFacet;


        @SerializedName("geo_facet")
        @Expose
        public Object geoFacet = null;
        @SerializedName("media")
        @Expose
        public List<Medium> media = null;

    }
}
