# praktikum-mobile

Repositori ini berisi proyek pengembangan aplikasi mobile dan web sebagai bagian dari tugas/praktikum. Proyek ini terbagi menjadi dua bagian utama: sebuah aplikasi mobile dan sebuah sistem backend/web menggunakan framework Laravel.

## Struktur Proyek

Repositori ini memiliki dua folder utama:

1.  **`BookApp/`**:
    * Berisi kode sumber untuk **aplikasi mobile**.
    * Dikembangkan menggunakan **Kotlin**, bahasa pemrograman modern untuk pengembangan aplikasi Android.
    * **[Deskripsi Singkat Aplikasi BookApp Anda]**
        * *Contoh: Aplikasi ini memungkinkan pengguna untuk membaca buku digital, melihat riwayat bacaan, dan menyimpan bookmark. Fitur-fitur utama meliputi daftar buku, pencarian, dan manajemen profil.*
        * *Contoh: Aplikasi mobile untuk mengelola koleksi buku pribadi, dengan fitur menambah, mengedit, dan menghapus data buku.*

2.  **`laravel/`**:
    * Berisi kode sumber untuk **sistem backend atau aplikasi web** yang mendukung proyek.
    * Dikembangkan menggunakan **PHP** dengan framework **Laravel** dan *templating engine* **Blade**.
    * **[Deskripsi Singkat Proyek Laravel Anda]**
        * *Contoh: Backend ini menyediakan API untuk aplikasi mobile BookApp, mengelola data pengguna, data buku, otentikasi, dan fungsionalitas lainnya.*
        * *Contoh: Sistem manajemen konten berbasis web untuk mengelola daftar buku, kategori, dan pengguna yang terhubung dengan aplikasi mobile.*

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

1.  Pastikan Anda memiliki Android Studio terinstal.
2.  Buka folder `BookApp` di Android Studio.
3.  Sinkronkan proyek dengan Gradle.
4.  Jalankan aplikasi di emulator atau perangkat Android fisik.

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
8.  Jalankan server pengembangan Laravel:
    ```bash
    php artisan serve
    ```
    Aplikasi akan berjalan di `http://127.0.0.1:8000` (atau port lain yang ditampilkan).

## Kontribusi

Jika Anda ingin berkontribusi pada proyek ini, silakan ikuti langkah-langkah berikut:
1.  Fork repositori ini.
2.  Buat branch baru: `git checkout -b fitur/nama-fitur-baru`
3.  Lakukan perubahan dan commit: `git commit -m "Menambahkan fitur baru"`
4.  Push ke branch Anda: `git push origin fitur/nama-fitur-baru`
5.  Buat Pull Request.

---
