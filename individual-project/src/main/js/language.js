const locale = document.getElementById('locales')

function changeLanguage(event) {
    const selectedOption = event.target.value
    if (selectedOption !== '') {
        const urlArguments = window.location.search
        const urlParameters = new URLSearchParams(urlArguments)

        if (urlParameters.has('lang')) {
            urlParameters.set('lang', selectedOption)
        } else {
            urlParameters.append('lang', selectedOption)
        }

        window.location.replace(`?${urlParameters}`)
    }
}

locale.addEventListener('change', changeLanguage)
