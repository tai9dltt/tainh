<?php

namespace App\Http\Controllers;

use App\User;
use Redirect, Response;
use Illuminate\Http\Request;
use Yajra\DataTables\Facades\DataTables;

use function PHPUnit\Framework\returnSelf;

class UsersController extends Controller
{

    public function index(Request $request)
    {
        $users = User::latest()->get();

        if ($request->ajax()) {

            $query = User::query();
            $from_date = (!empty($_GET["from_date"])) ? ($_GET["from_date"]) : ('');
            $to_date = (!empty($_GET["to_date"])) ? ($_GET["to_date"]) : ('');


            if ($from_date && $to_date) {

                $from_date = date('Y-m-d', strtotime($from_date));
                $to_date = date('Y-m-d', strtotime($to_date));

                $query->whereRaw("date(users.created_at) >= '" . $from_date . "' AND date(users.created_at) <= '" . $to_date . "'");
            }
            $users = $query->select('*');


            return Datatables::of($users)
                ->addIndexColumn()
                ->addColumn('action', function ($row) {

                    $btn = '<a href="javascript:void(0)" data-toggle="tooltip"  data-id="' . $row->id . '" data-original-title="Edit" class="edit btn btn-primary btn-sm editUser">Edit</a>';

                    $btn = $btn . ' <a href="javascript:void(0)" data-toggle="tooltip"  data-id="' . $row->id . '" data-original-title="Delete" class="btn btn-danger btn-sm deleteUser">Delete</a>';

                    $btn = $btn . ' <a href="javascript:void(0)" data-toggle="tooltip"  data-id="' . $row->id . '" data-original-title="UpdateStatus" class="btn btn-primary btn-sm changeStatus">Update Status</a>';

                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }

        return view('users', compact('users'));
    }

    public function store(Request $request)
    {

        User::updateOrCreate([
            'id' => $request->user_id
        ], [
            'name' => $request->name,
            'email' => $request->email,
            'password' => $request->password,
            'role_id' => $request->role
        ]);

        // return response
        $response = [
            'success' => true,
            'message' => 'User saved successfully.',
        ];
        return response()->json($response, 200);
    }

    public function edit($id)
    {
        $user = User::find($id);
        return response()->json($user);
    }


    public function changeStatus($id)
    {
        $user = User::find($id);
        if (empty($user)) {
            return response()->json([
                'success' => false,
                'message' => 'Item not found! ???? ',


            ], 404);
        } else {
            if ($user->status == 1 || $user->status == 0) {
                $user->status = ($user->status == 1 ? 0 : 1);
                $user->save();

                return response()->json([
                    'success' => true,
                    'message' => 'Item status successfully changed.',
                ], 200);
            } else {
                return response()->json([
                    'success' => false,
                    'message' => 'Item not found!',
                ], 404);
            }
        }

        return response()->json(['success' => 'Status change successfully.']);
    }

    public function destroy(User $user)
    {
        $user->delete();

        // return response
        $response = [
            'success' => true,
            'message' => 'User deleted successfully.',
        ];
        return response()->json($response, 200);
    }



    // public function getUser(Request $request)
    // {
    // return view('user');

    ## Ajax request
    //     $draw = $request->get('draw');
    //     $start = $request->get('start');
    //     $rowperpage = $request->get('length');

    //     $columnIndex_arr = $request->get('order');
    //     $columnName_arr = $request->get('columns');
    //     $order_arr = $request->get('order');
    //     $search_arr = $request->get('search');

    //     $columnIndex = $columnIndex_arr[0]['column']; // Column index
    //     $columnName = $columnName_arr[$columnIndex]['data']; // Column name
    //     $columnSortOrder = $order_arr[0]['dir']; // asc or desc
    //     $searchValue = $search_arr['value']; // Search value

    //     // Total records
    //     $totalRecords = User::select('count(*) as allcount')->count();
    //     $totalRecordswithFilter = User::select('count(*) as allcount')->where('name', 'like', '%' . $searchValue . '%')->count();

    //     // Fetch records
    //     $records = User::orderBy($columnName, $columnSortOrder)
    //         ->where('users.name', 'like', '%' . $searchValue . '%')
    //         ->select('users.*')
    //         ->skip($start)
    //         ->take($rowperpage)
    //         ->get();

    //     $data_arr = array();

    //     foreach ($records as $record) {
    //         $id = $record->id;
    //         $name = $record->name;
    //         $email = $record->email;
    //         $created_at = $record->created_at;
    //         $updated_at = $record->updated_at;

    //         $data_arr[] = array(
    //             "id" => $id,
    //             "name" => $name,
    //             "email" => $email,
    //             "updated_at" => $created_at,
    //             "created_at" => $updated_at
    //         );
    //     }
    //     $response = array(
    //         "draw" => intval($draw),
    //         "iTotalRecords" => $totalRecords,
    //         "iTotalDisplayRecords" => $totalRecordswithFilter,
    //         "aaData" => $data_arr
    //     );
    //     echo json_encode($response);
    //     exit;
    // }


}
