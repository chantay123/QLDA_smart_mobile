const image = document.querySelector(".image");

image.addEventListener("mousemove", (e) => {
    const {clientX, clientY, target} = e;
    const {width, height} = target.getBoundingClientRect();

    // Tính toán giá trị xoay
    const rotateX = ((clientY - target.offsetTop) / height - 0.5) * 30; // Điều chỉnh giá trị 30 để thay đổi độ nghiêng
    const rotateY = ((clientX - target.offsetLeft) / width - 0.5) * -30; // Điều chỉnh giá trị -30 để thay đổi độ nghiêng

    // Áp dụng transform
    image.style.transform = `perspective(300px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
});

image.addEventListener("mouseleave", () => {
    image.style.transform = "perspective(300px) rotateX(0deg) rotateY(0deg)"; // Trở về trạng thái ban đầu
});

$(document).ready(function () {
    var error = $('#errorDiv span').text(); // Nhúng giá trị error từ model vào biến error trong JavaScript
    if (error) {
        toastr.options.timeout = 2000;
        toastr.error(error); // Hiển thị thông báo lỗi nếu có
    }
});

