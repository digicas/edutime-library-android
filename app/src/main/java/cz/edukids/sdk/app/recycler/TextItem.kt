package cz.edukids.sdk.app.recycler

import com.skoumal.teanity.databinding.RecyclerViewItem
import com.skoumal.teanity.extensions.compareTo
import cz.edukids.sdk.app.R

class TextItem(val text: String) : RecyclerViewItem() {

    override val layoutRes = R.layout.item_text

    override fun contentSameAs(other: RecyclerViewItem) = sameAs(other)
    override fun sameAs(other: RecyclerViewItem) = other.compareTo<TextItem> {
        it.text == text
    }

}