<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('buku', function (Blueprint $table) {
            // Pastikan kolom 'genre' ada sebelum mencoba menghapusnya
            if (Schema::hasColumn('buku', 'genre')) {
                $table->dropColumn('genre'); // Hapus kolom genre lama
            }
            $table->string('cover_image_url')->nullable()->after('rating'); // Tambah kolom URL gambar cover
        });
    }

    public function down(): void
    {
        Schema::table('buku', function (Blueprint $table) {
            $table->dropColumn('cover_image_url');
            // Opsional: jika ingin mengembalikan kolom genre lama saat rollback
            // $table->string('genre')->nullable()->after('judul');
        });
    }
};
