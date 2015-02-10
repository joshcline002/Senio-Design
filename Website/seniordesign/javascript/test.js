$(function() { //When the document loads
  $("#scrollTop").bind("click", function() {
    $(window).scrollTop($("#scroll").offset().top); //scroll to div with container as ID.
    return false; //Prevent Default and event bubbling.
  });
});