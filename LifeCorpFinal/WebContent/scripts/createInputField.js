/**
 * 
 */

var $ = function(id)
{
	return document.getElementById(id);
}

function dynInput(cbox) 
{
  if (cbox.checked) 
  {
    var input = document.getElementById("newCategory");
    input.type = "text";
    // input.name = "newCategory";
    // input.id = "newCategory";
    // var div = document.createElement("div");
    // div.id = cbox.name;
    // div.innerHTML = "Text to display for " + cbox.name;
    // div.appendChild(input);
    // document.getElementById("addCategory").appendChild(div);
  } else 
  {
    // document.getElementById("newCategory").remove();
	var input = document.getElementById("newCategory");
	input.type = "hidden";
  }
}

window.onload = function()
{
	// $("calculate").onclick = checkPassHTML5;
	// $("subtotal").focus;
	$("addCategory").onclick = dynInput($("addCategory"));

}