package noro.me.pixacloneandroid.realm

import com.google.gson.Gson
import io.realm.Realm
import noro.me.pixacloneandroid.model.PixaPhotoModel
import noro.me.pixacloneandroid.model.PixaResponse
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


public class RealmService {

    companion object {

        var lifePeriod : Long = 24 * 60 * 60


        fun save(request: String, response: ArrayList<PixaPhotoModel>) {

            val gson = Gson()
            val jsonArray = gson.toJson(response)

            val realm = Realm.getDefaultInstance()

            val result = realm.where(ApiResponse::class.java).equalTo("url",request).findAll()

            if (result.size == 0) {

                val newRecord = ApiResponse()
                newRecord.url = request
                newRecord.response = jsonArray
                newRecord.isValidResponse = true

                realm.executeTransaction {
                    it.insert(newRecord)
                }
                return
            }
            else {
                val record = result.first()!!
                if (!record.isValidResponse) {
                    realm.beginTransaction()
                    record.response = jsonArray
                    record.date = Date()
                    record.isValidResponse = true
                    realm.commitTransaction()
                }
            }
        }

        fun cache(request: String) : String? {

            val realm = Realm.getDefaultInstance()
            var record = realm.where(ApiResponse::class.java).equalTo("url",request).findFirst()

            if (record != null) {

                val period  = Date().time - record!!.date.time.toLong()
                if (period > lifePeriod) {
                    realm.beginTransaction()
                    record!!.isValidResponse = false
                    realm.commitTransaction()
                }
                return record!!.response.toString()

            }

            return null
        }

    }

}
