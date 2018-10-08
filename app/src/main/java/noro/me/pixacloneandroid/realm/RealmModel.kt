package noro.me.pixacloneandroid.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


enum class RealmError {
    NewRecordFailed,
    UpdateRecordFailed,
    RealmInstanceIsNil
}

open class ApiResponse: RealmObject() {
    @PrimaryKey var url: String = ""
    var response: String = ""
    var date: Date = Date()
    var isValidResponse: Boolean = false

}