package com.dev.wellthytask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dev.wellthytask.adapters.WellthyAdapter;
import com.dev.wellthytask.constants.AppConstants;
import com.dev.wellthytask.database.WellthyTestDb;
import com.dev.wellthytask.models.DataModel;
import com.dev.wellthytask.networking.ApiCaller;
import com.dev.wellthytask.networking.ApiResponseFetcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiResponseFetcher{
    Context ctx = MainActivity.this;
    private RecyclerView word_meaning_list;
    WellthyTestDb db = new WellthyTestDb(ctx);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Wellthy Task");

        setLayoutReferences();

    }

    private void setLayoutReferences(){
        db.deleteAllData();
        word_meaning_list = (RecyclerView) findViewById(R.id.word_meaning_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        word_meaning_list.setLayoutManager(layoutManager);

        getDataFromServer();
    }

    private void getDataFromServer(){
        ApiCaller request = new ApiCaller(this, ctx);
        request.getData(AppConstants.DATA_URL);
    }

    @Override
    public void result(JSONObject obj) {
        if (obj==null){
         //Some Server error
        }else{
           //Parsing response here

            JSONArray parent_array = obj.optJSONArray("words");
            for (int i=0; i<parent_array.length();i++){
                JSONObject data_obj = parent_array.optJSONObject(i);
                DataModel model = new DataModel();
                model.setImage_id(data_obj.optString("id"));
                model.setWord(data_obj.optString("word"));
                model.setMeaning(data_obj.optString("meaning"));
                model.setRatio(data_obj.optString("ratio"));
                if (data_obj.optString("ratio").contains("-")){

                }else{
                    db.insertData(model);
                }

            }
            setDataInList();
        }
    }

    @Override
    public void error(String error) {
        Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show();
    }

    private void setDataInList(){
        ArrayList<DataModel> data = new ArrayList<>();
        data.clear();
        data = db.getAllData();
        if (data!=null){
            WellthyAdapter adapter = new WellthyAdapter(ctx, data);
            word_meaning_list.setAdapter(adapter);
        }else{
            Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
