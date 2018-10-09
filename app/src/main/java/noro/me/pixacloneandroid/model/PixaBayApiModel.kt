package noro.me.pixacloneandroid.model

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


enum class PixaApiError(val value: String) {
    FailedToDecodePhoto(""),
    HttpResponseError(""),
    NilResults(""),
    CachedResultsFailed(""),
    WrongUrl("")
}

class PixaBayAPI {


    enum class Keys(val value: String) {
        Key( "key"),
        Q("q"),
        Category("category"),
        Editors_Choice( "editors_choice"),
        Safesearch("safesearch"),
        Order("order"),
        Page("page"),
        Per_Page("per_page")
    }


    companion object {

        const val MaxFetchPerPage = 150
        private const val api = "https://pixabay.com/api/"
        private const val key = "183103-aa92344f89e7134a1a806f5b5"

        fun buildRequestURL(parameters: HashMap<Keys, String>?, latest: Boolean = false): String? {

            if (parameters == null)
                return null

            val builder = Uri.parse(api).buildUpon()
            builder.appendQueryParameter(Keys.Key.value, key)
            builder.appendQueryParameter(Keys.Safesearch.value, "true")
            builder.appendQueryParameter(Keys.Per_Page.value, MaxFetchPerPage.toString())
            builder.appendQueryParameter(Keys.Order.value, if (latest) "latest" else "popular")

            parameters.map { builder.appendQueryParameter(it.key.value, it.value) }


            return builder.toString()

        }

        fun decode(response: String): Array<PixaPhotoModel> {
            val gson = Gson()
            var gsonResponse:Array<PixaPhotoModel> = arrayOf()
           try{
               gsonResponse = gson.fromJson(response,   Array<PixaPhotoModel>::class.java)
           }catch (e: JsonParseException) {
               throw Exception("failed in parsing Json")
           }
            return gsonResponse
        }
    }


    }
