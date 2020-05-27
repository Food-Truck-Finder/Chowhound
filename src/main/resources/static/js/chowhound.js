function openTruckModal(id) {

    $.ajax({
        url: "/trucks/" + id,
        success: function (data) {
            $("truckModalHolder").html(data);
            $("#truckModal").modal("show");
        }
    });
}