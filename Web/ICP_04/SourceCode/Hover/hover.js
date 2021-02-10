// to define the image as myElement
let myElement = document.getElementById("image");
// to store the original text in the image box
let originalText= document.getElementById("image").textContent;

function upDate(previewPic) {
    /* In this function you should
       1) change the url for the background image of the div with the id = "image" to the source file of the preview image
       2) Change the text  of the div with the id = "image" to the alt text of the preview image
    */
    // the src address of the hovered image
    var src = previewPic.getAttribute("src");
    // the alt text of the hovered image
    var alt = previewPic.getAttribute("alt");

    // the background of the image is changed
    myElement.style.backgroundImage = "url('"+ src + "')";

    // the text of the image is shown
    myElement.innerHTML = alt;
}

function unDo() {
    /* In this function you should
   1) Update the url for the background image of the div with the id = "image" back to the orginal-image.  You can use the css code to see what that original URL was
   2) Change the text  of the div with the id = "image" back to the original text.  You can use the html code to see what that original text was
   */
    myElement.style.backgroundImage = "url('')";
    myElement.innerHTML = originalText;
}
