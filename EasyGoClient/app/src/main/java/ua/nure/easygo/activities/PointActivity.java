package ua.nure.easygo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.gms.maps.model.LatLng;

import easygo.nure.ua.easygoclient.R;
import easygo.nure.ua.easygoclient.databinding.ActivityPointBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.nure.easygo.adapters.PointAttrAdapter;
import ua.nure.easygo.model.Map;
import ua.nure.easygo.model.Point;
import ua.nure.easygo.rest.EasyGoService;
import ua.nure.easygo.rest.RestService;

public class PointActivity extends AppCompatActivity {

    public static final String EXTRA_POINT_ID = "point_id", EXTRA_MAP_ID = "map_id", EXTRA_LOC = "location";


    EasyGoService service;

    ActivityPointBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_point);
        final TableLayout table;
        table = (TableLayout) findViewById(R.id.attr_list);
        service = RestService.get();

        int id = getIntent().getIntExtra(EXTRA_POINT_ID, -1);

        if (id >= 0) {

            service.getPoint(id).enqueue(new Callback<Point>() {
                @Override
                public void onResponse(Call<Point> call, Response<Point> response) {

                    binding.setPoint(response.body());
                    service.getMap(response.body().mapId).enqueue(new Callback<Map>() {
                        @Override
                        public void onResponse(Call<Map> call, Response<Map> response) {
                            binding.setMap(response.body());
                        }

                        @Override
                        public void onFailure(Call<Map> call, Throwable t) {

                        }
                    });

                }

                @Override
                public void onFailure(Call<Point> call, Throwable t) {

                }
            });
        } else {
            Point p = new Point();
            LatLng location = getIntent().getParcelableExtra(EXTRA_LOC);
            p.x = (float) location.latitude;
            p.y = (float) location.longitude;
            binding.setPoint(p);
            long mapId =
                    getIntent().getLongExtra(EXTRA_MAP_ID, 0);
            service.getMap(mapId).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {


                    binding.setMap(response.body());
                    PointAttrAdapter attrAdapter = new PointAttrAdapter(PointActivity.this, response.body().mapAttributes, binding.getPoint().attributeValues, table);
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {

                }
            });
        }


        //if (p.attributeValues != null) {

/*        service.getMaps().enqueue(new Callback<MapList>() {
            @Override
            public void onResponse(Call<MapList> call, Response<MapList> response) {
                Map m = response.body().maps.get(mapId);


            }

            @Override
            public void onFailure(Call<MapList> call, Throwable t) {

            }
        });*/


        //!table.setAdapter(attrAdapter);
        //}

        setSupportActionBar(binding.toolbar2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            RestService.get().postPoint(binding.getPoint()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();


        setResult(RESULT_OK);
            /*p.mapId = m.mapId;
            p.pointId = m.points.size();
            m.points.add(p);*/

    }
}
