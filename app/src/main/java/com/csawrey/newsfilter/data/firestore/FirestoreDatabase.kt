package com.csawrey.newsfilter.data.firestore

//import com.csawrey.newsfilter.dashboard.home.HomeViewModel
//import com.csawrey.newsfilter.dashboard.home.NewsItem
//import com.google.firebase.firestore.DocumentSnapshot
//import com.google.firebase.firestore.FirebaseFirestore
//
//@Deprecated("Not using firebase currently")
//object FirestoreDatabase {
//
////    private fun getDb() = FirebaseFirestore.getInstance()
////
////    fun getNews(keyword: String, homeViewModel: HomeViewModel) {
////        getDb().collection(keyword).get().addOnSuccessListener {
////            homeViewModel.receiveNews(convertDocs(it.documents))
////        }
////    }
////
////    //fun getNews(keyword: Array<String>, homeViewModel: HomeViewModel)
////
////    private fun convertDocs(docs: List<DocumentSnapshot>): List<NewsItem> {
////        val retList: MutableList<NewsItem> = mutableListOf()
////        for (doc in docs) {
////            retList.add(doc.toObject(NewsItem::class.java)!!)
////        }
////        return retList
////    }
//}