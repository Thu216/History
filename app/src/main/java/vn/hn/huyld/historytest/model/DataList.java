package vn.hn.huyld.historytest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataList {
    @SerializedName("parts")
    public String mParts;

    @SerializedName("chapters")
    public String mChapters;

    @SerializedName("posts")
    public String mPosts;

    @SerializedName("questions")
    public List<Question> mQuestions;

    public DataList(String parts, String chapters, String posts, List<Question> questions) {
        mParts = parts;
        mChapters = chapters;
        mPosts = posts;
        mQuestions = questions;
    }
}
