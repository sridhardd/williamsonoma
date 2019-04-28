var currentImageIndex = 0;
var currentProduct;
var maxCarouselItems=3;
var firstImageIndex=0;

function loadProductDetail()
{
    //Cross domain request doesn't work as server didn't allow, so couldn't retrieve values from
    //https://www.westelm.com/services/catalog/v4/category/shop/new/all-new/index.json
    //I downloaded the data to products.json.
    var productsUrl="../data/products.json";

    $.ajax({
        url: productsUrl,
        success:function(data)
        {
            var productId="";

            var queryStrings = location.toString().substring(location.toString().indexOf("?")+1, location.toString().length).split("&");

            if (queryStrings !== null && queryStrings.length > 0)
            {
                for (var counter=0; counter < queryStrings.length; counter++)
                {
                    if (queryStrings[counter].split("=")[0] == "id")
                    {
                        productId = queryStrings[counter].split("=")[1].trim();
                    }
                }

                if (productId !== "")
                {
                    $.each(data.groups, function (id, product) {
                        if (product.id == productId)
                        {
                            currentProduct = product;
                            renderProductDetail();
                        }
                    });
                }
            }
        },
        error:function(errorStatus,xhr)
        {
            console.log("Error"+JSON.stringify(errorStatus));
        }
    });
}

function renderProductDetail()
{
    var product = currentProduct;

    if (product === null)
    {
        return false;
    }

    $("#img_product").attr("src",product.hero.href);
    $("#img_product").css('height', product.hero.height * 2 - 200);
    $("#img_product").css('width', product.hero.width * 2 - 150);
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