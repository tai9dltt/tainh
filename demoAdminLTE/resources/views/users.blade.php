@extends('layouts.app')

@section('content')

    <div class="container">
        <br>
        <div class="container-fluid">
            <div class="row">
                <h4 class="one"> List User</h4>
                <button class="btn btn-info ml-auto" id="createNewUser">Create User</button>
            </div>
        </div>
        <br>

        <div class="form-group col-md-3">
            <h5>From Date <span class="text-danger"></span></h5>
            <div class="controls">
                <input type="date" name="from_date" id="from_date" class="form-control datepicker-autoclose"
                    placeholder="Please from date">
            </div>
        </div>

        <div class="form-group col-md-3">
            <h5>To Date <span class="text-danger"></span></h5>
            <div class="controls">
                <input type="date" name="to_date" id="to_date" class="form-control datepicker-autoclose"
                    placeholder="Please to date">
            </div>
        </div>


        <div class="form-group col-md-2" style="margin-top: 32px;">
            <div class="controls">
                <button type="text" id="btn-search" class="btn btn-info form-control">Submit</button>
            </div>
        </div>

        <table id="dataTable" class="table table-bordered">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role </th>
                    <th>Status </th>
                    <th width="250px">Action</th>

                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="ajaxModel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modelHeading"></h4>
                </div>
                <div class="modal-body">
                    <form id="userForm" name="userForm" class="form-horizontal">
                        <input type="hidden" name="user_id" id="user_id">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name"
                                    value="" maxlength="50" required="" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="email" name="email" placeholder="Enter email"
                                    value="" maxlength="50" required="" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Password</label>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="password" name="password" required
                                    placeholder="Enter password" value="" maxlength="50" required="" autocomplete="off">
                            </div>
                        </div>


                        {{-- <div class="form-group">
                            <label class="col-sm-2 control-label">Role</label>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="role" name="role" placeholder="Enter Role"
                                    value="" maxlength="50" required="" autocomplete="off">
                            </div>
                        </div> --}}

                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="exampleFormControlSelect1">Role</label>
                            <select class="form-control" id="role" name="role">
                              <option value="1">Admin</option>
                              <option value="2">User</option>
                            </select>
                          </div>


                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary" id="saveBtn">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>



    <script type="text/javascript">
        $(function() {
            //ajax setup
            $.ajaxSetup({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                }
            });

            // datatable
            var table = $('#dataTable').DataTable({
                processing: true,
                serverSide: true,
                ajax: {
                    url: "{{ url('users') }}",
                    data: function(d) {
                        d.from_date = $('#from_date').val();
                        d.to_date = $('#to_date').val();
                    }
                },
                columns: [{
                        data: 'id',
                        name: 'id'
                    },
                    {
                        data: 'name',
                        name: 'name'
                    },
                    {
                        data: 'email',
                        name: 'email'
                    },
                    {
                        data: 'role_id',
                        name: 'role_id',
                        render: function(data, type, full, meta) {
                            if(data == 1)
                            return "Admin";
                            else return "User";
                        }
                    },
                    {
                        data: 'status',
                        name: 'status',
                        render: function(data, type, full, meta) {
                            return data ? "Active" : "Not Active";
                        }
                    },
                    {
                        data: 'action',
                        name: 'action',
                        orderable: false,
                        searchable: false
                    },
                ]
            });

            $('#btn-search').click(function() {
                $('#dataTable').DataTable().draw(true);
            });

            // create new user
            $('#createNewUser').click(function() {
                $('#saveBtn').html("Create");
                $('#user_id').val('');
                $('#userForm').trigger("reset");
                $('#modelHeading').html("Create New User");
                $('#ajaxModel').modal('show');
            });

            // create or update user
            $('#saveBtn').click(function(e) {
                e.preventDefault();
                $(this).html('Saving..');

                $.ajax({
                    data: $('#userForm').serialize(),
                    url: "{{ url('users') }}",
                    type: "POST",
                    dataType: 'json',
                    success: function(data) {
                        $('#userForm').trigger("reset");
                        $('#ajaxModel').modal('hide');
                        table.draw();
                        $('#saveBtn').html('Save');
                    },
                    error: function(data) {
                        console.log('Error:', data);
                        $('#saveBtn').html('Save');
                    }
                });
            });


            // edit user
            $('body').on('click', '.editUser', function() {
                var user_id = $(this).data('id');
                $.get("{{ url('users') }}" + '/' + user_id + '/edit', function(data) {
                    $('#modelHeading').html("Edit User");
                    $('#saveBtn').html('Update');
                    $('#ajaxModel').modal('show');
                    $('#user_id').val(data.id);
                    $('#name').val(data.name);
                    $('#email').val(data.email);
                })
            });
            // delete user
            $('body').on('click', '.deleteUser', function() {
                var user_id = $(this).data("id");
                var delete_confirm = confirm("Are You sure want to delete !");

                if (delete_confirm) {
                    $.ajax({
                        type: "DELETE",
                        url: "{{ url('users') }}" + '/' + user_id,
                        success: function(data) {
                            table.draw(false);

                        },
                        error: function(data) {
                            console.log('Error:', data);
                        }
                    });
                }
            });

        });

        $('body').on('click', '.changeStatus', function() {
            var user_id = $(this).data("id");
            var change_confirm = confirm("Are You sure want to change !");

            if (change_confirm) {
                $.ajax({
                    type: "POST",
                    url: "{{ url('users') }}" + '/' + 'change/' + user_id,
                    success: function(data) {
                        table.draw(true);
                    },
                    error: function(data) {
                        console.log('Error:', data);
                    }
                });
            }
        });

    </script>



    <script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc="
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
    </script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    {{-- <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js">
    </script> --}}


@endsection
