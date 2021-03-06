package ua.nure.easygo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import easygo.nure.ua.easygoclient.BR;
import easygo.nure.ua.easygoclient.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.nure.easygo.adapters.BaseBindableAdapter;
import ua.nure.easygo.model.Map;
import ua.nure.easygo.rest.EasyGoService;
import ua.nure.easygo.rest.RestService;
import ua.nure.easygo.utils.ArrayUtil;

/**
 * Activity for viewing and selecting map from list.
 * <p>
 * EXTRA_MAP_ITEMS specify array of maps ids being displayed. If it is not specified, full maps list will be displayed.
 */
public class MapsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_MAP_ID = "map_id";
    private static final int REQUEST_CREATE_MAP = 1;
    private static final String EXTRA_MAP_ITEMS = "map_items", EXTRA_TITLE = "title", EXTRA_EDITING = "editing";
    EasyGoService service;
    ListView listView;
    ListAdapter adapter;
    private boolean editing, searching;

    public static void startWithFullMapList(Activity context, int requestCode, String title, boolean editing) {

        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_EDITING, editing);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startWithCertainMapList(Activity context, int requestCode, long[] mapIds, String title, boolean editing) {
        Intent i = new Intent(context, MapsActivity.class);
        i.putExtra(EXTRA_TITLE, title);
        i.putExtra(EXTRA_EDITING, editing);
        i.putExtra(MapsActivity.EXTRA_MAP_ITEMS, mapIds);

        context.startActivityForResult(i, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        editing = getIntent().getBooleanExtra(EXTRA_EDITING, true);

        listView = (ListView) findViewById(R.id.list_maps);

        service = RestService.get();


        service.getMaps().enqueue(new Callback<List<Map>>() {
            @Override
            public void onResponse(Call<List<Map>> call, Response<List<Map>> response) {
                List<Map> maps = response.body();
                if (getIntent().hasExtra(EXTRA_MAP_ITEMS)) {
                    long[] ids = getIntent().getLongArrayExtra(EXTRA_MAP_ITEMS);
                    maps = new LinkedList<>();
                    for (Map m : response.body()) {
                        if (ArrayUtil.contains(ids, m.mapId)) {
                            maps.add(m);
                        }
                    }
                }

                adapter = new BaseBindableAdapter<Map>(MapsActivity.this, maps, R.layout.map_item, BR.map) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        if (!editing) {
                            v.findViewById(R.id.add).setVisibility(View.INVISIBLE);
                        }
                        return v;
                    }
                };


                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Map>> call, Throwable t) {
                //TODO: Add error handling
            }
        });

        listView.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        getSupportActionBar().setTitle(title == null ? "Maps list" : title);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent res = new Intent();
        res.putExtra(EXTRA_MAP_ID, ((Map) adapter.getItem(position)).mapId);
        setResult(RESULT_OK, res);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        if (!editing) {
            menu.findItem(R.id.add).setVisible(false);
        }
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (!searching) {
            menu.findItem(R.id.action_search).setVisible(false);
        } else {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    service.searchMaps(query).enqueue(new Callback<List<Map>>() {
                        @Override
                        public void onResponse(Call<List<Map>> call, Response<List<Map>> response) {
                            List<Map> maps = response.body();

                            adapter = new BaseBindableAdapter<Map>(MapsActivity.this, maps, R.layout.map_item, BR.map) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);
                                    if (!editing) {
                                        v.findViewById(R.id.add).setVisibility(View.INVISIBLE);
                                    }
                                    return v;
                                }
                            };
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Map>> call, Throwable t) {
                            //TODO: Add error handling
                        }
                    });
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_MAP) {
            service.getMaps().enqueue(new Callback<List<Map>>() {
                @Override
                public void onResponse(Call<List<Map>> call, Response<List<Map>> response) {

                    listView.setAdapter(new BaseBindableAdapter<>(MapsActivity.this, response.body(), R.layout.map_item, BR.map));
                }

                @Override
                public void onFailure(Call<List<Map>> call, Throwable t) {
                    //TODO: Add error handling
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(this, MapInfoActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_MAP);
        }
        return super.onOptionsItemSelected(item);
    }
}
