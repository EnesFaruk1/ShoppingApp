package mobi.appcent.shoppingapp.model

data class Shopping(
    val mainTitle: String?,
    val subTitle : String?,
    val categoryList : List<CategoryList>,
    val productList : List<ProductList>?
)