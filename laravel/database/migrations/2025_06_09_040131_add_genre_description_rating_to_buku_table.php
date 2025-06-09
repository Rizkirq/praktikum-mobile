<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::table('buku', function (Blueprint $table) {
            $table->string('genre')->nullable()->after('judul'); 
            $table->text('deskripsi')->nullable()->after('tanggal_publikasi');
            $table->decimal('rating', 2, 1)->nullable()->after('deskripsi'); 
           
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('buku', function (Blueprint $table) {
            $table->dropColumn('genre');
            $table->dropColumn('deskripsi');
            $table->dropColumn('rating');
        });
    }
};