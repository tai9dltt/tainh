<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use Yajra\DataTables\Facades\DataTables;

use function PHPUnit\Framework\returnSelf;

class UsersController extends Controller
{

    public function index()
    {
        return view('user');
    }

    public function getUser(Request $request)
    {
        // if ($request->ajax()) {
        //     // $users = User::select(['id', 'name', 'email', 'password', 'created_at', 'updated_at']);
        //     $users = User::all();
        //     return Datatables::of($users)
        //         ->addColumn('action', function ($user) {
        //             return '<a href="#edit-'.$user->id.'" class="btn btn-xs btn-primary">
        //                 <i class="glyphicon glyphicon-edit"></i> Edit</a>';
        //         })
        //         ->editColumn('id', '{{$id}}')
        //         ->toJson();
        // }

        // return view('user');

        ## Ajax request
        $draw = $request->get('draw');
        $start = $request->get('start');
        $rowperpage = $request->get('length');

        $columnIndex_arr = $request->get('order');
        $columnName_arr = $request->get('columns');
        $order_arr = $request->get('order');
        $search_arr = $request->get('search');

        $columnIndex = $columnIndex_arr[0]['column']; // Column index
        $columnName = $columnName_arr[$columnIndex]['data']; // Column name
        $columnSortOrder = $order_arr[0]['dir']; // asc or desc
        $searchValue = $search_arr['value']; // Search value

        // Total records
        $totalRecords = User::select('count(*) as allcount')->count();
        $totalRecordswithFilter = User::select('count(*) as allcount')->where('name', 'like', '%' . $searchValue . '%')->count();

        // Fetch records
        $records = User::orderBy($columnName, $columnSortOrder)
            ->where('users.name', 'like', '%' . $searchValue . '%')
            ->select('users.*')
            ->skip($start)
            ->take($rowperpage)
            ->get();

        $data_arr = array();

        foreach ($records as $record) {
            $id = $record->id;
            $name = $record->name;
            $email = $record->email;
            $created_at = $record->created_at;
            $updated_at = $record->updated_at;

            $data_arr[] = array(
                "id" => $id,
                "name" => $name,
                "email" => $email,
                "updated_at"=>$created_at,
                "created_at"=>$updated_at
            );
        }
        $response = array(
            "draw" => intval($draw),
            "iTotalRecords" => $totalRecords,
            "iTotalDisplayRecords" => $totalRecordswithFilter,
            "aaData" => $data_arr
         );
         echo json_encode($response);
         exit;  
    }
}
