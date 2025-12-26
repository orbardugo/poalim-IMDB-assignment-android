package com.example.imdbassignmentandroid.data.local

import android.content.SharedPreferences
import com.example.imdbassignmentandroid.data.local.PrefsKeys.FAVORITES
import com.example.imdbassignmentandroid.domain.model.MediaType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesStore @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getFavoritesRaw(): Set<String> =
        prefs.getStringSet(FAVORITES, emptySet()) ?: emptySet()

    private fun buildKey(mediaType: MediaType, id: Int): String =
        "${mediaType.name.lowercase()}:$id"

    fun isFavorite(mediaType: MediaType, id: Int): Boolean {
        val key = buildKey(mediaType, id)
        return getFavoritesRaw().contains(key)
    }

    fun toggleFavorite(mediaType: MediaType, id: Int) {
        val key = buildKey(mediaType, id)
        val current = getFavoritesRaw().toMutableSet()

        if (current.contains(key)) current.remove(key) else current.add(key)

        prefs.edit()
            .putStringSet(FAVORITES, current)
            .apply()
    }
}
