package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication.GetAllUserLeaveApplication;

public class ParseShowAllLeaveApplication {
    private GetAllUserLeaveApplication getAllUserLeaveApplication;
    private Context context;
    private List<LeaveApplicationDataCache> leaveApplicationDataCacheList = null;

    public ParseShowAllLeaveApplication(GetAllUserLeaveApplication getAllUserLeaveApplication, Context context) {
        this.getAllUserLeaveApplication=getAllUserLeaveApplication;
        this.context=context;
    }

    public void parse(JSONObject response) {
        try {
            if (response.getString("Status").equals("true")){
                if(response.getString("Response").equals("Success")){
                    //getAllUserLeaveApplication.setMessage(response.getString("Response"));
                    //todo parse
                    leaveApplicationDataCacheList = new ArrayList<>();

                    JSONArray data = response.getJSONArray("Data");
                    //getAllUserLeaveApplication.setMessage(data.toString());
                    if(data.length()>0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);

                            String SystemCode = object.getString("SystemCode");
                            String StudentId = object.getString("StudentId");
                            String Subject = object.getString("Subject");
                            String From = object.getString("From");
                            String To = object.getString("To");
                            String Message = object.getString("Message");
                            String CreatedDate = object.getString("CreatedDate");
                            String IsActive = object.getString("IsActive");

                            leaveApplicationDataCacheList.add(new LeaveApplicationDataCache(SystemCode, StudentId, Subject, Message, splitDate(From), splitDate(To), splitDate(CreatedDate), IsActive));
                        }

                        getAllUserLeaveApplication.setData(leaveApplicationDataCacheList);
                    }else {
                        getProgressBarInVisibility();
                        setMessageInTextView("You have not taken leave yet.");
                    }
                }else {
                    setMessage(response.getString("Response"));
                    getProgressBarInVisibility();
                }
            }
        }catch (Exception e){
            setMessage(e.getMessage());
            getProgressBarInVisibility();
        }
    }

    private String splitDate(String date){
        //removing created time from created date
        String split[] = date.split("T");
        return split[0];
    }

    private void setMessage(String message){
        getAllUserLeaveApplication.setMessage(message);
    }
    private void setMessageInTextView(String message){
        getAllUserLeaveApplication.setMessageInTextView(message);
    }
    private void getProgressBarInVisibility(){
        getAllUserLeaveApplication.leaveApplicationPresenter.getProgressBarInVisibility();
    }
}