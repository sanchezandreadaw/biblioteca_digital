
const showAlert = (icon = "", title = "", text = "", theme = "") => {
    return Swal.fire({
        icon: icon,
        title: title,
        text: text,
        theme: theme
    });
}
window.showAlert = showAlert;