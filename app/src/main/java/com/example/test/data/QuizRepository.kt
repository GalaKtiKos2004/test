package com.example.test.data

object QuizRepository {
    fun getQuestions(): List<Question> = listOf(
        Question(
            id = 1,
            text = "Сколько игроков в команде по керлингу?",
            options = listOf(
                "2 игрока",
                "4 игрока",
                "6 игроков",
                "8 игроков"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 2,
            text = "Как называется снаряд для керлинга?",
            options = listOf(
                "Диск",
                "Шайба",
                "Снаряд",
                "Камень"
            ),
            correctAnswerIndex = 3
        ),
        Question(
            id = 3,
            text = "Какой вес имеет стандартный камень для керлинга?",
            options = listOf(
                "Около 10 кг",
                "Около 15 кг",
                "Около 20 кг",
                "Около 25 кг"
            ),
            correctAnswerIndex = 2
        ),
        Question(
            id = 4,
            text = "Как называется центральная мишень в керлинге?",
            options = listOf(
                "Дом",
                "Круг",
                "Центр",
                "Яблочко"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 5,
            text = "Сколько эндов в стандартной игре по керлингу?",
            options = listOf(
                "6 эндов",
                "8 эндов",
                "10 эндов",
                "12 эндов"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 6,
            text = "Как называется капитан команды",
            options = listOf(
                "Скип",
                "Свипер",
                "Тиро",
                "Капитан"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 7,
            text = "Что делают свиперы во время движения камня?",
            options = listOf(
                "Кричат подсказки",
                "Трут лед перед камнем",
                "Измеряют расстояние",
                "Подсчитывают очки"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 8,
            text = "В какой стране керлинг стал популярным видом спорта?",
            options = listOf(
                "Канада",
                "Шотландия",
                "Норвегия",
                "Россия"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 9,
            text = "Как называется линия, за которую камень должен полностью пересечь, чтобы быть в игре?",
            options = listOf(
                "Стартовая линия",
                "Линия дома",
                "Линия Хог",
                "Финишная линия"
            ),
            correctAnswerIndex = 2
        ),
        Question(
            id = 10,
            text = "Когда керлинг был включен в программу зимних Олимпийских игр?",
            options = listOf(
                "1998 год (Нагано)",
                "2002 год (Солт-Лейк-Сити)",
                "2006 год (Турин)",
                "2010 год (Ванкувер)"
            ),
            correctAnswerIndex = 0
        )
    )
}
