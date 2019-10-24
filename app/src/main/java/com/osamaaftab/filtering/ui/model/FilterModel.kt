package com.osamaaftab.filtering.ui.model

data class FilterModel(
    var hasPhoto: Boolean? = null,
    var inContact: Boolean? = null,
    var isFavourite: Boolean? = null,
    var score_start: Double? = null,
    var score_end: Double? = null,
    var age_start: Int? = null,
    var age_end: Int? = null,
    var height_start: Int? = null,
    var height_end: Int? = null,
    var distance: Int? = null,
    var currentuser: String? = null
) {

}