import { getCsrfHeader, getCsrfToken } from './modules/csrf.js'
import { fetchCRUD } from './modules/rest.js'

const deleteButtons = document.querySelectorAll('td .deleteButton')
const campusDetails = document.getElementsByClassName('form-control-plaintext')
const updateButtons = document.querySelectorAll('td .updateButton')
const resetButtons = document.querySelectorAll('td .resetButton')
const emptyTableImage = document.querySelector('.emptyTableImage')

const campusDetailsMap = new Map()
const campusRowsBackupMap = new Map()

function getCampusId(tableRow) {
    const tableRowId = tableRow.id
    const campusId = +tableRowId.substring(tableRowId.indexOf('_') + 1)

    return campusId
}

function resetCampusRow(tableRow) {
    const resetButton = tableRow.querySelector('td .resetButton')
    const updateButton = tableRow.querySelector('td .updateButton')
    updateButton.style.display = 'none'
    resetButton.style.display = 'none'

    const campusId = getCampusId(tableRow)
    const campusRowBackupMap = campusRowsBackupMap.get(campusId)

    for (const campusRowDetail of campusRowBackupMap.keys()) {
        campusRowDetail.value = campusRowBackupMap.get(campusRowDetail)
    }
}

async function updateCampus(event) {
    const updateButton = event.target
    const resetButton = event.target.parentNode.parentNode.querySelector('td .resetButton')
    updateButton.style.display = 'none'
    resetButton.style.display = 'none'

    const tableRow = event.target.parentNode.parentNode
    const campusId = getCampusId(tableRow)

    let jsonBody = '{'
    let isFirstJsonRow = true
    let myResponseStatus = new Response(null, { status: 204 })
    const currentCampusDetails = campusDetailsMap.get(campusId)
    for (const campusDetail of currentCampusDetails) {
        const detailName = campusDetail.dataset.value
        const detailValue = campusDetail.value

        if (!isFirstJsonRow) {
            jsonBody = jsonBody.concat(',')
        }

        if (detailName === 'closingTime' || detailName === 'openingTime') {
            if (detailValue === '') {
                myResponseStatus = new Response(null, { status: 400 })
            }
        }

        if (detailName === 'postalCode') {
            jsonBody = jsonBody.concat(`"${detailName}":${detailValue}`)
        } else {
            jsonBody = jsonBody.concat(`"${detailName}":"${detailValue}"`)
        }

        isFirstJsonRow = false
    }
    jsonBody = jsonBody.concat('}')

    const headers = {}
    headers.Accept = 'application/json'
    headers['Content-Type'] = 'application/json'
    headers[getCsrfHeader()] = getCsrfToken()

    const response = await fetchCRUD(`/api/campuses/${campusId}`, 'PATCH', headers, jsonBody)

    if (response.status !== 204 || myResponseStatus.status !== 204) {
        resetCampusRow(tableRow)
    }
    campusDetailsMap.delete(campusId)
    campusRowsBackupMap.delete(campusId)
}

function handleTHead() {
    const tHead = document.querySelector('thead')
    const nrRows = document.querySelectorAll('tr').length
    if (nrRows > 1) {
        tHead.style.visibility = 'visible'
        emptyTableImage.style.display = 'none'
    } else {
        tHead.style.visibility = 'hidden'
        emptyTableImage.style.display = 'inline-block'
    }
}

async function deleteCampus(event) {
    const tableRow = event.target.parentNode.parentNode
    const campusId = getCampusId(tableRow)

    const headers = {}
    headers[getCsrfHeader()] = getCsrfToken()

    const response = await fetchCRUD(`/api/campuses/${campusId}`, 'DELETE', headers)

    if (response.status === 204) {
        tableRow.parentNode.removeChild(tableRow)
    }
    handleTHead()
}

function hideButtons(buttons) {
    for (const button of buttons) {
        button.style.display = 'none'
    }
}

function makeCampusRowBackup(event) {
    const campusRow = event.target.parentNode.parentNode
    const campusId = getCampusId(campusRow)
    const campusRowDetailMap = new Map()

    if (campusRowsBackupMap.get(campusId) !== undefined) return

    campusRowsBackupMap.set(campusId, campusRowDetailMap)
    const campusRowDetails = campusRow.querySelectorAll('td input')
    for (const campusRowDetail of campusRowDetails) {
        campusRowDetailMap.set(campusRowDetail, campusRowDetail.value)
    }
}

function makeCampusDetailEdits(event) {
    const campusDetail = event.target
    console.log(campusDetail)

    const tableRow = event.target.parentNode.parentNode
    const campusId = getCampusId(tableRow)

    const currentMapValue = campusDetailsMap.get(campusId)
    if (currentMapValue === undefined) {
        campusDetailsMap.set(campusId, [])
    }

    if (campusDetailsMap.get(campusId).indexOf(campusDetail) === -1) {
        campusDetailsMap.get(campusId).push(campusDetail)
    } else {
        campusDetailsMap.get(campusId)[campusDetail] = campusDetail
    }

    const updateButton = event.target.parentNode.parentNode.querySelector('td .updateButton')
    const resetButton = event.target.parentNode.parentNode.querySelector('td .resetButton')
    updateButton.style.display = 'inline-block'
    resetButton.style.display = 'inline-block'
}

handleTHead()
hideButtons(updateButtons)
hideButtons(resetButtons)

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener('click', deleteCampus)
}

for (const campusDetail of campusDetails) {
    campusDetail.addEventListener('click', makeCampusRowBackup)
    campusDetail.addEventListener('input', makeCampusDetailEdits)
}

for (const updateButton of updateButtons) {
    updateButton.addEventListener('click', updateCampus)
}

for (const resetButton of resetButtons) {
    resetButton.addEventListener('click', (event) => {
        const tableRow = event.target.parentNode.parentNode
        resetCampusRow(tableRow)
    })
}
