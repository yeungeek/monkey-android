package com.yeungeek.monkeyandroid.ui.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yeungeek on 2016/4/8.
 */
public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<User> users = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public UserAdapter(final Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        if (null != mOnItemClickListener) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v);
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user = users.get(position);
        UserViewHolder viewHolder = (UserViewHolder) holder;
        viewHolder.user = user;

        viewHolder.login.setText(user.getLogin());
        Glide.with(mContext).load(user.getAvatarUrl()).into(viewHolder.userAvatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAll(final List<User> list) {
        users.addAll(list);
    }

    public void addTopAll(final List<User> list) {
        users.clear();
        users.addAll(list);
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        User user;

        @Bind(R.id.id_user_login)
        TextView login;
        @Bind(R.id.id_user_avatar)
        ImageView userAvatar;

//        @OnClick(R.id.id_user_card)
//        public void onItemClick() {
//            mContext.startActivity(DetailActivity.getStartIntent(mContext, user));
//        }

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public User getUser(int position) {
        return users.get(position);
    }

    public UserAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
}
