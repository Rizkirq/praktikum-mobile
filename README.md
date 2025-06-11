# BookApp

Repositori ini berisi proyek pengembangan aplikasi mobile dan web sebagai tugas praktikum. Proyek ini terbagi menjadi dua bagian utama: sebuah aplikasi mobile dan sebuah sistem backend/web menggunakan framework Laravel.

## Struktur Proyek

Repositori ini memiliki dua folder utama:

1.  **`BookApp/`**:
    * Berisi kode sumber untuk **aplikasi mobile**.
    * Dikembangkan menggunakan **Kotlin**, bahasa pemrograman modern untuk pengembangan aplikasi Android.
    * **[Deskripsi Singkat Aplikasi BookApp]**
        * Aplikasi ini memungkinkan pengguna untuk membaca buku digital, melihat riwayat bacaan, dan menyimpan bookmark. Fitur-fitur utama meliputi daftar buku, pencarian, dan manajemen profil.*
        * Aplikasi mobile untuk mengelola koleksi buku pribadi, dengan fitur menambah, mengedit, dan menghapus data buku.*

2.  **`laravel/`**:
    * Berisi kode sumber untuk **sistem backend atau aplikasi web** yang mendukung proyek.
    * Dikembangkan menggunakan **PHP** dengan framework **Laravel** dan *templating engine* **Blade**.
    * **[Deskripsi Singkat Proyek Laravel ]**
        * *Backend ini menyediakan API untuk aplikasi mobile BookApp, mengelola data pengguna, data buku, otentikasi, dan fungsionalitas lainnya.*
        * *Sistem manajemen konten berbasis web untuk mengelola daftar buku, kategori, dan pengguna yang terhubung dengan aplikasi mobile.*

## Teknologi yang Digunakan

* **Aplikasi Mobile (`BookApp`):**
    * Kotlin
    * Android SDK

* **Sistem Backend/Web (`laravel`):**
    * PHP
    * Laravel Framework
    * Blade Templating Engine
    * JavaScript

## Cara Menjalankan Proyek (Panduan Umum)

### Untuk Aplikasi Mobile (`BookApp`)

1.  Pastikan  memiliki Android Studio terinstal.
2.  Buka folder `BookApp` di Android Studio.
3.  Sinkronkan proyek dengan Gradle.
4.  Jalankan aplikasi di emulator atau perangkat Android fisik.
5.  **Penting untuk Koneksi API dari Emulator:**
    * Jika backend Laravel  berjalan di `http://127.0.0.1:8080` (atau port lain) di komputer Anda, emulator Android akan mengenali `10.0.2.2` sebagai alamat IP *host* (komputer) Anda.
    * Pastikan URL API di kode Kotlin Anda (misalnya di file konfigurasi atau kelas koneksi API) diarahkan ke `http://10.0.2.2:PORT_LARAVEL/api/...` (ganti `PORT_LARAVEL` dengan port yang Anda gunakan, misalnya `8080`).
    * Contoh perubahan di kode Kotlin (ini adalah contoh, lokasi sebenarnya bisa bervariasi):
        ```kotlin
        // Contoh: di sebuah objek Constants.kt atau sejenisnya
        object ApiConfig {
            const val BASE_URL = "[http://10.0.2.2:8080/api/](http://10.0.2.2:8080/api/)" // Ganti dengan port yang Anda gunakan untuk Laravel
            // Jika di perangkat fisik, gunakan IP lokal komputer Anda di jaringan yang sama, misal: "[http://192.168.1.](http://192.168.1.)XX:8080/api/"
        }
        ```

### Untuk Proyek Laravel (`laravel`)

1.  Pastikan Anda memiliki PHP dan Composer terinstal.
2.  Navigasi ke folder `laravel` di terminal Anda.
3.  Instal dependensi Composer:
    ```bash
    composer install
    ```
4.  Salin file `.env.example` menjadi `.env` dan konfigurasikan database Anda:
    ```bash
    cp .env.example .env
    ```
5.  Buat *application key*:
    ```bash
    php artisan key:generate
    ```
6.  Jalankan migrasi database:
    ```bash
    php artisan migrate
    ```
7.  (Opsional) Jalankan *seeder* untuk data awal:
    ```bash
    php artisan db:seed
    ```
8.  Jalankan server pengembangan Laravel. Anda bisa memilih port yang berbeda, misalnya `8080`:
    ```bash
    php artisan serve --port=8080
    ```
    Atau, jika Anda tidak menentukan port, Laravel akan menggunakan port default `8000`:
    ```bash
    php artisan serve
    ```
    Server akan berjalan di `http://127.0.0.1:PORT_ANDA` (sesuai port yang Anda gunakan).

