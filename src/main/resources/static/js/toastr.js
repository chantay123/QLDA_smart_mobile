// js toastr
$(document).ready(function () {
    var error = $('#errorCreateuser span').text(); // Nhúng giá trị error từ model vào biến error trong JavaScript
    if (error) {
        toastr.options.timeout = 2000;
        toastr.error(error); // Hiển thị thông báo lỗi nếu có
    }
});

$(document).ready(function () {
    var error = $('#errorModifyUser span').text(); // Nhúng giá trị error từ model vào biến error trong JavaScript
    if (error) {
        toastr.options.timeout = 2000;
        toastr.error(error,); // Hiển thị thông báo lỗi nếu có
    }
});