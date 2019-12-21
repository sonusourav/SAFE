package com.sonusourav.sadak.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sonusourav.sadak.R;
import com.sonusourav.sadak.activities.DetailsActivity;
import com.sonusourav.sadak.dao.postDao;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private final int VIEW_TYPE_ITEM = 0;
  private final int VIEW_TYPE_LOADING = 1;
  private Context context;
  private List<postDao> postList;
  private int mCurrentPosition;
  private boolean isLoading = false;

  public PostAdapter(Context context, List<postDao> postList) {
    this.context = context;
    this.postList = postList;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEW_TYPE_ITEM:
        return new MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.post_card, parent, false));
      case VIEW_TYPE_LOADING:
        return new ProgressHolder(
            LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

    if (getItemViewType(position) == VIEW_TYPE_LOADING) {
      mCurrentPosition = position;
      ProgressHolder loadingViewHolder = (ProgressHolder) holder;
      loadingViewHolder.progressBar.setIndeterminate(true);
    } else {
      MyViewHolder myViewHolder = (MyViewHolder) holder;
      final postDao post = postList.get(position);
      mCurrentPosition = position;

      if(post!=null){
        myViewHolder.userName.setText(post.getBy());
        myViewHolder.dop.setText(post.getDate());
        myViewHolder.caption.setText(post.getCaption());
        myViewHolder.location.setText(post.getLocation());
        myViewHolder.downVotes.setText(Integer.toString(post.getDownvotes()));
        myViewHolder.upVotes.setText(Integer.toString(post.getUpvotes()));
        myViewHolder.comments.setText(Integer.toString(post.getCommentsList()));

        if(!post.getProfileUrl().isEmpty()){
          Glide
              .with(context)
              .load(post.getProfileUrl())
              .placeholder(R.drawable.image_profile_pic)
              .centerCrop()
              .into(myViewHolder.profilePic);
        }

        if(!post.getRoadPic().isEmpty()){
          Glide
              .with(context)
              .load(post.getRoadPic())
              .placeholder(R.drawable.image_profile)
              .centerCrop()
              .into(myViewHolder.imagePost);
        }

        myViewHolder.imagePost.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            Intent intent=new Intent(context,DetailsActivity.class);
            intent.putExtra("postObject", post);
            context.startActivity(intent);
          }
        });
      }
    }
  }

  public void addItems(List<postDao> questions) {
    clear();
    postList.addAll(questions);
    notifyDataSetChanged();
  }

  public void addLoading() {
    isLoading = true;
    postList.add(new postDao());
    notifyItemInserted(postList.size() - 1);
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void removeLoading() {
    int position = getCurrentPosition();
    if (getItemViewType(position) == VIEW_TYPE_LOADING) {
      postList.remove(position);
      notifyItemRemoved(position);
    }
    isLoading = false;
  }

  public void clear() {
    postList.clear();
    notifyDataSetChanged();
  }

  private postDao getItem(int position) {
    return postList.get(position);
  }

  private int getCurrentPosition() {
    return mCurrentPosition;
  }

  @Override
  public int getItemViewType(int position) {
    if (isLoading) {
      return position == postList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    } else {
      return VIEW_TYPE_ITEM;
    }
  }

  @Override
  public int getItemCount() {
    return postList == null ? 0 : postList.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView userName,dop,caption,location,upVotes,downVotes,comments;
    ImageView profilePic,imagePost;
    CardView cardView;
    ImageView upvoteIcon,downvoteIcon,commentsIcon;

    MyViewHolder(View view) {
      super(view);

      cardView = view.findViewById(R.id.post_card_view);
      userName=view.findViewById(R.id.post_name);
      dop=view.findViewById(R.id.post_date);
      profilePic=view.findViewById(R.id.post_pic);
      caption=view.findViewById(R.id.post_caption);
      location=view.findViewById(R.id.post_location);
      upVotes=view.findViewById(R.id.post_upvote);
      downVotes=view.findViewById(R.id.post_downvote);
      comments=view.findViewById(R.id.post_comment);
      upvoteIcon=view.findViewById(R.id.post_upvote_icon);
      downvoteIcon=view.findViewById(R.id.post_downvote_icon);
      commentsIcon=view.findViewById(R.id.icon_comment_icon);
      imagePost=view.findViewById(R.id.post_image);
    }
  }

  public class ProgressHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    ProgressHolder(View itemView) {
      super(itemView);
      progressBar = itemView.findViewById(R.id.progressBar);
    }
  }
}