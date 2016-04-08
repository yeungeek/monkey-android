package com.yeungeek.monkeyandroid.data.local;

import android.content.Context;

import com.google.gson.Gson;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yeungeek on 2016/3/30.
 */
@Singleton
public class LanguageHelper {
    private HashMap<String, Language> languageMap = new HashMap<>();
    private List<Language> selectedLanguages = new ArrayList<>();
    private Language[] allLanguages;
    private Context mContext;
    private Gson mGson;

    @Inject
    public LanguageHelper(@ApplicationContext Context context, Gson gson) {
        mContext = context;
        mGson = gson;
        selectedLanguages.addAll(getDefaultLanguage());
    }

    public Language getAllLanguage() {
        return getLanguageByName("All Language");
    }

    public List<Language> getLanguage() {
        return selectedLanguages;
    }

    private List<Language> getDefaultLanguage() {
        String[] defaultLanguagesName = new String[]{"Java", "JavaScript", "Go", "CSS", "Objective-C", "Python", "Swift", "HTML"};

        List<Language> defaultLanguages = new ArrayList<>();
        for (String langNAme : defaultLanguagesName) {
            defaultLanguages.add(getLanguageByName(langNAme));
        }
        return defaultLanguages;
    }

    public Language getLanguageByName(String languageName) {
        if (languageMap.size() == 0) {

            for (Language language : getAllLanguages()) {
                languageMap.put(language.name, language);
            }
        }

        return languageMap.get(languageName);
    }

    public Language[] getAllLanguages() {
        if (allLanguages != null) {
            return allLanguages;
        }

        try {
            StringBuilder buf = new StringBuilder();

            InputStream inputStream = mContext.getAssets().open("langs.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();

            allLanguages = mGson.fromJson(buf.toString(), Language[].class);
        } catch (Exception e) {
        }
        return allLanguages;
    }

}
