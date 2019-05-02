var products;
var currentImageIndex = 0;
var currentProduct;
var maxCarouselItems=3;
var firstImageIndex=0;
var isMobile=false;


function loadProducts()
{
    isMobile = (window.innerWidth <= 500);

    //Cross domain request doesn't work as server didn't allow, so couldn't retrieve values from
    //https://www.westelm.com/services/catalog/v4/category/shop/new/all-new/index.json
    //I downloaded the data to products.json.

    var productsUrl="../data/products.json";

    $.ajax({
        url: productsUrl,
        success:function(data)
        {
            products = data.groups;
            renderProducts(data.groups);
        },
        error:function(errorStatus,xhr)
        {
            console.log("Error"+JSON.stringify(errorStatus));
        }
    });
}

function renderProducts(products)
{
    var productHTML = "";
    var counter=0;

    $("#div_products").html("");
    if (products === null || products.length === 0)
    {
        return;
    }

    products.forEach(function(product)
    {
        productHTML = "<DIV id='div_image_" + counter + "' class='column'>";
        productHTML += "<a href='javascript:void(0);' onclick='showProductDetail(\"" + product.id + "\");'>";
        productHTML += "<img id='img_" + product.id + "' class='image_main' src='" + product.thumbnail.href;

//        if (isMobile)
//        {
//            productHTML += "' style='width:" + (product.thumbnail.width/1.7) + "; height:" + (product.thumbnail.height/1.7) + "px ";
//        }
//        else
//        {
//            productHTML += "' style='width:" + product.thumbnail.width +"; height:" + product.thumbnail.height;
//        }

        productHTML += "'></img></a>";
        productHTML += "<div id='div_title_" + counter + "' class='title'>";

//        if (isMobile)
//        {
//            productHTML += "style='width:" + (product.thumbnail.width/1.7) + "; display: none;'>";
//        }
//        else
//        {
//            productHTML += "style='width:" + product.thumbnail.width + "; display: none;'>";
//        }

        productHTML += "<a class='reset-a' href='javascript:void(0);' onclick='showProductDetail(\"" + product.id + "\");'>";
        productHTML += "<span>" + product.name + "</span></a></div><br>";

        productHTML +="</DIV>";

        $("#div_products").append(productHTML);
        counter++;
    });

    setTimeout(function(){ positionTitles(); }, 500);
}

function positionTitles()
{
    var counter=0;

    for (var counter=0; counter < $("[id^=div_title_]").length; counter++)
    {
        $("#div_title_" + counter).show();
        $("#div_title_" + counter).css('left', $("#div_image_" + counter).position().left + 15);
        $("#div_title_" + counter).css('top', $("#div_image_" + counter).position().top + 15);
    }
}

function renderProductDetail()
{
    var product = currentProduct;

    if (product === null)
    {
        return false;
    }

    $("#img_product").attr("src",product.hero.href);
    $("#lbl_title").text(product.name);

    if (typeof(product.priceRange.regular) !== "undefined" && product.priceRange.regular !== null)
    {
        $("#lbl_original_price").text("$" + product.priceRange.regular.low.toFixed(2) + " - " + product.priceRange.regular.high.toFixed(2));
        $("#lbl_price").text("On Sale $" + product.priceRange.selling.low.toFixed(2) + " - " + product.priceRange.selling.high.toFixed(2));
    }
    else
    {
        $("#lbl_price").text("$" + product.priceRange.selling.low.toFixed(2) + " - " + product.priceRange.selling.high.toFixed(2));
    }

    renderCarousel(0);
}

function renderCarousel(startIndex)
{
    var product = currentProduct;
    var carouselHtml = "";
    var counter=0;

    currentImageIndex = startIndex;

    $("#div_carousel").html("");

    if (product.images === null || product.images.length === 0)
    {
        return false;
    }

    carouselHtml = "<span id='nav_left' class='carousel_navigator' onclick='showPrevious();'>&lt;</span>";
    $("#div_carousel").append(carouselHtml);

    if (startIndex == 0)
    {
        carouselHtml = "<a href='javascript:void(0);' onclick='selectImage(\"" + product.hero.href + "\");'>";
        carouselHtml += "<img class='image_carousel' src='" + product.hero.href + "'></img></a>";
        $("#div_carousel").append(carouselHtml);
        counter++;
    }

    product.images.forEach(function(image) {
        counter++;

        if (counter >= startIndex && counter <= maxCarouselItems)
        {
          firstImageIndex=startIndex;

          carouselHtml = "<a href='javascript:void(0);' onclick='selectImage(\"" + image.href + "\");'>";
          carouselHtml += "<img class='image_carousel' src='" + image.href + "'></img></a>";
          $("#div_carousel").append(carouselHtml);
        }
        });

    carouselHtml = "<span id='nav_right' class='carousel_navigator' onclick='showNext();'>&gt;</span>";
    $("#div_carousel").append(carouselHtml);

    showHideNavigators();
}

function showHideNavigators()
{
    var product = currentProduct;

    $("#nav_right").hide();
    $("#nav_left").hide();

    //alert("maxItems: " + maxCarouselItems + ", firstIndex: " + firstImageIndex + ", totalimages: " + product.images.length);

    if (maxCarouselItems + firstImageIndex <= product.images.length)
    {
        $("#nav_right").show();
    }

    if (firstImageIndex > 0)
    {
        $("#nav_left").show();
    }
}

function selectImage(image_url)
{
    $("#img_product").attr("src",image_url);
}

function showNext()
{
    renderCarousel(currentImageIndex+1);
}

function showPrevious()
{
    renderCarousel(currentImageIndex-1);
}

function showProductDetail(productId)
{
    $.each(products, function (id, product) {
        if (product.id == productId)
        {
            currentProduct = product;
            renderProductDetail();
            document.getElementById('div_products').style.pointerEvents = 'none';
            $(".product-detail").addClass("active");
            $(".product-detail-outer").addClass("active");
            $('body').addClass('disable-background-scroll');
        }
    });


}

function closeProductDetail()
{
    document.getElementById('div_products').style.pointerEvents = 'auto';
    $(".product-detail-outer").removeClass("active");
    $(".product-detail").removeClass("active");
    $('body').removeClass('disable-background-scroll');
}