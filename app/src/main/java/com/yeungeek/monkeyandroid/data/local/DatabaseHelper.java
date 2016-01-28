package com.yeungeek.monkeyandroid.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.yeungeek.monkeyandroid.data.model.Repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yeungeek on 2016/1/18.
 */
@Singleton
public class DatabaseHelper {
    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    /**
     * Repo
     */
    public Observable<List<Repo>> addRepos(final List<Repo> repos) {
        return Observable.create(new Observable.OnSubscribe<List<Repo>>() {
            @Override
            public void call(Subscriber<? super List<Repo>> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    //clear
                    mDb.delete(Db.RepoTable.TABLE_NAME,null);

                    for (Repo repo : repos) {
                        ContentValues contentValues = Db.RepoTable.toContentValues(repo);
                        mDb.insert(Db.RepoTable.TABLE_NAME, contentValues);
                    }

                    transaction.markSuccessful();
//                    subscriber.onCompleted();
                    subscriber.onNext(repos);
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
                    }

                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }
}
