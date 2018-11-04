package org.yxm.bees.conversion;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListStringConversion {
    @TypeConverter
    public static List<String> strToStrList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listype = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(data, listype);
    }

    @TypeConverter
    public static String listStrToStr(List<String> stringList) {
        return new Gson().toJson(stringList);
    }
}
