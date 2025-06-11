<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Genre; // <-- Import model Genre
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator; // <-- Import ini

class GenreController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $genres = Genre::all(); // Mengambil semua genre
        return response()->json([
            'status' => true,
            'message' => 'Data genre ditemukan',
            'data' => $genres
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $rules = [
            'name' => 'required|string|max:255|unique:genres,name', // Nama genre harus unik
        ];
        $validator = Validator::make($request->all(), $rules);

        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal memasukkan data genre',
                'data' => $validator->errors()
            ], 422);
        }

        $genre = Genre::create($request->all());
        return response()->json([
            'status' => true,
            'message' => 'Sukses memasukkan data genre',
            'data' => $genre
        ], 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(Genre $genre) // Menggunakan Route Model Binding
    {
        return response()->json([
            'status' => true,
            'message' => 'Data genre ditemukan',
            'data' => $genre
        ], 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Genre $genre) // Menggunakan Route Model Binding
    {
        $rules = [
            'name' => 'required|string|max:255|unique:genres,name,' . $genre->id, // Unique kecuali ID ini sendiri
        ];
        $validator = Validator::make($request->all(), $rules);

        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal melakukan update data genre',
                'data' => $validator->errors()
            ], 422);
        }

        $genre->update($request->all());
        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan update data genre',
            'data' => $genre
        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Genre $genre) // Menggunakan Route Model Binding
    {
        $genre->delete();
        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan delete data genre'
        ], 204); // 204 No Content
    }
}
