package com.sonusourav.sadak.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.sonusourav.sadak.R;
import com.sonusourav.sadak.Utils.CommonFunctions;
import com.sonusourav.sadak.Utils.Constants;
import com.sonusourav.sadak.Utils.PreferenceManager;
import com.sonusourav.sadak.adapter.PaginationListener;
import com.sonusourav.sadak.adapter.PostAdapter;
import com.sonusourav.sadak.api.ApiClient;
import com.sonusourav.sadak.api.ApiInterface;
import com.sonusourav.sadak.api.ApiResponse;
import com.sonusourav.sadak.api.PostResponse;
import com.sonusourav.sadak.dao.postContractor;
import com.sonusourav.sadak.dao.postDao;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private PostAdapter postAdapter;
    private NestedScrollView nestedScrollView;
    private List<postDao> postDaoList;
    private ApiInterface apiService;
    private ShimmerFrameLayout shimmerFrameLayout;
    private FloatingActionButton fab;
    private String TAG=HomeActivity.class.getSimpleName();
    private Uri selectedImage;
    private ProgressDialog mProgressDialog;
    private Button submit,addButton;
    private PreferenceManager homePref;
    private int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.include_home).setVisibility(View.VISIBLE);

        CommonFunctions.setUser(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        homeInit();
        postDaoList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postDaoList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        initScrollListener(mLayoutManager);
        postAdapter.addLoading();
        swipeRefresh.setEnabled(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                if (!checkPermission()) {
                    requestPermission();
                    addPost();
                }
            }
        });

        apiService = ApiClient.getClient().create(ApiInterface.class);
        fetchPosts();
    }


    private void addPost(){

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.add_post);
        dialog.setTitle(" Add new Post ");
        dialog.setCancelable(true);

        final TextInputEditText docTitle = dialog.findViewById(R.id.add_title1);
        final TextInputEditText length = dialog.findViewById(R.id.add_length1);
        final TextInputEditText width = dialog.findViewById(R.id.add_width1);
        final TextInputEditText material = dialog.findViewById(R.id.add_material1);
        final TextInputEditText location = dialog.findViewById(R.id.add_location1);
        submit = dialog.findViewById(R.id.post_upload);
         addButton = dialog.findViewById(R.id.post_add_btn);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCancelable(false);

                String title,len,wid,mat,loc;
                title=docTitle.getText().toString().trim();
                len = length.getText().toString().trim();
                wid = width.getText().toString();
                mat=material.getText().toString().trim();
                loc=location.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please fill Title ", Toast.LENGTH_SHORT).show();
                    docTitle.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }
                if(len.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill the length of the road ", Toast.LENGTH_SHORT).show();
                    length.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                if(wid.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill width of the road", Toast.LENGTH_SHORT).show();
                    width.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                if(mat.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill materials used", Toast.LENGTH_SHORT).show();
                    material.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }
                if(loc.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill width of the road", Toast.LENGTH_SHORT).show();
                    location.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                if(selectedImage!=null){
                    showProgressDialog();
                    dialog.dismiss();
                    postContractor newDoc=new postContractor(title,len,wid,mat,loc);

                    //checking the permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + getPackageName()));
                        finish();
                        startActivity(intent);
                        return;
                    }


                    uploadFile(selectedImage,newDoc);
                }

            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            submit.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void uploadFile(Uri fileUri,postContractor newPost) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), newPost.getDocTitle());
        RequestBody length = RequestBody.create(MediaType.parse("text/plain"), newPost.getLength());
        RequestBody width = RequestBody.create(MediaType.parse("text/plain"), newPost.getWidth());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), newPost.getLocation());
        RequestBody material = RequestBody.create(MediaType.parse("text/plain"), newPost.getMaterial());

        MultipartBody.Part docFile= MultipartBody.Part.createFormData("document", file.getName(), requestFile);
        //creating a call and calling the upload image method
        Call<PostResponse> call = apiService.addPosts(title, length, width, material, location,docFile,
            "Bearer " + homePref.getUserId());

        //finally performing the call
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostResponse> call,
                @NonNull Response<PostResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Post successfully added",
                        Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"postUploaded");
                    hideProgressDialog();
                }else{
                    Log.d(TAG,"failed to upload");
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PostResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG,t.toString());
                hideProgressDialog();
            }
        });
    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            mProgressDialog.setMessage("Uploading ....");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()){
                moveTaskToBack(true);            }
            else{
                Toast.makeText(getBaseContext(), "Press twice to exit", Toast.LENGTH_SHORT).show();
                back_pressed = System.currentTimeMillis();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return CommonFunctions.navigationItemSelect(item, this);

    }

    private void homeInit(){

        recyclerView=findViewById(R.id.post_recycler_view);
        swipeRefresh=findViewById(R.id.swipeRefresh);
        nestedScrollView=findViewById(R.id.nested_scroll_view);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        fab=findViewById(R.id.home_fab);
        homePref=new PreferenceManager(this);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
        apiService = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiInterface.class);
    }

    private void initScrollListener(LinearLayoutManager layoutManager) {

        nestedScrollView.setOnScrollChangeListener(
            new PaginationListener(layoutManager) {
                @Override
                protected void loadMoreItems() {
                    postAdapter.addLoading();
                    fetchPosts();
                }
                @Override
                public boolean isLoading() {
                    return postAdapter.isLoading();
                }
            });
    }

    private void fetchPosts(){

        Call<ApiResponse> call = apiService.getPosts();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call,
                @NonNull retrofit2.Response<ApiResponse> response) {
                if (response.body() != null) {
                    postAdapter.removeLoading();
                    if (swipeRefresh.isRefreshing()) {
                        postDaoList.clear();
                        postAdapter.addItems(Arrays.asList(response.body().getPosts()));
                        swipeRefresh.setRefreshing(false);
                    } else {
                        postDaoList.clear();
                        postDaoList.addAll(Arrays.asList(response.body().getPosts()));
                        postAdapter.notifyDataSetChanged();
                    }
                }
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                swipeRefresh.setEnabled(true);
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
                postAdapter.removeLoading();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                shimmerFrameLayout.setVisibility(View.GONE);

            }
        });
    }


    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return (result1 == PackageManager.PERMISSION_GRANTED
            && result2 == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[] { READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION },
            PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
        int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean locationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (storageAccepted && locationAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission granted",
                        Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied",
                        Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("Please allow to read your storage",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[] { READ_EXTERNAL_STORAGE },
                                            PERMISSION_REQUEST_CODE);
                                    }
                                });
                        } else if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel("Please allow to use camera",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[] { ACCESS_FINE_LOCATION },
                                            PERMISSION_REQUEST_CODE);
                                    }
                                });
                        } else {
                            showMessageOKCancel("Please allow to use camera and mic",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[] { READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION },
                                            PERMISSION_REQUEST_CODE);
                                    }
                                });
                        }
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show();
    }

}
