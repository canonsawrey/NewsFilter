package com.csawrey.newsstreams.dashboard.search

import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import com.csawrey.newsstreams.edit_stream.EditorSearchItem
import com.csawrey.newsstreams.toSort
import com.csawrey.newsstreams.toWeight

fun DatabaseSearchItem.toEditorSearchItem() =
    EditorSearchItem(this.uid, this.keyword, this.sort.toSort(), this.weight.toWeight(), this.daysOld)