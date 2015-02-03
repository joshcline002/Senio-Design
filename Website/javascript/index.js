$(document)
  .ready(function() {

    $('.filter.menu .item')
      .tab()
    ;

    $('.ui.rating')
      .rating({
        clearable: true
      })
    ;

    $('.ui.sidebar')
      .sidebar('attach events', '.launch.button')
    ;


    $('.ui.dropdown')
      .dropdown()
    ;
    
    $(function() { //When the document loads
 
   $("#homebutton").bind("click", function() {
      $(window).scrollTop($("#home").offset().top); 
      return false; 
    });
    
    $("#aboutbutton").bind("click", function() {
      $(window).scrollTop($("#intro2").offset().top);
      return false; 
    });
    
    $("#classbutton").bind("click", function() {
      $(window).scrollTop($("#section1").offset().top);
      return false;
    });
    
    $("#projectbutton").bind("click", function() {
      $(window).scrollTop($("#section2").offset().top); 
      return false; 
    });
    
    $("#workbutton").bind("click", function() {
      $(window).scrollTop($("#section3").offset().top); 
      return false; 
    });
    
    $("#resumebutton").bind("click", function() {
      $(window).scrollTop($("#section4").offset().top); 
      return false; 
    });
    $("#reflectionbutton").bind("click", function() {
      $(window).scrollTop($("#section5").offset().top); 
      return false; 
    });
    
});

});
    
   
  