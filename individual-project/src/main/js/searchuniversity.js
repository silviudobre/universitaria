import { getCsrfHeader, getCsrfToken } from './modules/csrf.js'
import { fetchCRUD } from './modules/rest.js'

const searchInput = document.getElementById('searchValue')
const searchResults = document.getElementById('searchResults')
const tbody = document.querySelector('tbody')
const emptyTableImage = document.querySelector('.emptyTableImage')

searchResults.style.display = 'none'
emptyTableImage.style.display = 'inline-block'

function showUniversities(universities) {
    const searchValue = searchInput.value
    const regex = new RegExp(`(${searchValue})`, 'gi')

    searchResults.style.display = 'table'
    emptyTableImage.style.display = 'none'
    tbody.innerHTML = ''
    let counter = 0
    for (const university of universities) {
        const highlightedString = university.name.replace(regex, '<span class="bold">$1</span>')

        if (counter % 2) {
            tbody.innerHTML += `
            <tr class="table-danger">
                <td>${highlightedString}</td>
                <td>${university.foundingDate}</td>
                <td>${university.universityType}</td>
            </tr>`
        } else {
            tbody.innerHTML += `
            <tr class="table-primary">
                <td>${highlightedString}</td>
                <td>${university.foundingDate}</td>
                <td>${university.universityType}</td>
            </tr>`
        }

        // eslint-disable-next-line no-plusplus
        counter++
    }
}

async function searchUniversities() {
    const searchValue = searchInput.value

    const headers = {}
    headers.Accept = 'application/json'
    headers[getCsrfHeader()] = getCsrfToken()

    const response = await fetchCRUD(`/api/universities?searchValue=${searchValue}`, 'GET', headers)

    if (response.status === 200) {
        const jsonResponse = await response.json()
        showUniversities(jsonResponse)
    } else {
        tbody.innerHTML = ''
        searchResults.style.display = 'none'
        emptyTableImage.style.display = 'inline-block'
    }
}

searchInput.addEventListener('input', searchUniversities)
