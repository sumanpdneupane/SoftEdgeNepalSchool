package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Assignment;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.AssignmentPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;

public class AssignmentModel implements AssignmentContractor.Model {
    private AssignmentPresenter assignmentPresenter;
    private List<AssignmentCache> assignmentCacheList;

    public AssignmentModel(AssignmentPresenter assignmentPresenter) {
        this.assignmentPresenter = assignmentPresenter;
    }

    public void fetchDataFromServer(Map<String, String> params){
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online(params);
        }else {
            //todo go offline
            offline();
        }
    }

    private void online(Map<String, String> params) {
        String Url = new URL().assignmentUrl()+
                "?dateFrom="+params.get("From")+
                "&dateTo="+params.get("To")+
                "&studentId="+params.get("studentId");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.Assignment);
                preference.storeData(response.toString());
                offline();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setMessage(error.toString());
                offline();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(getContext());
        preference.setType(preference.Assignment);
        String data = preference.getData();

        if(data==null){
            setMessage("There is not any assignment.");
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
           // parseJson(response);
            setJsonDataToView(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(JSONObject response){
        assignmentCacheList = new ArrayList<>();

        try {
            if(response.getString("Status").equals("true")){
                if(response.getString("Response").equals("Success")) {
                    JSONArray dataArray = response.getJSONArray("Data");
                    if(!dataArray.toString().equals("[]")) {
                        if (dataArray.length() >= 0) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONArray array = dataArray.getJSONArray(i);
                                for(int j=0; j<array.length(); j++) {
                                    JSONObject data = array.getJSONObject(j);
                                    if (!data.toString().equals("")) {
                                        String Class = data.getString("Class");
                                        String ClassConfigurationCode = data.getString("ClassConfigurationCode");
                                        String Homework = data.getString("Homework");
                                        String CreateDate = "";
                                        if(!data.getString("Date").equals("null")) {
                                            CreateDate = DateTime.splitDateOrTime(data.getString("Date"), "date");
                                        }
                                        String Deadline = "";
                                        if(!data.getString("Deadline").equals("null")) {
                                            Deadline = DateTime.splitDateOrTime(data.getString("Deadline"), "date");
                                        }
                                        String SubjectNameEng = data.getString("SubjectNameEng");
                                        String SubjectCode = data.getString("SubjectCode");
                                        String ImageUrl = data.getString("ImageUrl");
                                        String FontType = "";

                                        assignmentCacheList.add(new AssignmentCache(Class, ClassConfigurationCode, Homework, CreateDate, Deadline, SubjectNameEng, SubjectCode, ImageUrl, FontType));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            setDataToView(response);
        }catch (Exception e){
            setMessage(e.getMessage());
            setDataToView(response);
        }
    }
    private void setJsonDataToView(JSONObject response) {
        assignmentPresenter.setJsonData(response);

    }

    private void setDataToView(JSONObject response) {
        assignmentPresenter.setData(assignmentCacheList);
    }

    private Context getContext() {
        return assignmentPresenter.getCalContext();
    }

    @Override
    public void setMessage(String message) {
        assignmentPresenter.setMessage(message);
    }
}