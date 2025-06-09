<?php

namespace App\Http\Controllers\Api;
use App\Http\Controllers\Controller;
use App\Models\Buku;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;

class BukuController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $data = Buku::with('author')->orderBy('judul','asc',)->get();
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
        $dataBuku = new Buku;

        $rules = [
            'judul' =>'required',
            'genre' => 'nullable|string|max:255',
            'tanggal_publikasi' => 'required|date',
            'deskripsi' => 'nullable|string',
            'rating' => 'nullable|numeric|min:0|max:5|regex:/^\d+(\.\d{1})?$/',
            'author_id' => 'required|exists:author,id'
        ];
        $validator = Validator::make($request->all(), $rules);

        if($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal memasukan data',
                'data' => $validator->errors()
            ],404);
        }

        $dataBuku = Buku::create([
            'judul' => $request->judul,
            'genre' => $request->genre,
            'tanggal_publikasi' => $request->tanggal_publikasi,
            'deskripsi' => $request->deskripsi,
            'rating' => $request->rating,
            'author_id' => $request-> author_id
        ]);
        // $dataBuku->judul = $request->judul;
        // $dataBuku->pengarang = $request->pengarang;
        // $dataBuku->tanggal_publikasi = $request->tanggal_publikasi;

        // $post = $dataBuku->save();

        return response()->json([
            'status' => true,
            'message' => 'Sukses memasukan data',
            'data' => $dataBuku->load('author')
        ],201);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        $data = Buku::with('author')->find($id);
        if($data){
            return response()->json([
                'status' => true,
                'message' => 'Data ditemukan',
                'data' => $data
            ],200);
        } else {
            return response()->json([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ],404);
        }
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $dataBuku = Buku::find($id); 
        if (empty($dataBuku)) {
            return response()->json([
                'status' => false,
                'message' => 'Data tidak ditemukan'
            ], 404);
        }

        $rules = [
            'judul' =>'required',
            'genre' => 'nullable|string|max:255',
            'tanggal_publikasi' => 'required|date',
            'deskripsi' => 'nullable|string',
            'rating' => 'nullable|numeric|min:0|max:5|regex:/^\d+(\.\d{1})?$/',
            'author_id' => 'required|exists:authors,id'
        ];

        $validator = Validator::make($request->all(), $rules);
        if($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => 'Gagal melakukan update data',
                'data' => $validator->errors()
            ],404);
        }

        $dataBuku->judul = $request->judul;
        $dataBuku->genre = $request->genre;
        $dataBuku->tanggal_publikasi = $request->tanggal_publikasi;
        $dataBuku->deskripsi = $request->deskripsi;
         $dataBuku->rating = $request->rating;
        $dataBuku->author_id = $request->author_id;

        $post = $dataBuku->save();

        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan update data',
            'data' => $dataBuku->load('author')
        ]);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
            $dataBuku = Buku::find($id); 
             if (empty($dataBuku)) {
                    return response()->json([
                      'status' => false,
                     'message' => 'Data tidak ditemukan'
                 ], 404);
              }

        $post = $dataBuku->delete();

        return response()->json([
            'status' => true,
            'message' => 'Sukses melakukan delete data'
        ]);
    }
}
