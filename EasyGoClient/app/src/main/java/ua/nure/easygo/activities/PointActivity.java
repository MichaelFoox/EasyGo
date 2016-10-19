package ua.nure.easygo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import easygo.nure.ua.easygoclient.R;
import easygo.nure.ua.easygoclient.databinding.ActivityPointBinding;
import ua.nure.easygo.model.Point;
import ua.nure.easygo.rest.ControllerStub;

public class PointActivity extends AppCompatActivity {

    public static final String EXTRA_POINT_ID="point_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPointBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_point);
        Point p;
        p = new ControllerStub().getMap(0).getPoints().get(1);
        binding.setPoint(p);

    }
}