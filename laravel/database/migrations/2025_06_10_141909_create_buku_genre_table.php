<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('buku_genre', function (Blueprint $table) {
            $table->foreignId('buku_id')->constrained('buku')->onDelete('cascade');
            $table->foreignId('genre_id')->constrained('genres')->onDelete('cascade');
            $table->primary(['buku_id', 'genre_id']); // Gabungan kunci unik
            $table->timestamps(); // Opsional, jika ingin melacak kapan relasi dibuat
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('buku_genre');
    }
};
