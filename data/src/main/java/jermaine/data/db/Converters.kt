package jermaine.data.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import jermaine.domain.articles.model.Source


class Converters {
    @TypeConverter
    fun sourceToString(source: Source): String = Gson().toJson(source)

    @TypeConverter
    fun stringToSource(string: String): Source = Gson().fromJson(string, Source::class.java)
}