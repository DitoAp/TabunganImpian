<?php

namespace App\Http\Controllers;

use App\Models\Tabungan;
use App\Models\TabunganItems;
use Illuminate\Http\Request;

class TabunganItemsController extends Controller
{
    public function getData($id)
    {
        $data = TabunganItems::with('tabungan')->where('tabungan_id', $id)->get();
        return response()->json(['data' => $data]);
    }

    public function store(Request $request)
    {
        $this->increment($request);
        return TabunganItems::insert([
            'tabungan_id' => $request->tabungan,
            'category' => $request->category,
            'currency' => $request->currency
        ])
            ? response()->json(['message' => 'success'])
            : response()->json(['message' => 'error']);
    }

    public function put(Request $request)
    {
        $this->decrement($request);
        return TabunganItems::insert([
            'tabungan_id' => $request->tabungan,
            'category' => $request->category,
            'currency' => $request->currency
        ])
            ? response()->json(['message' => 'success'])
            : response()->json(['message' => 'error']);
    }

    public function destroy($id)
    {
        $data = TabunganItems::where('id', $id)->first();
        if ($data->category === 'menabung') {
            $total = Tabungan::where('id', $data->tabungan_id)->first();
            $total -= $data->currency;
            Tabungan::where('id', $data->tabungan_id)->update([
                'total' => $total
            ]);
            return TabunganItems::where('id', $id)->delete()
                ? response()->json(['message' => 'success'])
                : response()->json(['message' => 'error']);
        } else {
            $total = Tabungan::where('id', $data->tabungan_id)->first();
            $total += $data->currency;
            Tabungan::where('id', $data->tabungan_id)->update([
                'total' => $total
            ]);
            return TabunganItems::where('id', $id)->delete()
                ? response()->json(['message' => 'success'])
                : response()->json(['message' => 'error']);
        }
    }

    public function increment($request)
    {
        $data = Tabungan::where('id', $request->tabungan)->first();
        $total = $data->total;
        $total += $request->currency;
        if ($data->total === $data->target) {
            Tabungan::where('id', $request->tabungan)->update([
                'total' => $total,
                'status' => true
            ]);
        }
        Tabungan::where('id', $request->tabungan)->update([
            'total' => $total
        ]);
    }

    public function decrement($request)
    {
        $data = Tabungan::where('id', $request->tabungan)->first();
        $total = $data->total;
        $total -= $request->currency;
        Tabungan::where('id', $request->tabungan)->update([
            'total' => $total
        ]);
    }
}
