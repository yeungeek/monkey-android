package com.yeungeek.monkeyandroid.data.model;

import java.io.Serializable;

/**
 * Created by yeungeek on 2016/4/13.
 */
public class RepoContent implements Serializable{
    private String name;
    private String path;
    private String sha;
    private int size;
    private String url;
    private String html_url;
    private String git_url;
    private String file;
    private String content;
    private String encoding;
    //links

    public String getName() {
        return name;
    }

    public RepoContent setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RepoContent setPath(String path) {
        this.path = path;
        return this;
    }

    public String getSha() {
        return sha;
    }

    public RepoContent setSha(String sha) {
        this.sha = sha;
        return this;
    }

    public int getSize() {
        return size;
    }

    public RepoContent setSize(int size) {
        this.size = size;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RepoContent setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHtml_url() {
        return html_url;
    }

    public RepoContent setHtml_url(String html_url) {
        this.html_url = html_url;
        return this;
    }

    public String getGit_url() {
        return git_url;
    }

    public RepoContent setGit_url(String git_url) {
        this.git_url = git_url;
        return this;
    }

    public String getFile() {
        return file;
    }

    public RepoContent setFile(String file) {
        this.file = file;
        return this;
    }

    public String getContent() {
        return content;
    }

    public RepoContent setContent(String content) {
        this.content = content;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public RepoContent setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
}
