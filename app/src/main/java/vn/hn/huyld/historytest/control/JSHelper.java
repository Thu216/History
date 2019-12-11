package vn.hn.huyld.historytest.control;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.hn.huyld.historytest.R;
import vn.hn.huyld.historytest.model.DataList;
import vn.hn.huyld.historytest.model.Question;

public class JSHelper {

    private Context mContext;

    public JSHelper(Context mContext) {
        this.mContext = mContext;
    }

    private String readText(Context context, int resID) throws IOException {
        InputStream is = context.getResources().openRawResource(resID);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Question> getQuestion(String post) throws IOException {
        ArrayList<Question> questionList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DataList>>(){}.getType();
        String jQuestion = readText(mContext, R.raw.database);
        ArrayList<DataList> dataListList = gson.fromJson(jQuestion, type);
        for (int i = 0; i < dataListList.size(); i++){
            if (dataListList.get(i).mPosts.equals(post)){
                for (int j = 0; j < dataListList.get(i).mQuestions.size(); j++) {
                    Question question = new Question(
                            dataListList.get(i).mQuestions.get(j).mQues,
                            dataListList.get(i).mQuestions.get(j).mAnsA,
                            dataListList.get(i).mQuestions.get(j).mAnsB,
                            dataListList.get(i).mQuestions.get(j).mAnsC,
                            dataListList.get(i).mQuestions.get(j).mAnsD,
                            dataListList.get(i).mQuestions.get(j).mAnsTrue
                    );
                    questionList.add(question);
                }
            }
        }
        return questionList;
    }

    public ArrayList<DataList> getData(String part) throws IOException {
        ArrayList<DataList> dataList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DataList>>(){}.getType();
        String jQuestion = readText(mContext, R.raw.database);
        ArrayList<DataList> data = gson.fromJson(jQuestion, type);
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).mParts.equals(part)) {
                DataList dataItem = new DataList(
                        data.get(i).mParts,
                        data.get(i).mChapters,
                        data.get(i).mPosts,
                        data.get(i).mQuestions
                );
                dataList.add(dataItem);
            }
        }
        return dataList;
    }
}
