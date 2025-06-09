<?php

namespace Database\Seeders;

use App\Models\Buku;
use App\Models\Author; 
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Faker\Factory as Faker;

class BukuSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $faker = Faker::create('id_ID');
        $numAuthors = 5; 
        for($i = 0; $i < $numAuthors; $i++){
            Author::create([
                'name' => $faker->name,
                'biografi' => $faker->paragraph,
                'birth_date' => $faker->date(),
            ]);
        }

        $authorIds = Author::pluck('id')->toArray();


        $numBooks = 20; 
        $genres = ['Fiksi', 'Fantasi', 'Sains Fiksi', 'Horor', 'Roman', 'Misteri', 'Biografi', 'Sejarah', 'Thriller', 'Komedi']; // Contoh genre
        for($i = 0; $i < $numBooks; $i++){
            Buku::create([
                'judul' => $faker->sentence(rand(3, 7)), 
                'genre' => $faker->randomElement($genres), 
                'tanggal_publikasi' => $faker->date(),
                'deskripsi' => $faker->paragraphs(3, true), 
                'rating' => $faker->randomFloat(1, 1, 5), 
                'author_id' => $faker->randomElement($authorIds) 
            ]);
        }
    }
}