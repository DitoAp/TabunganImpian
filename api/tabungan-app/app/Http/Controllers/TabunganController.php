<?php

namespace App\Http\Controllers;

use App\Models\Tabungan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class TabunganController extends Controller
{
    public function getData()
    {
        return response()->json(['data' => Tabungan::get()]);
    }

    public function getSuccess()
    {
        return response()->json(['data' => Tabungan::where('status', true)->get()]);
    }

    public function store(Request $request)
    {
        $name = Tabungan::where('name', $request->name)->first();
        if ($name) {
            return response()->json(['message' => 'Nama tabungan telah dipakai']);
        }
        return Tabungan::insert([
            'name' => $request->name,
            'tipe' => $request->tipe,
            'target' => $request->target,
            'total' => $request->total,
            'menabung' => $request->menabung,
            'image' => $this->storeImage($request)
        ])
            ? response()->json(['message' => 'success'])
            : response()->json(['message' => 'error']);
    }

    public function storeImage($request)
    {
        $image = $request->file('image');
        $image_uploaded_path = $image->store('tabungan', 'public');
        return Storage::url($image_uploaded_path);
    }

    public function deleteImage($path)
    {
        $slice = explode('/', $path);
        File::delete('storage/tabungan/' . $slice[3]);
    }

    public function update(Request $request, $id)
    {
        $data = Tabungan::where('name', $request->name)->first();
        if ($data) {
            if ($data->name === $request->name && (int) $id === (int) $data->id) {
                return Tabungan::where('id', $id)->update([
                    'name' => $request->name,
                    'tipe' => $request->tipe,
                    'target' => $request->target,
                    'total' => $request->total,
                    'menabung' => $request->menabung
                ])
                    ? response()->json(['message' => 'success'])
                    : response()->json(['message' => 'error']);
            } else if ($data->id === (int) $id) {
                return response()->json(['message' => 'Nama tabungan telah dipakai !!!']);
            }
        } else {
            return Tabungan::where('id', $id)->update([
                'name' => $request->name,
                'tipe' => $request->tipe,
                'target' => $request->target,
                'total' => $request->total,
                'menabung' => $request->menabung
            ])
                ? response()->json(['message' => 'success'])
                : response()->json(['message' => 'error']);
        }
    }

    public function updateImage(Request $request) {
        $data = Tabungan::where('id', $request->id)->first();
        // $this->deleteImage($data->image);
        return $data->update([
            'image' => $this->storeImage($request->file('image'))
        ])
        ? response()->json(['message' => 'success'])
        : response()->json(['error' => 'error']);
    }

    public function getDetail($id)
    {
        $data = Tabungan::where('id', $id)->first();
        return response()->json(['data' => $data]);
    }

    public function destroy($id)
    {
        $data = Tabungan::where('id', $id)->first();
        $this->deleteImage($data->image);
        return $data->delete()
            ? response()->json(['message' => 'success'])
            : response()->json(['message' => 'error']);
    }
}
