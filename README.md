# NotesMiniApp

A simple and intuitive Android application for managing daily tasks and notes. This project was developed as part of a mobile application development assignment to demonstrate proficiency in modern Android development practices.

## Features
- **Create Notes**: Quickly add new notes with a title and detailed content.
- **Edit Notes**: Seamlessly update existing notes by tapping on any item in the list.
- **Delete Notes**: Easily remove unwanted notes using a long-press gesture.
- **Task Management**: Mark notes as "Completed" using a checkbox, which provides visual feedback via a strike-through effect.
- **Local Persistence**: All data is stored locally using the Room database, ensuring notes are available offline.
- **Real-time UI Updates**: Utilizes LiveData to observe database changes and update the user interface automatically.

## UI Screenshots


## Design Choices

### 1. Architecture: MVVM (Model-View-ViewModel)
The application follows the **MVVM** pattern to promote a clean separation of concerns and maintainable code:
- **Model**: Handles the data logic (Room entities, DAOs, and the Repository).
- **View**: Responsible for the UI and user interaction.
- **ViewModel**: Acts as a bridge, holding UI-related data and interacting with the Repository while surviving configuration changes (like screen rotations).

### 2. Data Persistence: Room Database
**Room** was chosen for data management because it provides an abstraction layer over SQLite. It offers compile-time checks for SQL queries and integrates perfectly with `LiveData`, allowing for a reactive data flow from the database to the UI.

### 3. Asynchronous Operations: Kotlin Coroutines
To keep the UI responsive, all database operations (Insert, Update, Delete) are performed on background threads using **Kotlin Coroutines**. This prevents the Main Thread from blocking during disk I/O.

### 4. Efficient UI: ListAdapter & DiffUtil
For the notes list, I implemented `RecyclerView` with `ListAdapter`. By using `DiffUtil`, the app calculates exactly what changed in the list and updates only those specific items, resulting in better performance and smoother animations compared to a standard adapter.

### 5. Modern UI Components
- **ViewBinding**: Used to eliminate the need for `findViewById`, reducing boilerplate and increasing type safety.
- **Material Design**: Utilized standard Material components like the Floating Action Button (FAB) and Toolbar for a familiar and consistent Android user experience.

## Technologies Used
- **Language**: Kotlin
- **Database**: Room
- **Async Processing**: Coroutines
- **UI Logic**: LiveData & ViewModel
- **View Layer**: ViewBinding, RecyclerView, ConstraintLayout

---
*Developed for Mobile Application Development Assignment*
