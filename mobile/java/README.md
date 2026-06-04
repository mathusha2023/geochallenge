# GeoChallenge Native Android Layer

Нативный Java-слой для Flutter-приложения ГеоВызов. Обеспечивает platform channels для геолокации, шагомера, офлайн-очереди и фоновой синхронизации прохождения квестов.

> Модуль не подключён к Gradle-сборке Flutter-проекта напрямую; используется как reference-реализация и для platform-specific логики.

## Структура

| Пакет | Назначение |
|-------|------------|
| `core` | Регистрация MethodChannel, мост Flutter ↔ Android |
| `geo` | Геофencing, расчёт дистанции до чекпоинтов |
| `quest` | Сессия прохождения, таймер, проверка близости |
| `sync` | Офлайн-очередь REST-запросов |
| `auth` | Хранение JWT, парсинг payload |
| `pedometer` | Аккумулятор шагов для чекпоинтов |
| `team` | Обратный отсчёт готовности команды |
| `config` | Загрузка конфигурации из XML |

## Ресурсы

`src/main/res/xml/` — network security, backup rules, shared preferences schema.
