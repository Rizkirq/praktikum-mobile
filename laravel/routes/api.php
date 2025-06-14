<?php

use App\Http\Controllers\Api\AuthorController;
use App\Http\Controllers\Api\BukuController;
use App\Http\Controllers\Api\AuthController;
use App\Http\Controllers\Api\GenreController;
use App\Http\Controllers\Api\PasswordResetController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

// Route::middleware('auth:sanctum')->get('/user', function (Request $request){
//     return $request->user();
// });

// Route::get('/buku', [BukuController::class,'index']);    
// Route::get('/buku/{id}', [BukuController::class,'show']);    
// Route::post('/buku', [BukuController::class,'store']);    
// Route::put('/buku/{id}', [BukuController::class,'update']);    
// Route::delete('/buku/{id}', [BukuController::class,'destroy']);    

route::apiResource('buku', BukuController::class);
route::apiResource('authors', AuthorController::class);
route::apiResource('genres', GenreController::class);

Route::post('/login', [AuthController::class, 'login']);
Route::post('/register', [AuthController::class, 'register']);
Route::post('/verify-email', [AuthController::class, 'verifyEmail']);
Route::post('/resend-verification-code', [AuthController::class, 'resendVerificationCode']); 
Route::middleware('auth:sanctum')->post('/logout', [AuthController::class, 'logout']);

Route::post('/forgot-password-request', [PasswordResetController::class, 'sendResetLinkEmail']);
Route::post('/reset-password', [PasswordResetController::class, 'reset']);
Route::post('/check-reset-token', [PasswordResetController::class, 'checkToken']);
