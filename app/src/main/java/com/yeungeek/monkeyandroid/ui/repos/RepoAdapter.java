package com.yeungeek.monkeyandroid.ui.repos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.adapter.BaseLoadMoreAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class RepoAdapter extends BaseLoadMoreAdapter<Repo, RepoAdapter.RepoViewHolder> {
    private Context mContext;

    public RepoAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public RepoViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RepoViewHolder holder, int position) {
        final Repo repo = getItem(position);
        holder.repo = repo;

        holder.repoName.setText(repo.getName());
        holder.repoDesc.setText(repo.getDescription());
        holder.repoStars.setText(String.valueOf(repo.getStargazers_count()));
        if (null != repo.getOwner()) {
            Glide.with(mContext).load(repo.getOwner().getAvatarUrl()).into(holder.ownerAvatar);
        }
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        Repo repo;

        @Bind(R.id.id_repo_name)
        TextView repoName;
        @Bind(R.id.id_repo_desc)
        TextView repoDesc;
        @Bind(R.id.id_repo_owner_avatar)
        ImageView ownerAvatar;
        @Bind(R.id.id_repo_stars)
        TextView repoStars;

        @OnClick(R.id.id_repo_card)
        public void onItemClick() {
            Toast.makeText(mContext, "点击了: " + repo.getName(), Toast.LENGTH_SHORT).show();
        }

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
