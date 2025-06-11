<?php

namespace App\Http\Controllers\Api;
use App\Http\Controllers\Controller;
use App\Models\Buku;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use App\Models\Author;

class BukuController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $data = Buku::with('author', 'genres')->orderBy('judul','asc',)->get();
        return response()->json([   
            'status' => true,
            'message'=> 'Data ditemukan',
            'data' => $data
        ],200);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $rules = [
            'judul' =>'required',
            'tanggal_publikasi' => 'required|date',
            'deskripsi' => 'nullable|string',
            'rating' => 'nullable|numeric|min:0|max:5|regex:/^\d+(\.\d{1})?$/',
            'author_id' => 'required|exists:author,id',
            'cover_image_url' => 'nullable|url|max:255', 
            'genre_ids' => 'nullable|array', 
            'genre_ids.*' => 'exists:genres,id',
        ];
        $validator = Validator::make($request->all(), $rules);

        if($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal memasukan data',
                'data' => $validator->errors()
            ],422);
        }

        $buku = Buku::create([ 
            'judul' => $request->judul,
            'tanggal_publikasi' => $request->tanggal_publikasi,
            'deskripsi' => $request->deskripsi,
            'rating' => $request->rating,
            'author_id' => $request->author_id,
            'cover_image_url' => $request->cover_image_url, 
        ]);

        if ($request->has('genre_ids')) {
            $buku->genres()->sync($request->genre_ids);
        }

        return response()->json([
            'status' => true,
            'message' => 'Sukses memasukan data',
            'data' => $buku->load('author', 'genres') 
        ], 201);
    }

    public function show(string $id)
    {
        $data = Buku::with('author', 'genres')->find($id);
        if ($data) {
            return response()->json([
                'status' => true,
                'message' => 'Data ditemukan',
                'data' => $data
            ], 200);
        } else {
            return response()->json([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }
    }

    public function update(Request $request, string $id)
    {
        $buku = Buku::find($id); 
        if (empty($buku)) {
            return response()->json([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }

        $rules = [
            'judul' => 'required',
            'tanggal_publikasi' => 'required|date',
            'deskripsi' => 'nullable|string',
            'rating' => 'nullable|numeric|min:0|max:5|regex:/^\d+(\.\d{1})?$/',
            'author_id' => 'required|exists:authors,id',
            'cover_image_url' => 'nullable|url|max:255', 
            'genre_ids' => 'nullable|array', 
            'genre_ids.*' => 'exists:genres,id', 
        ];

        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal melakukan update data',
                'data' => $validator->errors()
            ], 422); 
        }

        $buku->judul = $request->judul;
        $buku->tanggal_publikasi = $request->tanggal_publikasi;
        $buku->deskripsi = $request->deskripsi;
        $buku->rating = $request->rating;
        $buku->author_id = $request->author_id;
        $buku->cover_image_url = $request->cover_image_url; 
        $buku->save();

        if ($request->has('genre_ids')) {
            $buku->genres()->sync($request->genre_ids);
        } else {
            // Opsional: jika genre_ids tidak dikirim, bisa asumsikan ingin menghapus semua genre
            // $buku->genres()->detach();
        }

        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan update data',
            'data' => $buku->load('author', 'genres') 
        ]);
    }

    public function destroy(string $id)
    {
        $buku = Buku::find($id);
        if (empty($buku)) {
            return response()->json([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }

        $buku->genres()->detach();

        $buku->delete();

        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan delete data'
        ], 204);
    }
}
