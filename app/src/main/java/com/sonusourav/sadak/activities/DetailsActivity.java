package com.sonusourav.sadak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.sonusourav.sadak.R;
import com.sonusourav.sadak.dao.postDao;

public class DetailsActivity extends AppCompatActivity {

  private ImageView profilePic,postImage;
  private TextView userName,dateofPost,caption,location,length,width,loc,material,report;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.post_details);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    setSupportActionBar(toolbar);

    Intent i = getIntent();
    postDao post = (postDao)i.getSerializableExtra("postObject");

    init();
    if(post.getProfileUrl()!=null && !post.getProfileUrl().isEmpty()){
      Glide
          .with(this)
          .load(post.getProfileUrl())
          .placeholder(R.drawable.image_profile_pic)
          .centerCrop()
          .into(profilePic);
    }

    if(post.getRoadPic()!=null && !post.getRoadPic().isEmpty()){
      Glide
          .with(this)
          .load(post.getRoadPic())
          .placeholder(R.drawable.image_profile)
          .centerCrop()
          .into(postImage);
    }

    userName.setText(post.getBy());
    dateofPost.setText(post.getDate());
    caption.setText(post.getCaption());
    location.setText(post.getLocation());
    length.setText(post.getLength());
    width.setText(post.getWidth());
    material.setText(post.getMaterial());
    loc.setText(post.getLocation());

    report.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.details_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
            .show();
        break;
      default:
        break;
    }

    return true;
  }

  public void init(){
    profilePic=findViewById(R.id.post_details_pic);
    postImage=findViewById(R.id.post_details_image);
    userName=findViewById(R.id.post_details_name);
    dateofPost=findViewById(R.id.post_details_date);
    caption=findViewById(R.id.post_details_caption);
    location=findViewById(R.id.post_details_location);
    length=findViewById(R.id.post_details_length);
    width=findViewById(R.id.post_details_width);
    material=findViewById(R.id.post_details_materials);
    report=findViewById(R.id.report_tv);
    loc=findViewById(R.id.post_details_location_icon);
  }
}
