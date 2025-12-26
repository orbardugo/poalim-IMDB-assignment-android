package com.example.imdbassignmentandroid.ui.search

import com.example.imdbassignmentandroid.domain.model.SearchResult

data class SearchUiState(
    val query: String = "",
    val results: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
