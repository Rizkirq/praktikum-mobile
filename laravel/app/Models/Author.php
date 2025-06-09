<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Author extends Model
{
     use HasFactory;

    protected $fillable = ['name', 'biografi', 'birth_date'];

    public function buku() 
    {
        return $this->hasMany(Buku::class);
    }
}
