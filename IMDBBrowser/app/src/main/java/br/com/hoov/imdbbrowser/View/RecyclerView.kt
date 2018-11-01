package br.com.hoov.imdbbrowser.View

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup

class RecyclerView<R : Row, Item> : RecyclerView {
    private lateinit var rowClass: Class<R>
    private var _items: List<Item> = listOf()
        set(value) { field = value; adapter.items = _items }
    var items: List<Item>
        get() = _items
        set(value) { _items = value }
    var itemSelectedHandler: ((Item) -> Void)? = null
        set(value) { field = value; adapter.itemSelectedHandler = itemSelectedHandler }

    // Components
    private val layoutManager by lazy { LinearLayoutManager(context) }
    private val adapter by lazy { Adapter<R, Item>(rowClass) }

    // Lifecycle
    constructor(context: Context) : super(context) { throw IllegalAccessException() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { throw IllegalAccessException() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { throw IllegalAccessException() }

    constructor(context: Context, rowClass: Class<R>, items: List<Item>) : super(context) {
        // Set up data
        this.rowClass = rowClass
        _items = items
        // Set up separators
        val separator = DividerItemDecoration(context, VERTICAL)
        separator.setDrawable(resources.getDrawable(br.com.hoov.imdbbrowser.R.drawable.component_separator, context.theme))
        addItemDecoration(separator)
        // Set up layout
        setLayoutManager(layoutManager)
        setAdapter(adapter)
    }

    // Nested types
    private class RowHolder(val row: Row) : ViewHolder(row)

    private class Adapter<R : Row, Item>(private val rowClass: Class<R>) : RecyclerView.Adapter<RowHolder>() {
        var items: List<Item> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        var itemSelectedHandler: ((Item) -> Void)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
            val constructor = rowClass.getConstructor(Context::class.java)
            val row = constructor.newInstance(parent.context)
            return RowHolder(row)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: RowHolder, position: Int) {
            val item = items.getOrNull(position) ?: return
            holder.row.item = item
            holder.row.setOnClickListener {
                val itemSelectedHandler = this.itemSelectedHandler
                if (itemSelectedHandler != null) {
                    itemSelectedHandler.invoke(item)
                } else {
                    holder.row.handleDidSelect()
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            // Must be overridden for correct row re-use
            return regularRowTypeID
        }

        companion object {
            private const val regularRowTypeID = 0
        }
    }
}
