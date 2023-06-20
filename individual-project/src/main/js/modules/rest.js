export async function fetchCRUD(endPoint, method, headers, body) {
    let response
    switch (method) {
        case 'GET':
        case 'DELETE':
            response = await fetch(endPoint, {
                method,
                headers
            })
            break

        case 'POST':
        case 'PUT':
        case 'PATCH':
            response = await fetch(endPoint, {
                method,
                headers,
                body
            })
            break

        default:
            throw new Error(`Method ${method} is not supported`)
    }

    return response
}
