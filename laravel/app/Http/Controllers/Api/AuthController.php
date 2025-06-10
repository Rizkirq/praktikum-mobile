<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str; 
use Illuminate\Support\Facades\Mail;
use App\Mail\VerifyUserEmail;
use Carbon\Carbon;
use App\Models\User;
use App\Http\Controllers\Controller;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Log;

class AuthController extends Controller
{
    public function login(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'password' => 'required',
        ]);

        $user = User::where('email', $request->email)->first();

        $user = User::where('email', $request->email)->first();

        if (!$user || !Hash::check($request->password, $user->password)) {
            return response()->json([
                'error' => true,
                'message' => 'Email or password is incorrect'
            ], 400); 
        }

        if (is_null($user->email_verified_at)) {
            return response()->json([
                'error' => true,
                'message' => 'Your email is not verified. Please check your inbox for a verification code.'
            ], 403); 
        }

        $token = $user->createToken('auth_token')->plainTextToken;

        return response()->json([
            'error' => false,
            'message' => 'Login Successful',
            'loginResult' => [
                'userId' => $user->id,
                'name' => $user->name,
                'token' => $token
            ]
        ]);
    }
    public function register(Request $request) 
    {
        try {
            $request->validate([
                'name' => 'required|string|max:255',
                'email' => 'required|string|email|max:255|unique:users',
                'password' => 'required|string|min:8|confirmed',
            ]);
        } catch (ValidationException $e) {
            return response()->json([
                'error' => true,
                'message' => 'Validation failed',
                'errors' => $e->errors()
            ], 422);
        }

        $verificationToken = '';
        for ($i = 0; $i < 6; $i++) {
            $verificationToken .= mt_rand(0, 9); 
        }
        $expiresAt = Carbon::now()->addMinutes(10);

        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'verification_token' => $verificationToken, 
            'verification_token_expires_at' => $expiresAt, 
            'email_verified_at' => null, 
        ]);

        try {
            Mail::to($user->email)->send(new VerifyUserEmail($user, $verificationToken));
        } catch (\Exception $e) {
            // Opsional: Log error jika email gagal dikirim
            // Atau hapus user jika pengiriman email adalah wajib
            Log::error("Failed to send verification email to {$user->email}: {$e->getMessage()}");
            return response()->json([
                'error' => true,
                'message' => 'Registration successful, but failed to send verification email. Please try again later.'
            ], 500);
        }

        return response()->json([
            'error' => false,
            'message' => 'Registration Successful. Please check your email for verification code.',
            // 'loginResult' => [ ... ]
        ], 201);
    }

    public function verifyEmail(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'verification_code' => 'required|string|size:6', // Sesuaikan ukuran kode
        ]);

        $user = User::where('email', $request->email)->first();

        if (!$user) {
            return response()->json([
                'error' => true,
                'message' => 'User not found.'
            ], 404);
        }

        if ($user->email_verified_at) {
            return response()->json([
                'error' => false,
                'message' => 'Email already verified.'
            ]);
        }

        // Cek token dan kadaluarsa
        if ($user->verification_token !== $request->verification_code || Carbon::now()->isAfter($user->verification_token_expires_at)) {
            return response()->json([
                'error' => true,
                'message' => 'Invalid or expired verification code.'
            ], 400);
        }

        // Verifikasi email
        $user->email_verified_at = Carbon::now();
        $user->verification_token = null; // Hapus token setelah digunakan
        $user->verification_token_expires_at = null;
        $user->save();

        // Opsional: Langsung login user setelah verifikasi
        $token = $user->createToken('auth_token')->plainTextToken;

        return response()->json([
            'error' => false,
            'message' => 'Email verified successfully. You are now logged in.',
            'loginResult' => [
                'userId' => $user->id,
                'name' => $user->name,
                'token' => $token
            ]
        ]);
    }

    public function resendVerificationCode(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
        ]);

        $user = User::where('email', $request->email)->first();

        if (!$user) {
            return response()->json([
                'error' => true,
                'message' => 'User not found.'
            ], 404);
        }

        if ($user->email_verified_at) {
            return response()->json([
                'error' => false,
                'message' => 'Email already verified.'
            ]);
        }

        // Generate token baru
        $verificationToken = '';
        for ($i = 0; $i < 6; $i++) {
            $verificationToken .= mt_rand(0, 9);
        }
        $expiresAt = Carbon::now()->addMinutes(10);

        $user->verification_token = $verificationToken;
        $user->verification_token_expires_at = $expiresAt;
        $user->save();

        try {
            Mail::to($user->email)->send(new VerifyUserEmail($user, $verificationToken));
        } catch (\Exception $e) {
            // \Log::error("Failed to resend verification email to {$user->email}: {$e->getMessage()}");
            return response()->json([
                'error' => true,
                'message' => 'Failed to resend verification email. Please try again later.'
            ], 500);
        }

        return response()->json([
            'error' => false,
            'message' => 'Verification code re-sent successfully. Please check your email.'
        ]);
    }



    public function logout(Request $request)
    {
        $request->user()->currentAccessToken()->delete();

        return response()->json([
            'message' => 'Logged out successfully'
        ]);
    }
}
