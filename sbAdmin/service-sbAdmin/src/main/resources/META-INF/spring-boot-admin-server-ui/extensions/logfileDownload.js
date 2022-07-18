(function(){
var flag = setInterval(function(){
  try{
        var lvas=document.getElementsByClassName("logfile-view-actions");
        if( lvas.length )
        {
            lvas[0].childNodes[1].download="logfile";
            clearInterval(flag);
        }
    }catch(e){}
},5000);


})();