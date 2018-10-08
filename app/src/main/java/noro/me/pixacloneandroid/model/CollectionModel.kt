package noro.me.pixacloneandroid.model


enum class CollectionType{
    Editor,
    Category,
    Search
}

open class PixaCollectionModelProtocol{
    open var data:Any? = null
    open var parameters: HashMap<PixaBayAPI.Keys, String> = hashMapOf()
}

class EditorCollectionModel: PixaCollectionModelProtocol {
    constructor()
    override var parameters: HashMap<PixaBayAPI.Keys, String> = hashMapOf(
            PixaBayAPI.Keys.Q to "",
            PixaBayAPI.Keys.Category to "",
            PixaBayAPI.Keys.Editors_Choice to "false",
            PixaBayAPI.Keys.Page to ""
    )

    override var data: Any? = null
        get() =  null
        set(newValue) {
            parameters[PixaBayAPI.Keys.Editors_Choice] = "true"
            field = newValue
        }


}

class SearchCollectionModel: PixaCollectionModelProtocol {
    constructor()

    override var parameters: HashMap<PixaBayAPI.Keys, String> = hashMapOf(
            PixaBayAPI.Keys.Q to "",
            PixaBayAPI.Keys.Category to "",
            PixaBayAPI.Keys.Editors_Choice to "false",
            PixaBayAPI.Keys.Page to ""
    )

    override var data: Any? = null
        get() = null

        set(newValue) {
            newValue.let {
                if (it is String) {
                    parameters[PixaBayAPI.Keys.Q] = it
                }
            }
            field = newValue
        }
}

class CategoryCollectionModel: PixaCollectionModelProtocol {

    constructor()

    override var parameters: HashMap<PixaBayAPI.Keys, String> = hashMapOf(
            PixaBayAPI.Keys.Q to "",
            PixaBayAPI.Keys.Category to "",
            PixaBayAPI.Keys.Editors_Choice to "false",
            PixaBayAPI.Keys.Page to "1"
    )

    override var data: Any? = null
        get() = null
        set(newValue) {
            newValue.let{
                if (it is String) {
                    parameters[PixaBayAPI.Keys.Category] = it
                }
            }
            field = newValue
        }

}
