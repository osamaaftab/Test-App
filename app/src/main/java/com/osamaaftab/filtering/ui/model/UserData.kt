package com.osamaaftab.filtering.ui.model

data class UserData(
    var display_name: String? = null,
    var main_photo: String? = null,
    var age: Int? = null,
    var job_title: String? = null,
    var height_in_cm: Int? = null,
    var compatibility_score: Double? = null,
    var contacts_exchanged: Int? = null,
    var favourite: Boolean? = null,
    var religion: String? = null,
    var city: UserCity? = null
) {

}