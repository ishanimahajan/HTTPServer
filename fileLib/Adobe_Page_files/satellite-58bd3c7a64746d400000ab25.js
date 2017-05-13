_satellite.pushAsyncScript(function(event, target, $variables){
  if (typeof jQuery === "function") {
    $('.channels').on('click', "a:contains('Refer a friend')", function(event) {
      if(s_adbadobenonacdc.pageName === "adobe.com" || s_adbadobenonacdc.pageName === "stage.adobe.com"){
        s_adbadobenonacdc.eVar69 = s_adbadobenonacdc.pageName;
        s_adbadobenonacdc.linkTrackVars = "eVar69";
        s_adbadobenonacdc.tl(true, 'o', s_adbadobenonacdc.pageName + ":ReferAFriendButtonClick");
      }
    });
}

});
