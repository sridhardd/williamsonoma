function loadProducts()
{
    //Cross domain request doesn't work as server didn't allow, so couldn't retrieve values from
    //https://www.westelm.com/services/catalog/v4/category/shop/new/all-new/index.json
    //I downloaded the data to products.json.

    var productsUrl="../data/products.json";

    $.ajax({
        url: productsUrl,
        success:function(data)
        {
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

    if (products === null || products.length === 0)
    {
        return;
    }

    products.forEach(function(product) {
      productHTML = "<DIV id='div_image_" + counter + "' class='column'>";
      productHTML += "<a href='product.html?id=" + product.id + "'>";
      productHTML += "<img class='image_main' src='" + product.thumbnail.href;
      productHTML += "' style='width:" + product.thumbnail.width +" height:" + product.thumbnail.height + "'></img></a>";
      productHTML += "<div id='div_title_" + counter + "' class='title' ";
      productHTML += "style='width:" + product.thumbnail.width + ";'>";
      productHTML += "<a class='reset-a' href='product.html?id=" + product.id + "'><span>" + product.name + "</span></a></div><br>";

      productHTML +="</DIV>";

      $("#div_products").append(productHTML);

      $("#div_title_" + counter).css('left', $("#div_image_" + counter).position().left + 12);
      $("#div_title_" + counter).css('top', $("#div_image_" + counter).position().top + 12);
      counter++;
    });
}