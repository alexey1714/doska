package com.example.doska.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doska.model.Ad
import com.example.doska.model.DbManager

class FirebaseViewModel: ViewModel() {
    private val dbManager = DbManager()
    val lifeAdsData = MutableLiveData<ArrayList<Ad>>()
    fun loadAllAds(){
        dbManager.getAllAds(object: DbManager.ReadDataCallback{
            override fun readData(list: ArrayList<Ad>) {
                lifeAdsData.value = list
            }

        })
    }

    fun onFavClick(ad: Ad){
        dbManager.onFavClick(ad, object:DbManager.FinishWorkListener{
            override fun onFinish() {
                val updatedList = lifeAdsData.value
                val pos = updatedList?.indexOf(ad)
                if (pos != -1){
                    pos?.let {
                        val favCounter = if (ad.isFav) ad.favCounter.toInt() - 1 else ad.favCounter.toInt() + 1
                        updatedList[pos] = updatedList[pos].copy(isFav = !ad.isFav, favCounter = favCounter.toString())
                    }

                }
                lifeAdsData.postValue(updatedList)
            }

        })
    }

    fun adViewed(ad: Ad){
        dbManager.adViewed(ad)
    }

    fun loadMyAds(){
        dbManager.getMyAds(object: DbManager.ReadDataCallback{
            override fun readData(list: ArrayList<Ad>) {
                lifeAdsData.value = list
            }

        })
    }

    fun loadMyFavs(){
        dbManager.getMyFav(object: DbManager.ReadDataCallback{
            override fun readData(list: ArrayList<Ad>) {
                lifeAdsData.value = list
            }

        })
    }

    fun deleteItem(ad: Ad){
        dbManager.deleteAd(ad, object : DbManager.FinishWorkListener{
            override fun onFinish() {
                val updatedList = lifeAdsData.value
                updatedList?.remove(ad)
                lifeAdsData.postValue(updatedList)
            }

        })
    }

}