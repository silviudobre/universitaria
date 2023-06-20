export function getCsrfHeader() {
    return document.querySelector('meta[name="_csrf_header"]').content
}

export function getCsrfToken() {
    return document.querySelector('meta[name="_csrf"]').content
}
