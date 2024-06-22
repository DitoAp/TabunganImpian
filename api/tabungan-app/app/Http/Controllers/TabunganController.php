<?php

namespace App\Http\Controllers;

use App\Models\Tabungan;
use Illuminate\Http\Request;

class TabunganController extends Controller
{
    public function getData() {
        return response()->json(['data' => Tabungan::get()]);
    }

    public function getSuccess() {
        return response()->json(['data' => Tabungan::where('status', true)->get()]);
    }

    public function store(Request $request) {
        $name = Tabungan::where('name', $request->name)->first();
        if ($name) {
            return response()->json(['message' => 'Nama tabungan telah dipakai']);
        }
        return Tabungan::insert([
            'name' => $request->name,
            'tipe' => $request->tipe,
            'target' => $request->target,
            'total' => $request->total,
            'image' => $request->image
        ])
        ? response()->json(['message' => 'success'])
        : response()->json(['message' => 'error']);
    }

    public function update(Request $request, $id) {
        $data = Tabungan::where('name', $request->name)->first();
        if ($data) {
            if ($data->name === $request->name && (int) $id === (int) $data->id) {
                return Tabungan::where('id', $id)->update([
                    'name' => $request->name,
                    'tipe' => $request->tipe,
                    'target' => $request->target,
                    'total' => $request->total,
                    'image' => $request->image
                ]) 
                ? response()->json(['message' => 'success'])
                : response()->json(['message' => 'error']); 
            } else {
                return response()->json(['message' => 'Nama tabungan telah dipakai !!!']);
            }
        } else {
            return Tabungan::where('id', $id)->update([
                'name' => $request->name,
                'tipe' => $request->tipe,
                'target' => $request->target,
                'total' => $request->total,
                'image' => $request->image
            ]) 
            ? response()->json(['message' => 'success'])
            : response()->json(['message' => 'error']); 
        }
    }

    public function destroy($id) {
        return Tabungan::where('id', $id)->delete()
        ? response()->json(['message' => 'success'])
        : response()->json(['message' => 'error']);
    }
}
