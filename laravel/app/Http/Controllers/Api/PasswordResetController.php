<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Str;
use Carbon\Carbon;
use App\Models\User;
use App\Mail\ResetPasswordNotification;
use Illuminate\Support\Facades\Log;

class PasswordResetController extends Controller
{
    /**
     * Kirim link/token reset password melalui email.
     */
    public function sendResetLinkEmail(Request $request)
    {
        $request->validate(['email' => 'required|email|exists:users,email']);

        $user = User::where('email', $request->email)->first();

        // Hapus token lama jika ada
        DB::table('password_reset_tokens')->where('email', $request->email)->delete();

        // Buat token reset baru (misal 6 digit angka, seperti verifikasi email)
        $token = '';
        for ($i = 0; $i < 6; $i++) {
            $token .= mt_rand(0, 9);
        }

        // Simpan token ke tabel password_reset_tokens
        DB::table('password_reset_tokens')->insert([
            'email' => $request->email,
            'token' => $token,
            'created_at' => Carbon::now()
        ]);

        try {
            // Kirim email notifikasi reset password
            Mail::to($user->email)->send(new ResetPasswordNotification($user, $token));
        } catch (\Exception $e) {
            //\Log::error("Failed to send password reset email to {$user->email}: {$e->getMessage()}");
            return response()->json([
                'error' => true,
                'message' => 'Failed to send password reset email. Please try again later.'
            ], 500);
        }

        return response()->json([
            'error' => false,
            'message' => 'Password reset code sent to your email.'
        ], 200);
    }

    /**
     * Reset password user.
     */
    public function reset(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'token' => 'required|string|size:6', // Pastikan size:6 karena kita buat 6 digit angka
            'password' => 'required|string|min:8|confirmed',
        ]);

        // Temukan token di database
        $passwordReset = DB::table('password_reset_tokens')
            ->where('email', $request->email)
            ->where('token', $request->token)
            ->first();

        if (!$passwordReset || Carbon::parse($passwordReset->created_at)->addMinutes(60)->isBefore(Carbon::now())) { // Token berlaku 60 menit
            return response()->json([
                'error' => true,
                'message' => 'Invalid or expired password reset code.'
            ], 400);
        }

        $user = User::where('email', $request->email)->first();

        if (!$user) {
            return response()->json([
                'error' => true,
                'message' => 'User not found.'
            ], 404);
        }

        // Update password user
        $user->password = Hash::make($request->password);
        $user->save();

        // Hapus token dari database setelah digunakan
        DB::table('password_reset_tokens')->where('email', $request->email)->delete();

        return response()->json([
            'error' => false,
            'message' => 'Password has been reset successfully.'
        ], 200);
    }

    public function checkToken(Request $request)
    {
        $request->validate([
            'email' => 'required|email|exists:users,email',
            'token' => 'required|string|size:6', // Pastikan size:6 karena kita buat 6 digit angka
        ]);

        // Temukan token di database
        $passwordReset = DB::table('password_reset_tokens')
            ->where('email', $request->email)
            ->where('token', $request->token)
            ->first();

        if (!$passwordReset) {
            return response()->json([
                'valid' => false,
                'message' => 'Invalid password reset code.'
            ], 400); // 400 Bad Request karena kode tidak ditemukan
        }

        // Cek apakah token sudah kadaluarsa (60 menit dari created_at)
        if (Carbon::parse($passwordReset->created_at)->addMinutes(60)->isBefore(Carbon::now())) {
            return response()->json([
                'valid' => false,
                'message' => 'Password reset code has expired.'
            ], 400); // 400 Bad Request karena kode kadaluarsa
        }

        return response()->json([
            'valid' => true,
            'message' => 'Password reset code is valid.'
        ], 200);
    }
}
