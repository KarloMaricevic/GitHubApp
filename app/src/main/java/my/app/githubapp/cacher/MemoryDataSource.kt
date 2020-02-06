package my.app.githubapp.cacher

import java.lang.Exception

internal class MemoryDataSource<Key : Any,ResourceData : Any>(private val maxEatery : Int ) {

    private val mData = arrayListOf<SingleData<Key,ResourceData>>()
    private var firstAdded = 0

    fun cacheInMemory(key : Key,resource : ResourceData){
        if(mData.count() >= maxEatery){
            mData[firstAdded] = SingleData(key,resource)

            if(firstAdded < mData.count()){
                firstAdded++
            }
            else{
                firstAdded = 0
            }
        }
        else{
            mData.add(SingleData(key,resource))
        }
    }

    private fun whatPlaceIsKey(key: Key) : Int{
        var i = -1

        for(singleData in mData){
            i++
            if(singleData.key == key){
                return i
            }
        }
        return i
    }

    fun doesContainsKey(key : Key) : Boolean {
        for(singleData in mData){
            if(singleData.key == key){
                return true
            }
        }
        return false
    }

    fun getData(key: Key) : ResourceData{
        val dataPlace : Int = whatPlaceIsKey(key)
        if(dataPlace == -1){
            throw Exception("No data in memorySource,try checking if it is contains it")
        }
        else{
            return mData[dataPlace].resource
        }
    }

    fun clearCache() : Boolean{
        try {
            mData.clear()
            return true
        }
        catch (e : Exception){
            return false
        }
    }

}