package com.example.marie.moodyfoodie;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static class MoodTable implements BaseColumns {
        public static final String MOOD_TABLE_NAME = "moodTable";
        public static final String COL_MOOD_NAME = "moodName";
        public static final String COL_MOOD_EMOJI = "moodEmoji";
    }

    public static class DayTable implements BaseColumns {
        public static final String DAY_TABLE_NAME = "dayTable";
        public static final String DATE = "entryDate";
        public static final String FIELD_NAME = "fieldName";
        public static final String FIELD_VALUE = "fieldValue";
    }

    public static class FoodTable implements BaseColumns {
        public static final String FOOD_TABLE_NAME = "foodTable";
        public static final String FOOD_NAME = "foodName";
    }

    public static final String SQL_CREATE_MoodTable =
            "CREATE TABLE IF NOT EXISTS " + MoodTable.MOOD_TABLE_NAME + " (" +
            MoodTable._ID + " INTEGER PRIMARY KEY," +
            MoodTable.COL_MOOD_NAME + " TEXT," +
            MoodTable.COL_MOOD_EMOJI + " TEXT)";

    public static final String SQL_CREATE_DayTable =
            "CREATE TABLE IF NOT EXISTS " + DayTable.DAY_TABLE_NAME + " (" +
            DayTable._ID + " INTEGER PRIMARY KEY," +
            DayTable.DATE + " DATE," +
            DayTable.FIELD_NAME + " TEXT," +
            DayTable.FIELD_VALUE + " TEXT)";

    public static final String SQL_CREATE_FoodTable =
            "CREATE TABLE IF NOT EXISTS " + FoodTable.FOOD_TABLE_NAME + " (" +
            FoodTable._ID + " INTEGER PRIMARY KEY," +
            FoodTable.FOOD_NAME + " DATE)";

    public static final String DATABASE_NAME = "moodyFoodieDB";
}
