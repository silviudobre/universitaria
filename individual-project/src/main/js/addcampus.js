/* eslint-disable no-param-reassign */
import Joi from 'joi'
import flatpickr from 'flatpickr'
import { getCsrfHeader, getCsrfToken } from './modules/csrf.js'
import { fetchCRUD } from './modules/rest.js'

const form = document.querySelector('form')
let universityName
const name = document.getElementById('name')
const address = document.getElementById('address')
const postalCode = document.getElementById('postalCode')
const city = document.getElementById('city')
const openingTime = document.getElementById('openingTime')
const closingTime = document.getElementById('closingTime')
const submitButton = document.getElementById('submit')

const universityNameError = document.getElementById('universityNameError')
const campusNameError = document.getElementById('campusNameError')
const addressError = document.getElementById('addressError')
const postalCodeError = document.getElementById('postalCodeError')
const cityError = document.getElementById('cityError')
const timeError = document.getElementById('timeError')
const successfulSubmission = document.getElementById('successfulSubmission')

const nameRegex = /(^(([a-z])|([a-z][a-z ]*[a-z]))$)/i
const addressRegex = /(^(([a-z])|([a-z][a-z ]*[a-z])) [0-9]+$)/i
const postalCodeRegex = /([1-9][0-9]*$)/i
const timeRegex = /(^(([0-1][0-9])|(2[0-3])):[0-5][0-9]$)/i

const keysToInputs = new Map()
keysToInputs.set('name', name)
keysToInputs.set('address', address)
keysToInputs.set('postalCode', postalCode)
keysToInputs.set('city', city)
keysToInputs.set('openingTime', openingTime)
keysToInputs.set('closingTime', closingTime)

function getUniversityNames() {
    const universityNames = []
    const universities = document.querySelectorAll('input[name=\'universityName\']')
    for (const university of universities) {
        universityNames.push(university.value)
    }
    // console.log(universityNames)
    return universityNames
}

const campusSchema = Joi.object({
    universityName: Joi.string()
        .valid(...getUniversityNames())
        .required(),
    name: Joi.string()
        .regex(nameRegex)
        .required(),
    address: Joi.string()
        .regex(addressRegex)
        .required(),
    postalCode: Joi.string()
        .regex(postalCodeRegex)
        .required(),
    city: Joi.string()
        .regex(nameRegex)
        .required(),
    openingTime: Joi.string()
        .regex(timeRegex)
        .required(),
    closingTime: Joi.string()
        .regex(timeRegex)
        .required()
})

function setDefaultErrorSettings() {
    universityNameError.style.visibility = 'hidden'
    campusNameError.style.visibility = 'hidden'
    addressError.style.visibility = 'hidden'
    postalCodeError.style.visibility = 'hidden'
    cityError.style.visibility = 'hidden'
    timeError.style.visibility = 'hidden'

    universityNameError.style.fontSize = '0em'
    campusNameError.style.fontSize = '0em'
    addressError.style.fontSize = '0em'
    postalCodeError.style.fontSize = '0em'
    cityError.style.fontSize = '0em'
    timeError.style.fontSize = '0em'
}

function toggleVisibility(option, textElement) {
    if (option === 'on') {
        textElement.style.visibility = 'visible'
        textElement.style.fontSize = '1em'
    } else if (option === 'off') {
        textElement.style.visibility = 'hidden'
        textElement.style.fontSize = '0em'
    }
}

async function trySubmission() {
    universityName = document.querySelector('input[name=\'universityName\']:checked')
    keysToInputs.set('universityName', universityName)

    const campusObject = {
        universityName: universityName.value,
        name: name.value,
        address: address.value,
        postalCode: postalCode.value,
        city: city.value,
        openingTime: openingTime.value,
        closingTime: closingTime.value
    }

    const validationResult = campusSchema.validate(campusObject, { abortEarly: false })
    console.log(validationResult)

    universityName.setCustomValidity('')
    name.setCustomValidity('')
    address.setCustomValidity('')
    postalCode.setCustomValidity('')
    city.setCustomValidity('')
    openingTime.setCustomValidity('')
    closingTime.setCustomValidity('')

    setDefaultErrorSettings()

    if (validationResult.error) {
        for (const errorDetail of validationResult.error.details) {
            const inputField = keysToInputs.get(errorDetail.context.key)
            inputField.setCustomValidity(errorDetail.message)
            if (inputField === universityName) {
                toggleVisibility('on', universityNameError)
                universityNameError.innerHTML = errorDetail.message
            } else if (inputField === openingTime || inputField === closingTime) {
                toggleVisibility('on', timeError)
                timeError.innerHTML = errorDetail.message
            } else {
                toggleVisibility('on', inputField.nextElementSibling)
                inputField.nextElementSibling.innerHTML = errorDetail.message
            }
        }
    } else {
        const headers = {}
        headers.Accept = 'application/json'
        headers['Content-Type'] = 'application/json'
        headers[getCsrfHeader()] = getCsrfToken()

        const jsonBody = JSON.stringify(campusObject)

        const response = await fetchCRUD('/api/campuses', 'POST', headers, jsonBody)

        if (response.status === 201) {
            form.reset()
            toggleVisibility('on', successfulSubmission)
        }
    }
}

flatpickr('.myFlatPickrTime', {
    enableTime: true,
    noCalendar: true,
    dateFormat: 'H:i',
    time_24hr: true
})

setDefaultErrorSettings()
toggleVisibility('off', successfulSubmission)

submitButton.addEventListener('click', trySubmission)
