package com.yeungeek.monkeyandroid.ui.repos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Repo> repos = new ArrayList<>();
    private Context mContext;

    public RepoAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Repo repo = repos.get(position);
        RepoViewHolder repoViewHolder = (RepoViewHolder) holder;
        repoViewHolder.repoName.setText(repo.getName());
        repoViewHolder.repoDesc.setText(repo.getDescription());
        repoViewHolder.repoStars.setText(String.valueOf(repo.getStargazers_count()));
        if (null != repo.getOwner()) {
            Glide.with(mContext).load(repo.getOwner().getAvatarUrl()).into(repoViewHolder.ownerAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setDatas(final List<Repo> data) {
        this.repos = data;
        notifyDataSetChanged();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.id_repo_name)
        TextView repoName;
        @Bind(R.id.id_repo_desc)
        TextView repoDesc;
        @Bind(R.id.id_repo_owner_avatar)
        ImageView ownerAvatar;
        @Bind(R.id.id_repo_stars)
        TextView repoStars;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
