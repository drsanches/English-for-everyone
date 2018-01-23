var logButton = document.querySelector('.login-btn'),
    regButton = document.querySelector('.reg-btn'),
    logContainer = document.querySelector('.login-form-container '),
    regContainer = document.querySelector('.reg-form-container'),
    fader = document.querySelector('.fade');


logButton.addEventListener('click', function () {
    showContainer(logContainer)
});
regButton.addEventListener('click', function () {
    showContainer(regContainer)
});
fader.addEventListener('click', closeContainers)






function showContainer(container) {
    container.style.display = 'block';
    fader.style.display = 'block';
}


function closeContainers() {
    if (logContainer.style.display == 'block') {
        logContainer.style.display = 'none';
    }
    if (regContainer.style.display == 'block') {
        regContainer.style.display = 'none';
    }

    fader.style.display = 'none';
}
