// Biến để lưu trữ liên kết đang được focus
let activeLink;

// Gán focus cho liên kết khi trang tải
window.onload = function() {
    // Kiểm tra xem có giá trị lưu trong localStorage không
    const storedLink = localStorage.getItem('activeLink');
    if (storedLink) {
        activeLink = document.querySelector(`.sidenav a[href="${storedLink}"]`);
    } else {
        activeLink = document.querySelector('.sidenav a.active');
    }

    if (activeLink) {
        activeLink.classList.add('active');
        activeLink.focus();
    }
};

// Giữ focus cho liên kết khi offcanvas mở
var offcanvasElement = document.getElementById('staticBackdrop');
offcanvasElement.addEventListener('shown.bs.offcanvas', function () {
    if (activeLink) {
        activeLink.focus();
    }
});

// Lưu lại liên kết đang được focus khi người dùng nhấn vào một liên kết
document.querySelectorAll('.sidenav a').forEach(link => {
    link.addEventListener('click', function() {
        // Cập nhật liên kết đang được focus
        if (activeLink) {
            activeLink.classList.remove('active');
        }
        activeLink = this;
        activeLink.classList.add('active');

        // Lưu đường dẫn của liên kết vào localStorage
        localStorage.setItem('activeLink', this.getAttribute('href'));
    });
});


// Start date selection
flatpickr("#datepicker_start", {
    dateFormat: "Y-m-d",   // Định dạng ngày hiển thị
    defaultDate: "today",
    minDate: "2000-01-01",
    maxDate: "today",
})
// End date selection
flatpickr("#datepicker_end", {
    dateFormat: "Y-m-d",   // Định dạng ngày hiển thị
    defaultDate: "today",
    minDate: "2000-01-01",
    maxDate: "today",
})

// js đổi trang