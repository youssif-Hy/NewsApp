# NewsApp

A simple Android news app built with NewsAPI.

## Added Features

- Categories-first home screen
- News categories: `business`, `entertainment`, `general`, `health`, `science`, `sports`, `technology`
- Country selector in settings: `us`, `gb`, `eg`
- Pull-to-refresh news list
- Share article link
- Guest mode (favorite button shown but disabled)
- Placeholder image for missing/broken article images
- Clear empty/error state messages on the news list screen

## Project Structure

Main source path: `app/src/main/java/com/example/newsapp/`

- `activities/`
  - `MainActivity.kt` (category screen)
  - `NewsListActivity.kt` (headlines list)
  - `SettingsActivity.kt` (country settings)
- `adapter/`
  - `CategoryAdapter.kt`
  - `NewsAdapter.kt`
- `shared/`
  - `NewsApiService.kt` (Retrofit API interface)
  - `News.kt` (`News` and `Article` models)
- `util/`
  - `AppSettings.kt` (country preference helper)