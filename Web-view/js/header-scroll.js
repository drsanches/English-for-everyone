var header = document.querySelector('.header');

window.onscroll = function () {
    if (window.pageYOffset == 0) {
        header.classList.remove('fixed');
    } else {
        header.classList.add('fixed');
    }
}
