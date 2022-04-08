package example.chiwoo.demo.jobs.csvtoxml

import org.springframework.batch.item.ItemProcessor

class ItemProcessorModel : ItemProcessor<ItemModel, ItemModel> {
    override fun process(m: ItemModel): ItemModel {
        return m
    }
}