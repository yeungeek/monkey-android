package com.yeungeek.monkeyandroid.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.yeungeek.monkeyandroid.data.model.Repo;

/**
 * Created by yeungeek on 2016/1/18.
 */
public class Db {
    public Db() {
    }

    private static class BaseTable {
        public static final String COLUMN_INDEX = "_index";
    }

    public static final class RepoTable extends BaseTable {
        public static final String TABLE_NAME = "repo";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "_name";
        public static final String COLUMN_DESC = "_desc";
        public static final String COLUMN_OWERID = "_owerId";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_ID + " INTEGER NOT NULL,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_DESC + " TEXT,"
                        + COLUMN_OWERID + " INTEGER"
                        + " );";

        public static ContentValues toContentValues(Repo repo) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, repo.getId());
            values.put(COLUMN_NAME, repo.getName());
            values.put(COLUMN_DESC, repo.getDescription());
            return values;
        }

        public static Repo parseCursor(Cursor cursor) {
            Repo repo = new Repo();
            repo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            repo.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            repo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)));
            return repo;
        }
    }
}
