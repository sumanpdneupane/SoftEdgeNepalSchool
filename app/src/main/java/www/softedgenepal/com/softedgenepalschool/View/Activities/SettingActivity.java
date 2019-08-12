package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.ReportCardSetting;
import www.softedgenepal.com.softedgenepalschool.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private View backpress;
    private MaterialRippleLayout setting_done;
    public Switch aSwitch;
    public TextView settingLanguageText, settingLanguageTitle;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPercentage, radioButtonGPA, radioButtonBoth;
    private RadioButton radioButton;

    private LanguageSetting languageSetting;
    String lang;

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSetting(this);
        lang = languageSetting.loadLanguage();

        setContentView(R.layout.activity_setting);

        //casting
        casting();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        switchUpdate(lang);
        reAnimateView();
        forLanguageSetting();
        forReportCardRadioGoupAndButton();
        forSaveSetting();
    }

    private void forLanguageSetting() {
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo work here
                if (!aSwitch.isChecked()) {
                    languageSetting.changeLanguage("en");
                    switchUpdate("en");
                } else {
                    languageSetting.changeLanguage("ne");
                    switchUpdate("ne");
                }
                reAnimateView();
                onRestart();
            }
        });
    }

    private void forReportCardRadioGoupAndButton() {
        radioButtonPercentage.setOnClickListener(this);
        radioButtonGPA.setOnClickListener(this);
        radioButtonBoth.setOnClickListener(this);

        loadReportCardSetting();
    }

    private void forSaveSetting() {
        setting_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReportCardSetting();
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void casting() {
        backpress = findViewById(R.id.setting_bt_close);
        aSwitch = findViewById(R.id.settingLanguageSwitch);
        settingLanguageText = findViewById(R.id.settingLanguageText);
        settingLanguageTitle = findViewById(R.id.settingLanguageTitle);
        radioGroup = findViewById(R.id.reportCard_radioGroup);
        radioButtonPercentage = findViewById(R.id.radioButton_percentage);
        radioButtonGPA = findViewById(R.id.radioButton_gpa);
        radioButtonBoth = findViewById(R.id.radioButton_both);
        setting_done = findViewById(R.id.setting_done);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void switchUpdate(String b) {
        if (b.equals("en")) {
            aSwitch.setChecked(false);
        } else if (b.equals("ne")) {
            aSwitch.setChecked(true);
        }
    }

    public void reAnimateView() {
        settingLanguageText.setText(getResources().getString(R.string.language_type));
        settingLanguageTitle.setText(getResources().getString(R.string.language_title));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.radioButton_percentage || id == R.id.radioButton_gpa || id == R.id.radioButton_both) {
            int checkId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(checkId);
            //showMessage(radioButton.getText().toString());
        }
    }

    private void loadReportCardSetting() {
        String type = ReportCardSetting.getCardFormate(this);
        if(type.equals("No name defined")|| type.equals(getString(R.string.both))){
            radioGroup.check(R.id.radioButton_both);
        }else if(type.equals(getString(R.string.percentage))){
            radioGroup.check(R.id.radioButton_percentage);
        }else if(type.equals(getString(R.string.gpa))){
            radioGroup.check(R.id.radioButton_gpa);
        }
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private void saveReportCardSetting() {
        String type = radioButton.getText().toString().trim();
        ReportCardSetting.setCardFormate(this, type);
        showMessage(type);
    }
}
