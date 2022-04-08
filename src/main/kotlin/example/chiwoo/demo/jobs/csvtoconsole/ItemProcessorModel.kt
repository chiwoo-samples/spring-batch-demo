package example.chiwoo.demo.jobs.csvtoconsole

import org.springframework.batch.item.ItemProcessor

class ItemProcessorModel : ItemProcessor<ItemModel, ItemModel> {
    override fun process(model: ItemModel): ItemModel {
        return model
    }
}