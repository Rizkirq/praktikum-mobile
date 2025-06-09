<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Author;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AuthorController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $authors = Author::with('buku')->get(); 
        return response()->json([
            'status' => true,
            'message' => 'Data pengarang ditemukan',
            'data' => $authors
        ]);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $rules = [
            'name' => 'required|string|max:255',
            'bio' => 'nullable|string',
            'birth_date' => 'nullable|date',
        ];

        $validator = Validator::make($request->all(), $rules);

        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal memasukan data pengarang',
                'data' => $validator->errors()
            ], 400);
        }

        $author = Author::create($request->all());
        return response()->json([
            'status' => true,
            'message' => 'Sukses memasukan data pengarang',
            'data' => $author
        ], 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(Author $author) 
    {

        return response()->json([
            'status' => true,
            'message' => 'Data pengarang ditemukan',
            'data' => $author->load('buku')
        ]);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Author $author) 
    {
        $rules = [
            'name' => 'required|string|max:255',
            'bio' => 'nullable|string',
            'birth_date' => 'nullable|date',
        ];

        $validator = Validator::make($request->all(), $rules);

        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal melakukan update data pengarang',
                'data' => $validator->errors()
            ], 400);
        }

        $author->update($request->all());
        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan update data pengarang',
            'data' => $author
        ]);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Author $author) 
    {
        $author->delete();
        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan delete data pengarang'
        ], 204); // 204 No Content
    }
}