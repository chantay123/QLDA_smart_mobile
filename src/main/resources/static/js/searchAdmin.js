function submitFilterForm() {
    // Bỏ chọn checkbox còn lại nếu checkbox này được chọn
    let lockedCheckbox = document.getElementById('locked');
    let unlockedCheckbox = document.getElementById('unlocked');

    if (lockedCheckbox.checked) {
        unlockedCheckbox.checked = false;
    }

    if (unlockedCheckbox.checked) {
        lockedCheckbox.checked = false;
    }

    // Submit form sau khi checkbox thay đổi
    document.getElementById('filterForm').submit();
}
