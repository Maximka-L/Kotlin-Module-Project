import java.util.Scanner

class Menu(
    private val title: String,
    private val scanner: Scanner
) {
    private val staticItems = mutableListOf<MenuItem>()
    var dynamicItemsProvider: () -> List<MenuItem> = { emptyList() }

    fun addStaticItem(title: String, action: () -> Unit) {
        staticItems.add(MenuItem(title, action))
    }

    fun show() {
        while (true) {
            println("\n=== $title ===")
            val allItems = staticItems + dynamicItemsProvider()

            if (allItems.isEmpty()) {
                println("Нет доступных пунктов")
                return
            }

            allItems.forEachIndexed { index, item ->
                println("$index. ${item.title}")
            }

            print("Выберите пункт: ")
            val input = scanner.nextLine()

            try {
                val choice = input.toInt()
                if (choice in allItems.indices) {
                    val selectedItem = allItems[choice]
                    selectedItem.action.invoke()

                    if (selectedItem.title == "Назад" || selectedItem.title == "Выход") {
                        return
                    }
                } else {
                    println("Пункта с номером $choice не существует. Пожалуйста, выберите снова.")
                }
            } catch (e: NumberFormatException) {
                println("Некорректный ввод. Пожалуйста, введите номер пункта.")
            }
        }
    }
}

data class MenuItem(
    val title: String,
    val action: () -> Unit = {}
)