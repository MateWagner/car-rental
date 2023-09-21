function addDaysToDateObject(date, day) {
    let result = new Date(date)
    result.setDate(result.getDate() + day)
    return result
}

function getFormattedDateString(date) {
    return date.toISOString().split('T')[0]
}

function setDatePickerMin(id, extraDay = 0) {
    const date = addDaysToDateObject(new Date(), extraDay)
    const dateString = getFormattedDateString(date)
    const dateInput = document.getElementById(id)
    dateInput.setAttribute("min", dateString)
}

function init() {
    setDatePickerMin('dateFrom')
    setDatePickerMin('dateTo', 1)
}

init()
