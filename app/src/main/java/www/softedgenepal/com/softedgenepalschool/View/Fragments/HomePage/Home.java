package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialog.AlertDialog;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.SchoolHomePage;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements Toolbar.OnMenuItemClickListener {
    StudentHomePage studentHomePage;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        if(userType.equals("School")){
            view = inflater.inflate(R.layout.school, container, false);
            new SchoolHomePage(getActivity(),view).setView();
            showMessage("School");
        }else if(userType.equals("Teacher")){
            view = inflater.inflate(R.layout.fragment_home, container, false);
            showMessage("Teacher");
        }else if(userType.equals("Student")){
            view = inflater.inflate(R.layout.user_profile, container, false);
            //setHasOptionsMenu(true);
            onCreateOptionsMenu(view);

            //getActivity().startActivity(new Intent(getContext(), ShowAllLeaveApplicationDetailViewActivity.class));

            studentHomePage = new StudentHomePage(getActivity(), view);
            studentHomePage.setView();
            showMessage("Student");
        }
        return view;
    }

    public void onCreateOptionsMenu(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_profile);
        toolbar.inflateMenu(R.menu.menu);

        if(userType.equals("school")){
            toolbar.getMenu().setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("parent")){
            toolbar.getMenu().setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("student")){
            toolbar.getMenu().setGroupVisible(R.id.group_for_student, true);
        }

        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(userType.equals("Student")){
            studentHomePage.siblingMenu(menuItem);
        }
        return true;
    }

    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
