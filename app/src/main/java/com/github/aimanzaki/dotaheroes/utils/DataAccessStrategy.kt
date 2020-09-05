package com.github.aimanzaki.dotaheroes.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers


fun <T> performGetFromLocal(
  databaseQuery: () -> LiveData<T>
): LiveData<Resource<T>> = liveData(Dispatchers.IO){
    emit(Resource.loading())
    val source = databaseQuery.invoke().map { Resource.success(it) }
    emitSource(source)
}

fun <T,A> performGetOperation(
  databaseQuery:() -> LiveData<T>,
  networkCall:suspend () -> Resource<A>,
  saveCallResult:suspend (A) -> Unit):LiveData<Resource<T>> = liveData(Dispatchers.IO){
    emit(Resource.loading())
    val source = databaseQuery.invoke().map { Resource.success(it) }
    emitSource(source)

    val responseStatus = networkCall.invoke()
    if(responseStatus.status == Resource.Status.SUCCESS){
      saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == Resource.Status.ERROR){
      emit(Resource.error(responseStatus.message!!))
      emitSource((source))
    }
}

fun <A> performGetOperationWithoutLocal(
  networkCall:suspend () -> Resource<A>
):LiveData<Resource<A>> = liveData(Dispatchers.IO){
  val liveData = MutableLiveData<Resource<A>>()
  val responseStatus = networkCall.invoke()
  if(responseStatus.status == Resource.Status.SUCCESS){
    liveData.postValue(Resource.success(responseStatus.data!!))
    emitSource(liveData)
  } else if (responseStatus.status == Resource.Status.ERROR){
    emit(Resource.error(responseStatus.message!!))
    emitSource(liveData)
  }
}
