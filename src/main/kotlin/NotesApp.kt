import java.util.Scanner

class NotesApp {
    private val scanner = Scanner(System.`in`)
    private val archives = mutableListOf<Archive>()

    fun start() {
        showArchivesMenu()
    }

    private fun showArchivesMenu() {
        val menu = Menu(
            title = "Список архивов",
            scanner = scanner
        ).apply {
            addStaticItem("Создать архив") { createArchive() }
            addStaticItem("Выход") { return@addStaticItem }

            dynamicItemsProvider = {
                archives.mapIndexed { index, archive ->
                    MenuItem("${archive.name}") { showNotesMenu(archive) }
                }
            }
        }

        menu.show()
    }

    private fun createArchive() {
        val name = readNonEmptyInput("Введите название архива:")
        archives.add(Archive(name))
        println("Архив '$name' создан.")
    }

    private fun showNotesMenu(archive: Archive) {
        val menu = Menu(
            title = "Список заметок в архиве '${archive.name}'",
            scanner = scanner
        ).apply {
            addStaticItem("Создать заметку") { createNote(archive) }
            addStaticItem("Назад") { return@addStaticItem }

            dynamicItemsProvider = {
                archive.notes.mapIndexed { index, note ->
                    MenuItem("${note.title}") { showNoteContent(note) }
                }
            }
        }

        menu.show()
    }

    private fun createNote(archive: Archive) {
        val title = readNonEmptyInput("Введите название заметки:")
        val content = readNonEmptyInput("Введите текст заметки:")
        archive.notes.add(Note(title, content))
        println("Заметка '$title' создана.")
    }

    private fun showNoteContent(note: Note) {
        println("\n=== ${note.title} ===")
        println(note.content)
        println("\nНажмите Enter чтобы вернуться...")
        scanner.nextLine()
    }

    private fun readNonEmptyInput(prompt: String): String {
        while (true) {
            println(prompt)
            val input = scanner.nextLine().trim()
            if (input.isNotEmpty()) {
                return input
            }
            println("Поле не может быть пустым. Пожалуйста, введите значение.")
        }
    }
}