package com.yeungeek.monkeyandroid.data.model;

import com.yeungeek.monkeyandroid.util.ImageSize;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private int id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String followering_url;
    private String gists_url;
    private String statted_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private Boolean site_admin;
    private int score;
    private String email;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String created_at;
    private String updated_at;
    private int followers;
    private int following;

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getAvatarUrl() {
        return avatar_url + ImageSize.AVATAR_120;
    }

    public User setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
        return this;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public User setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public User setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHtml_url() {
        return html_url;
    }

    public User setHtml_url(String html_url) {
        this.html_url = html_url;
        return this;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public User setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
        return this;
    }

    public String getFollowering_url() {
        return followering_url;
    }

    public User setFollowering_url(String followering_url) {
        this.followering_url = followering_url;
        return this;
    }

    public String getGists_url() {
        return gists_url;
    }

    public User setGists_url(String gists_url) {
        this.gists_url = gists_url;
        return this;
    }

    public String getStatted_url() {
        return statted_url;
    }

    public User setStatted_url(String statted_url) {
        this.statted_url = statted_url;
        return this;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public User setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
        return this;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public User setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
        return this;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public User setRepos_url(String repos_url) {
        this.repos_url = repos_url;
        return this;
    }

    public String getEvents_url() {
        return events_url;
    }

    public User setEvents_url(String events_url) {
        this.events_url = events_url;
        return this;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public User setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
        return this;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    public Boolean getSite_admin() {
        return site_admin;
    }

    public User setSite_admin(Boolean site_admin) {
        this.site_admin = site_admin;
        return this;
    }

    public int getScore() {
        return score;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getFollowers() {
        return followers;
    }

    public User setFollowers(int followers) {
        this.followers = followers;
        return this;
    }

    public int getFollowing() {
        return following;
    }

    public User setFollowing(int following) {
        this.following = following;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public User setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getBlog() {
        return blog;
    }

    public User setBlog(String blog) {
        this.blog = blog;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public User setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public User setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public User setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
