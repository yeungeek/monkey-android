package com.yeungeek.monkeyandroid.data.model;

import com.google.gson.annotations.SerializedName;

public class Repo {
    private long id;
    private String name;
    private String full_name;
    @SerializedName("private")
    private boolean _private;
    private String html_url;
    private String description;
    private boolean fork;
    private String downloads_url;
    private String issues_url;
    private String pulls_url;
    private String releases_url;
    private String created_at;
    private String updated_at;
    private String pushed_at;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String homepage;
    private int size;
    private int stargazers_count;
    private int watchers_count;
    private String language;
    private int forks_count;
    private String mirror_url;
    private int open_issues_count;
    private int forks;
    private int open_issues;
    private int watchers;
    private String default_branch;
    private int score;

    User owner;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getFull_name() {
        return full_name;
    }

    public Repo setFull_name(String full_name) {
        this.full_name = full_name;
        return this;
    }

    public boolean is_private() {
        return _private;
    }

    public Repo set_private(boolean _private) {
        this._private = _private;
        return this;
    }

    public String getHtml_url() {
        return html_url;
    }

    public Repo setHtml_url(String html_url) {
        this.html_url = html_url;
        return this;
    }

    public boolean isFork() {
        return fork;
    }

    public Repo setFork(boolean fork) {
        this.fork = fork;
        return this;
    }

    public String getDownloads_url() {
        return downloads_url;
    }

    public Repo setDownloads_url(String downloads_url) {
        this.downloads_url = downloads_url;
        return this;
    }

    public String getIssues_url() {
        return issues_url;
    }

    public Repo setIssues_url(String issues_url) {
        this.issues_url = issues_url;
        return this;
    }

    public String getPulls_url() {
        return pulls_url;
    }

    public Repo setPulls_url(String pulls_url) {
        this.pulls_url = pulls_url;
        return this;
    }

    public String getReleases_url() {
        return releases_url;
    }

    public Repo setReleases_url(String releases_url) {
        this.releases_url = releases_url;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Repo setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Repo setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public Repo setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
        return this;
    }

    public String getGit_url() {
        return git_url;
    }

    public Repo setGit_url(String git_url) {
        this.git_url = git_url;
        return this;
    }

    public String getSsh_url() {
        return ssh_url;
    }

    public Repo setSsh_url(String ssh_url) {
        this.ssh_url = ssh_url;
        return this;
    }

    public String getClone_url() {
        return clone_url;
    }

    public Repo setClone_url(String clone_url) {
        this.clone_url = clone_url;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public Repo setHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public int getSize() {
        return size;
    }

    public Repo setSize(int size) {
        this.size = size;
        return this;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public Repo setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
        return this;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public Repo setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Repo setLanguage(String language) {
        this.language = language;
        return this;
    }

    public int getForks_count() {
        return forks_count;
    }

    public Repo setForks_count(int forks_count) {
        this.forks_count = forks_count;
        return this;
    }

    public String getMirror_url() {
        return mirror_url;
    }

    public Repo setMirror_url(String mirror_url) {
        this.mirror_url = mirror_url;
        return this;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public Repo setOpen_issues_count(int open_issues_count) {
        this.open_issues_count = open_issues_count;
        return this;
    }

    public int getForks() {
        return forks;
    }

    public Repo setForks(int forks) {
        this.forks = forks;
        return this;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public Repo setOpen_issues(int open_issues) {
        this.open_issues = open_issues;
        return this;
    }

    public int getWatchers() {
        return watchers;
    }

    public Repo setWatchers(int watchers) {
        this.watchers = watchers;
        return this;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public Repo setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Repo setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repo repo = (Repo) o;

        return id == repo.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
