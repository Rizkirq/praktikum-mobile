<?php

namespace Database\Seeders;

use App\Models\Buku;
use App\Models\Author;
use App\Models\Genre;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Faker\Factory as Faker;
use Illuminate\Support\Str;

class BukuSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = Faker::create('id_ID');

        // --- 1. Seed Authors ---
        // Kosongkan tabel authors sebelum seeding untuk menghindari duplikasi jika dijalankan berkali-kali
        // Author::truncate(); // Gunakan ini jika Anda ingin mengosongkan tabel authors setiap kali seeder dijalankan
        $numAuthors = 5;
        for ($i = 0; $i < $numAuthors; $i++) {
            Author::firstOrCreate(
                ['name' => $faker->unique()->name], // Gunakan unique() untuk nama
                [
                    'biografi' => $faker->paragraph,
                    'birth_date' => $faker->date(),
                ]
            );
        }
        $authorIds = Author::pluck('id')->toArray();

        // --- 2. Seed Genres ---
        // Kosongkan tabel genres sebelum seeding untuk menghindari duplikasi jika dijalankan berkali-kali
        // Genre::truncate(); // Gunakan ini jika Anda ingin mengosongkan tabel genres setiap kali seeder dijalankan
        $initialGenres = [
            'Fiksi',
            'Fantasi',
            'Sains Fiksi',
            'Horor',
            'Roman',
            'Misteri',
            'Biografi',
            'Sejarah',
            'Thriller',
            'Komedi',
            'Drama',
            'Petualangan',
            'Anak-anak',
            'Edukasi',
            'Memoir'
        ];
        foreach ($initialGenres as $genreName) {
            Genre::firstOrCreate(['name' => $genreName]);
        }
        $genreIds = Genre::pluck('id')->toArray(); // Dapatkan semua ID genre yang tersedia

        // --- 3. Seed Books ---
        // Kosongkan tabel buku sebelum seeding untuk menghindari duplikasi jika dijalankan berkali-kali
        // Buku::truncate(); // Gunakan ini jika Anda ingin mengosongkan tabel buku setiap kali seeder dijalankan
        $numBooks = 20;

        // --- URL Gambar Cover dari picsum.photos ---
        $picsumBaseUrl = 'https://picsum.photos/seed/';
        $imageSizes = ['200/300', '300/400', '250/350']; // Berbagai ukuran gambar

        for ($i = 0; $i < $numBooks; $i++) {
            // Buat seed unik untuk setiap gambar agar mendapatkan gambar yang berbeda
            $imageSeed = Str::random(10);
            $imageSize = $faker->randomElement($imageSizes);
            $coverImageUrl = $picsumBaseUrl . $imageSeed . '/' . $imageSize;

            $buku = Buku::create([
                'judul' => $faker->sentence(rand(3, 7)),
                'tanggal_publikasi' => $faker->date(),
                'deskripsi' => $faker->paragraphs(3, true),
                'rating' => $faker->randomFloat(1, 1, 5),
                'author_id' => $faker->randomElement($authorIds),
                'cover_image_url' => $coverImageUrl, // Menggunakan URL picsum.photos
            ]);

            // --- 4. Attach Genres to Books (Many-to-Many) ---
            $randomGenreIds = $faker->randomElements($genreIds, rand(1, min(3, count($genreIds))));
            $buku->genres()->attach($randomGenreIds);
        }
    }
}
