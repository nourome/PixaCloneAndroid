package noro.me.pixacloneandroid.ui.editor

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel


import android.widget.GridView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import io.reactivex.Observable
import io.reactivex.Single
import noro.me.pixacloneandroid.model.PixaPhotoModel
import noro.me.pixacloneandroid.model.GsonRequest
import noro.me.pixacloneandroid.model.*
import noro.me.pixacloneandroid.realm.RealmService
import java.lang.Error
import java.net.URL
import com.android.volley.RequestQueue
import com.google.gson.Gson
import noro.me.pixacloneandroid.helpers.VolleySingleton



class EditorViewModel(private val app: Application): AndroidViewModel(app){

    var model: PixaCollectionModelProtocol? = null
    var photos:ArrayList<PixaPhotoModel> = arrayListOf()
    private var _pageNumber = 0

    var loadedItems:List<PixaPhotoModel> = listOf()
        get() {
            if (photos.size > 0) {
                val min = photos.size - PixaBayAPI.MaxFetchPerPage
                val range = Array<Int>(PixaBayAPI.MaxFetchPerPage ) {it + min}
                return range.map {
                    photos[it]
                }
            } else {
                return listOf()
            }
        }

    var pageNumber: Int
        get() {
            return _pageNumber
        }
        set(page) {
            _pageNumber = page
            model?.let {  it.parameters[PixaBayAPI.Keys.Page] = page.toString() }

        }


    fun loadPhotos(latest: Boolean = false) : Observable<ResponseStatus> {
        return Observable.create {
            pageNumber += 1
            val observer = it
            //todo: check model forcing !!
            val url =  PixaBayAPI.buildRequestURL(model?.parameters, latest)

           if (url != null){
               val requestUrl = URL(url)

            val cached = RealmService.cache(url!!)

            if (cached != null) {
                val hits =  PixaBayAPI.decode(cached)
                photos.addAll(hits)
                if (photos.size <= PixaBayAPI.MaxFetchPerPage) {
                    observer.onNext(ResponseStatus.Start)
                    observer.onComplete()
                }else {
                    observer.onNext(ResponseStatus.Cached)
                    observer.onComplete()
                }
            } else {
                requestPhotos(url,latest).subscribe({
                    observer.onNext(it)
                    observer.onComplete()
                },{
                    observer.onNext(ResponseStatus.Failed)
                    observer.onError(it)
                })
            }
            } else {
               observer.onError(Throwable("URL is null"))
           }


        }
    }



    private fun requestPhotos(url: String, latest: Boolean): Single<ResponseStatus> {

        return Single.create{
            val single  = it
            val queue = VolleySingleton.getInstance(app.applicationContext).requestQueue
            val gsonRequest = GsonRequest<PixaResponse>(url, PixaResponse::class.java,null,
                     Response.Listener{
                        val hits = it.hits
                        if (hits == null) {
                            single.onSuccess(ResponseStatus.Failed)
                        }
                        hits.let {
                            //todo hits is saved as string , converting back will be hard
                            RealmService.save(url, hits!!)
                            photos.addAll(hits!!)
                            if (photos.size <= PixaBayAPI.MaxFetchPerPage) {
                                single.onSuccess(ResponseStatus.Success)
                            } else {
                                single.onSuccess(ResponseStatus.Success)
                                //Todo preload images
                                //ImagePrefetcher.init(urls: self.loadedItemsUrl).start()
                            }

                        }

                    },Response.ErrorListener(){
                single.onSuccess(ResponseStatus.Failed)
                //single.onError(it)
            })

            queue.add(gsonRequest)
        }
    }

}

