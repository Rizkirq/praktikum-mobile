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
        Schema::table('users', function (Blueprint $table) {
            // Pastikan email_verified_at sudah ada, jika belum, tambahkan
            if (!Schema::hasColumn('users', 'email_verified_at')) {
                $table->timestamp('email_verified_at')->nullable()->after('email');
            }
            $table->string('verification_token')->nullable()->after('password');
            $table->timestamp('verification_token_expires_at')->nullable()->after('verification_token');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->dropColumn('verification_token');
            $table->dropColumn('verification_token_expires_at');
            // Hati-hati jika email_verified_at dibuat di sini, pastikan tidak dihapus jika sudah ada
            // $table->dropColumn('email_verified_at'); // Biasanya tidak perlu dihapus di down() jika sudah default
        });
    }
};
