async function renderGallery() {
    const imageRepository = document.getElementById("imageRepository")
    imageRepository.innerHTML = ""
    const images = await getImageUrlList()
    console.log(images);
    images.urls.forEach(addImageToRepository)
}


function addImageToRepository(url) {
    const imageRepository = document.getElementById("imageRepository")
    const classes = ["rounded", "float-start", "m-1"]
    const img = createImageElementAndAllClasses(140, url, classes)
    img.setAttribute("data-bs-dismiss", "modal")
    img.addEventListener("click", () => choosePicture(url))
    imageRepository.appendChild(img)
}


function choosePicture(url) {
    document.getElementById('inputPicturePath').value = url
    const thumbnail = document.getElementById("thumbnail")
    thumbnail.innerHTML = ""
    const img = createImageElementAndAllClasses(180, url, ["rounded"])
    thumbnail.appendChild(img)
    document.getElementById('gallery')
}

function createImageElementAndAllClasses(width, url, listClasses) {
    const element = document.createElement("img")
    listClasses.forEach(className => element.classList.add(className))
    element.src = url
    element.width = width
    return element
}

async function getImageUrlList() {
    try {
        const response = await fetch("/admin/images")
        return await response.json();
    } catch (e) {
        console.log("Something went wrong!")
    }

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
    try {
        const response = await fetch("/admin/images", {
            method: "POST",
            body: createFormData(input.files[0])
        })
        if (!response.ok) {
            const error = await response.json()
            console.error(error?.message);
        }
        const image = await response.json()
        addImageToRepository(image.url);
    } catch (e) {
        console.log("Something went wrong!")
    }
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
