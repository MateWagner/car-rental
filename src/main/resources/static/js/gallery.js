async function renderGallery() {
    const imageRepository = document.getElementById("imageRepository")
    imageRepository.innerHTML = ""
    const images = await getImageUrlList()
    console.log(images);
    images.urls.forEach(addImageToRepository)
}


function addImageToRepository(url) {
    const imageRepository = document.getElementById("imageRepository")
    const classes = ["rounded", "float-start"]
    const img = createImageElementAndAllClasses(100, url, classes)
    img.addEventListener("click", () => onClick(url))
    imageRepository.appendChild(img)
}


function onClick(url) {
    document.getElementById('inputPicturePath').value = url
    const thumbnail = document.getElementById("thumbnail")
    thumbnail.innerHTML = ""
    const img = createImageElementAndAllClasses(150, url, ["rounded"])
    thumbnail.appendChild(img)
}

function createImageElementAndAllClasses(width, url, listClasses) {
    const element = document.createElement("img")
    listClasses.forEach(className => element.classList.add(className))
    element.src = url
    element.width = width
    return element
}

async function getImageUrlList() {
    const response = await fetch("/admin/images")
    return await response.json();
    // TODO Error handling
}

function addEventListenerForOpeningGallery() {
    const galleryModal = document.getElementById('gallery')
    galleryModal.addEventListener('show.bs.modal', () => renderGallery())
}

async function uploadImageAndAddToGallery(event) {
    event.preventDefault()
    event.stopPropagation()
    const input = document.getElementById("input-file")
    if (input.files.length === 0) {
        return;
    }

    const response = await fetch("/admin/images", {
        method: "POST",
        body: createFormData(input.files[0])
    })
    const image = await response.json()
    console.log(image);
    addImageToRepository(image.url);

}

function createFormData(file) {
    const formData = new FormData()
    formData.append("file", file)
    return formData
}

function addEventListenerForUploadButton() {
    const uploadButton = document.getElementById('upload-button')
    uploadButton.addEventListener("click", uploadImageAndAddToGallery)
}


function init() {
    addEventListenerForOpeningGallery()
    addEventListenerForUploadButton()
}

init()
