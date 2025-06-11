<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Genre extends Model
{
    use HasFactory;

    protected $fillable = ['name'];

    public function buku()
    {
        return $this->belongsToMany(Buku::class, 'buku_genre', 'genre_id', 'buku_id');
    }
}
