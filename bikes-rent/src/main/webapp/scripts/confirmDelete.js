document.addEventListener('DOMContentLoaded', function () {
    const deleteLinks = document.querySelectorAll('.delete-link');

    deleteLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            const confirmed = confirm('Você tem certeza que deseja deletar este cliente?');

            if (!confirmed) {
                event.preventDefault();
            }
        });
    });
});
