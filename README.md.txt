# Hotel DMS - Java & SQLite

This is a Hotel Data Management System created for my Software Development course.

## Features
- Connect to a SQLite database at runtime (no hardcoded path)
- Add, view, update, and delete reservations
- Input validation for name, email, room number, and dates
- Auto-filled rate based on room type
- Search reservations by guest name

## How to Run

1. Launch HotelGUI.java in IntelliJ (or any Java IDE).
2. Click "Connect DB" and select 'hotel.db' provided in the same folder.
3. Once connected, you can Add, View, Update, Delete, and Search reservations.
4. All validation is handled through the interface.
5. Make sure you have sqlite-jdbc in the classpath if using a different IDE.

## How to Recreate the Database from `hotel_export.sql`

If you don't have the `hotel.db` file, you can generate it using the `hotel_export.sql`.

### Requirements
- SQLite installed (or `sqlite3.exe` present in your project folder)

### Steps (Windows):

Open **Command Prompt** and navigate to the folder where `sqlite3.exe` and `hotel_export.sql` are located:

   ```bash
   cd "C:\path\to\your\project\folder"

## Author
Andres Vera  
CEN 3024 - Summer 2025